package com.stardevllc.starlib.math;

import java.util.LinkedHashMap;
import java.util.Map;

public class RomanNumerals {

    private static final Map<String, Integer> values = new LinkedHashMap<>();

    static {
        values.put("M", 1000);
        values.put("CM", 900);
        values.put("D", 500);
        values.put("CD", 400);
        values.put("C", 100);
        values.put("XC", 90);
        values.put("L", 50);
        values.put("XL", 40);
        values.put("X", 10);
        values.put("IX", 9);
        values.put("V", 5);
        values.put("IV", 4);
        values.put("I", 1);
    }

    public static String romanNumerals(int number) {
        StringBuilder res = new StringBuilder();
        for (Map.Entry<String, Integer> entry : values.entrySet()) {
            int matches = number / entry.getValue();
            res.append(String.valueOf(entry.getKey()).repeat(Math.max(0, matches)));
            number = number % entry.getValue();
        }
        return res.toString();
    }
}