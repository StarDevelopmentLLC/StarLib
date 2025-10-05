package com.stardevllc.starlib.helper;

import java.util.*;

/**
 * Utility class that generates random alphanumeric codes
 */
public final class CodeGenerator {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    
    private CodeGenerator() {}
    
    /**
     * The generation options
     */
    public enum Option {
        /**
         * a-z letters are used when present
         */
        LETTERS,
        
        /**
         * Randomly capitalize letters if present (50-50)
         */
        UPPERCASE,
        
        /**
         * 0-9 numbers are used when present
         */
        NUMBERS
    }
    
    /**
     * Generates a code with all options
     * @param length The length of the code string
     * @return The generated code
     */
    public static String generateAllOptions(int length) {
        return generate(length, Option.LETTERS, Option.UPPERCASE, Option.NUMBERS);
    }
    
    /**
     * Generates a code
     * @param length The length
     * @param options The options for generation
     * @return The generated code
     */
    public static String generate(int length, Option... options) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        Set<Option> optionSet = EnumSet.noneOf(Option.class);
        
        if (options != null) {
            optionSet.addAll(List.of(options));
        }
        
        boolean letters = optionSet.contains(Option.LETTERS), numbers = optionSet.contains(Option.NUMBERS), uppercase = optionSet.contains(Option.UPPERCASE);

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