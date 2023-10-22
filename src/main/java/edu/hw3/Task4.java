package edu.hw3;

import java.util.Comparator;
import java.util.TreeMap;

@SuppressWarnings("MagicNumber")
public final class Task4 {

    public static final TreeMap<Integer, String> ROMAN_NUMBERS;
    public static final int MAX_CONVERTIBLE_NUMBER = 3999;

    static {
        ROMAN_NUMBERS = new TreeMap<>(Comparator.reverseOrder());
        ROMAN_NUMBERS.put(1000, "M");
        ROMAN_NUMBERS.put(900, "CM");
        ROMAN_NUMBERS.put(500, "D");
        ROMAN_NUMBERS.put(400, "CD");
        ROMAN_NUMBERS.put(100, "C");
        ROMAN_NUMBERS.put(90, "XC");
        ROMAN_NUMBERS.put(50, "L");
        ROMAN_NUMBERS.put(40, "XL");
        ROMAN_NUMBERS.put(10, "X");
        ROMAN_NUMBERS.put(9, "IX");
        ROMAN_NUMBERS.put(5, "V");
        ROMAN_NUMBERS.put(4, "IV");
        ROMAN_NUMBERS.put(1, "I");
    }

    public static String convertToRoman(int number) {
        if (number < 1 || number > MAX_CONVERTIBLE_NUMBER) {
            throw new IllegalArgumentException("n should be in range [1, 3999]");
        }

        int n = number;
        var builder = new StringBuilder();
        for (var romanNumber : ROMAN_NUMBERS.entrySet()) {
            while (n >= romanNumber.getKey()) {
                n -= romanNumber.getKey();
                builder.append(romanNumber.getValue());
            }
        }

        return builder.toString();
    }

    private Task4() {
    }

}
