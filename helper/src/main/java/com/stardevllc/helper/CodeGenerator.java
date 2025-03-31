package com.stardevllc.helper;

import java.util.Random;

public final class CodeGenerator {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    
    public static String generate(int length) {
        return generate(length, true, true, true);
    }

    public static String generate(int length, boolean letters, boolean uppercase, boolean numbers) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        if (!letters && !numbers) {
            throw new IllegalArgumentException("You must have at least letters and/or numbers in the code.");
        }

        for (int i = 0; i < length; i++) {
            if (letters && numbers) {
                if (random.nextBoolean()) {
                    sb.append(generateLetter(random, uppercase));
                } else {
                    sb.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
                }
            } else if (letters) {
                sb.append(generateLetter(random, uppercase));
            } else {
                sb.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
            }
        }

        return sb.toString();
    }

    private static char generateLetter(Random random, boolean uppercase) {
        char c = LETTERS.charAt(random.nextInt(LETTERS.length()));
        if (uppercase) {
            if (random.nextBoolean()) {
                c = Character.toUpperCase(c);
            }
        }
        return c;
    }
}