package edu.hw3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Task2 {

    public static List<String> clusterize(String s) {
        Objects.requireNonNull(s);
        if (s.isEmpty()) {
            return Collections.emptyList();
        }

        int stack = 0;
        var result = new ArrayList<String>();
        var builder = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch == '(') {
                ++stack;
            } else if (ch == ')') {
                --stack;
            } else {
                throw new IllegalArgumentException("input string must contain only parenthesis");
            }
            if (stack < 0) {
                break;
            }
            builder.append(ch);
            if (stack == 0) {
                result.add(builder.toString());
                builder.setLength(0);
            }
        }

        if (stack != 0) {
            throw new IllegalArgumentException("parenthesis in string do not match");
        }

        return result;
    }

    private Task2() {
    }

}
