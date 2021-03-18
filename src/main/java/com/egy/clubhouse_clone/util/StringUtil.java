package com.egy.clubhouse_clone.util;

public class StringUtil {
    public static String camelToSnake(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : s.toCharArray()) {
            char nc = Character.toLowerCase(c);
            if (Character.isUpperCase(c)) {
                stringBuilder.append('_').append(nc);
            } else {
                stringBuilder.append(nc);
            }
        }
        return stringBuilder.toString();
    }
}
