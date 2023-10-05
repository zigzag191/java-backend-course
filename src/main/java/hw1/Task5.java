package hw1;

public class Task5 {

    public static boolean isPalindromeDescendant(int number) {
        number = Math.abs(number);
        if (number < 10) {
            return true;
        }

        while (number > 9) {
            int reversed = 0;
            int copy = number;
            int digits = 0;
            while (copy != 0) {
                reversed *= 10;
                reversed += copy % 10;
                copy /= 10;
                ++digits;
            }
            if (number == reversed) {
                return true;
            }
            if (digits % 2 != 0) {
                return false;
            }

            var builder = new StringBuilder();
            while (number != 0) {
                builder.append(number % 10 + (number % 100) / 10);
                number /= 100;
            }
            number =  Integer.parseInt(builder.toString());

        }
        return false;
    }

    private Task5() {
    }

}
