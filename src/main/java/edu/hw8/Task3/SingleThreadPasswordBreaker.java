package edu.hw8.Task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SingleThreadPasswordBreaker implements PasswordBreaker {

    public static final int MD5_HASH_LENGTH = 32;
    public static final int MD5_HASH_BASE = 16;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz123456789";
    public static final String ALGORITHM = "MD5";
    private final Map<String, String> hashes;
    private final int maxPasswordLength;

    public SingleThreadPasswordBreaker(Map<String, String> hashes, int maxPasswordLength) {
        this.hashes = hashes;
        this.maxPasswordLength = maxPasswordLength;
    }

    public Map<String, String> getPasswords() {
        var result = new HashMap<String, String>();
        tryAllPasswords(result, new byte[maxPasswordLength], 0);
        return result;
    }

    private boolean tryAllPasswords(Map<String, String> result, byte[] password, int current) {
        if (current == password.length) {
            return false;
        }

        boolean doneSearching = false;
        for (byte b : ALPHABET.getBytes()) {
            password[current] = b;
            if (tryBreakPassword(result, password, current) || tryAllPasswords(result, password, current + 1)) {
                doneSearching = true;
                break;
            }
        }
        return doneSearching;
    }

    private boolean tryBreakPassword(Map<String, String> result, byte[] password, int current) {
        boolean doneSearching = false;
        try {
            var md = MessageDigest.getInstance(ALGORITHM);
            for (int i = 0; i <= current; ++i) {
                md.update(password[i]);
            }

            var hash = new BigInteger(1, md.digest()).toString(MD5_HASH_BASE);
            if (hash.length() != MD5_HASH_LENGTH) {
                // BigInteger::toString отбрасывает первые нули
                hash = "0".repeat(MD5_HASH_LENGTH - hash.length()) + hash;
            }

            var user = hashes.get(hash);
            if (user != null) {
                result.put(user, new String(password, 0, current + 1));
                if (result.size() == hashes.size()) {
                    doneSearching = true;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return doneSearching;
    }

}
