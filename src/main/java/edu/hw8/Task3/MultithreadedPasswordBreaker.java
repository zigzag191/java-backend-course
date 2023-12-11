package edu.hw8.Task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultithreadedPasswordBreaker implements PasswordBreaker {

    public static final String ALGORITHM = "MD5";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz123456789";
    public static final int MD5_HASH_LENGTH = 32;
    public static final int MD5_HASH_BASE = 16;
    private final HashMap<String, String> userPasswordHashes;
    private final int threads;
    private final int maxPasswordLength;

    public MultithreadedPasswordBreaker(Map<String, String> userPasswordHashes, int threads, int maxPasswordLength) {
        this.userPasswordHashes =
            new HashMap<>(userPasswordHashes); // read-only копия, чтобы не синхронизироваться при чтении
        this.threads = threads;
        this.maxPasswordLength = maxPasswordLength;
    }

    public Map<String, String> getPasswords() {
        var result = new HashMap<String, String>();
        try (var pool = new ForkJoinPool(threads)) {
            var task = new DfsBruteForce(userPasswordHashes, result, maxPasswordLength);
            pool.execute(task);
            task.join();
        }
        return result;
    }

    private final static class DfsBruteForce extends RecursiveAction {

        private final Map<String, String> hashes;
        private final Map<String, String> result;
        private final byte[] password;
        private final int current;
        private final AtomicBoolean doneSearching;

        private DfsBruteForce(Map<String, String> hashes, Map<String, String> result, byte[] password, int current,
            AtomicBoolean doneSearching
        ) {
            this.hashes = hashes;
            this.result = result;
            this.password = Arrays.copyOf(password, password.length);
            this.current = current;
            this.doneSearching = doneSearching;
        }

        private DfsBruteForce(Map<String, String> hashes, Map<String, String> result, int length) {
            this(hashes, result, new byte[length], 0, new AtomicBoolean());
        }

        private void tryBreakPassword() {
            try {
                var md = MessageDigest.getInstance(ALGORITHM);
                for (int i = 0; i <= current; ++i) {
                    md.update(password[i]);
                }

                var hash = new BigInteger(1, md.digest()).toString(MD5_HASH_BASE);
                if (hash.length() != MD5_HASH_LENGTH) {
                    hash = "0".repeat(MD5_HASH_LENGTH - hash.length()) + hash;
                }

                var user = hashes.get(hash);
                if (user != null) {
                    synchronized (result) {
                        result.put(user, new String(password, 0, current + 1));
                        if (result.size() == hashes.size()) {
                            doneSearching.set(true);
                        }
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void compute() {
            if (current != password.length) {
                var tasks = new ArrayList<ForkJoinTask<Void>>();
                for (byte b : ALPHABET.getBytes()) {
                    password[current] = b;
                    tryBreakPassword();
                    if (doneSearching.get()) {
                        return;
                    }
                    tasks.add(new DfsBruteForce(hashes, result, password, current + 1, doneSearching));
                }
                ForkJoinTask.invokeAll(tasks);
                tasks.forEach(ForkJoinTask::join);
            }
        }

    }

}
