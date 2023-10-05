package hw1;

public class Task2 {

    public static int countDigits(int number) {
        number = Math.abs(number) / 10;

        int result = 1;
        while (number != 0) {
            ++result;
            number /= 10;
        }

        return result;
    }

    private Task2() {
    }

}
