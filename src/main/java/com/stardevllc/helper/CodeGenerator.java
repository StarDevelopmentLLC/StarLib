package com.stardevllc.helper;

import java.util.Random;

public final class CodeGenerator {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    
    public enum Option {
        LETTERS, UPPERCASE, NUMBERS
    }
    
    public static String generateAllOptions(int length) {
        return generate(length, Option.LETTERS, Option.UPPERCASE, Option.NUMBERS);
    }

    public static String generate(int length, Option... options) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        boolean letters = false, numbers = false, uppercase = false;
        
        for (Option option : options) {
            switch (option) {
                case LETTERS -> letters = true;
                case UPPERCASE -> uppercase = true;
                case NUMBERS -> numbers = true;
            }
        }

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
                return Character.toUpperCase(c);
            }
        }
        return c;
    }
}