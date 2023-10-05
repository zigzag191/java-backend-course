package hw1;

public class Task7 {

    public static int rotateLeft(int n, int shift) {
        char[] bits = Integer.toBinaryString(n).toCharArray();
        rotate(bits, 0, shift - 1);
        rotate(bits, shift, bits.length - 1);
        rotate(bits, 0, bits.length - 1);
        return Integer.parseInt(new String(bits), 2);
    }

    public static int rotateRight(int n, int shift) {
        char[] bits = Integer.toBinaryString(n).toCharArray();
        rotate(bits, 0, bits.length - shift - 1);
        rotate(bits, bits.length - shift, bits.length - 1);
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

    private Task7() {
    }

}
