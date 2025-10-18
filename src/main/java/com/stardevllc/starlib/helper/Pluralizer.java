package com.stardevllc.starlib.helper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A class that allows the ability to pluralize words and to customize it as well
 */
public final class Pluralizer {
    private static final List<Character> vowels = List.of('a', 'e', 'i', 'o', 'u');
    private static final List<Function<String, String>> pluralFunctions = new LinkedList<>();
    
    static {
        //Consonant and y ending
        pluralFunctions.add(word -> {
            if (word.charAt(word.length() - 1) == 'y' && !vowels.contains(word.charAt(word.length() - 2))) {
                return word.substring(0, word.length() - 1) + "ies";
            }
            
            return word;
        });
        
        //f and fe endings
        pluralFunctions.add(word -> {
            if (word.endsWith("f")) {
                return word.substring(0, word.length() - 1) + "ves";
            } else if (word.endsWith("fe")) {
                return word.substring(0, word.length() - 2) + "ves";
            }
            
            return word;
        });
        
        //o endings
        pluralFunctions.add(word -> {
            if (word.charAt(word.length() - 1) == 'o') {
                return word + "es";
            }
            
            return word;
        });
        
        //us endings
        pluralFunctions.add(word -> {
            if (word.endsWith("us")) {
                return word.substring(0, word.length() - 2) + "i";
            }
            
            return word;
        });
        
        //is endings
        pluralFunctions.add(word -> {
            if (word.endsWith("is")) {
                return word.substring(0, word.length() - 2) + "es";
            }
            
            return word;
        });
        
        //on endings
        pluralFunctions.add(word -> {
            if (word.endsWith("on")) {
                return word.substring(0, word.length() - 2) + "a";
            }
            
            return word;
        });
        
        //sh, s, ch, x and z ending
        pluralFunctions.add(word -> {
            if (Stream.of("s", "sh", "ch", "x").anyMatch(word::endsWith)) {
                return word + "es";
            } else if (word.endsWith("z")) {
                return word + "zes";
            }
            
            return word;
        });
    }
    
    private final List<Function<String, String>> customPluralFunctions = new LinkedList<>();
    private final Map<String, String> overrides = new HashMap<>();
    
    /**
     * Adds a way to customize pluralization a little bit with a function. <br>
     * Custom functions added using this method takes priority over the default rules
     *
     * @param function The function to add
     */
    public void addFunction(Function<String, String> function) {
        customPluralFunctions.add(function);
    }
    
    /**
     * Adds a plural form for a word. The check for the word is case insensitive. <br>
     * Words provided here skip all rules checks. 
     *
     * @param word       The word that is not case sensitive
     * @param pluralForm The new plural form
     */
    public void addOverride(String word, String pluralForm) {
        overrides.put(word.toLowerCase(), pluralForm);
    }
    
    /**
     * Pluralizes a word using some very basic rules
     *
     * @param word The word
     * @return the result
     */
    public String pluralize(String word) {
        if (overrides.containsKey(word.toLowerCase())) {
            return overrides.get(word.toLowerCase());
        }
        
        for (Function<String, String> function : customPluralFunctions) {
            try {
                String newWord = function.apply(word);
                if (!newWord.equals(word)) {
                    return newWord;
                }
            } catch (Throwable t) {}
        }
        
        for (Function<String, String> function : pluralFunctions) {
            try {
                String newWord = function.apply(word);
                if (!newWord.equals(word)) {
                    return newWord;
                }
            } catch (Throwable t) {}
        }
        
        return word + "s";
    }
}