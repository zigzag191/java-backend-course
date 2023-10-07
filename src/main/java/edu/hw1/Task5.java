package edu.hw1;

public final class Task5 {

    private static final int BASE = 10;

    public static boolean isPalindromeDescendant(int number) {
        int n = Math.abs(number);
        if (n < BASE) {
            return true;
        }

        while (n >= BASE) {
            int reversed = 0;
            int copy = n;
            int digits = 0;
            while (copy != 0) {
                reversed *= BASE;
                reversed += copy % BASE;
                copy /= BASE;
                ++digits;
            }
            if (n == reversed) {
                return true;
            }
            if (digits % 2 != 0) {
                return false;
            }

            var builder = new StringBuilder();
            while (n != 0) {
                builder.append(n % BASE + (n % (BASE * BASE)) / BASE);
                n /= BASE * BASE;
            }
            n =  Integer.parseInt(builder.reverse().toString());
        }

        return false;
    }

    private Task5() {
    }

}
