package hw1;

import java.util.Objects;

public class Task4 {

    public static String fixString(String str) {
        Objects.requireNonNull(str);
        if (str.length() == 0) {
            return "";
        }

        var builder = new StringBuilder();
        for (int i = 0; i < str.length() - 1; i += 2) {
            builder.append(str.charAt(i + 1)).append(str.charAt(i));
        }

        if (str.length() % 2 != 0) {
            builder.append(str.charAt(str.length() - 1));
        }

        return builder.toString();
    }

    private Task4() {
    }

}
