package edu.hw1;

public final class Task7 {

    public static int rotateLeft(int n, int shift) {
        assureNisValid(n);

        if (shift == 0) {
            return n;
        }
        if (shift < 0) {
            return rotateRight(n, -shift);
        }

        char[] bits = Integer.toBinaryString(n).toCharArray();
        int pivot = shift % bits.length;
        rotate(bits, 0, pivot - 1);
        rotate(bits, pivot, bits.length - 1);
        rotate(bits, 0, bits.length - 1);

        return Integer.parseInt(new String(bits), 2);
    }

    public static int rotateRight(int n, int shift) {
        assureNisValid(n);

        if (shift == 0) {
            return n;
        }
        if (shift < 0) {
            return rotateLeft(n, -shift);
        }

        char[] bits = Integer.toBinaryString(n).toCharArray();
        int pivot = shift % bits.length;
        rotate(bits, 0, bits.length - pivot - 1);
        rotate(bits, bits.length - pivot, bits.length - 1);
        rotate(bits, 0, bits.length - 1);

        return Integer.parseInt(new String(bits), 2);
    }

    private static void rotate(char[] bits, int start, int end) {
        for (int l = start, r = end; l < r; ++l, --r) {
            char tmp = bits[l];
            bits[l] = bits[r];
            bits[r] = tmp;
        }
    }

    private static void assureNisValid(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive number");
        }
    }

    private Task7() {
    }

}
