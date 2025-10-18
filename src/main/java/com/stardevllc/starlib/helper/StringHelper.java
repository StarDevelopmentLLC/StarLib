package com.stardevllc.starlib.helper;

import java.util.*;
import java.util.regex.Pattern;

/**
 * A collection of utilities for Strings
 */
public final class StringHelper {
    
    private StringHelper() {
    }
    
    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
    private static final Map<String, UUID> uuidCache = new HashMap<>();
    
    /**
     * Pluralizes a word using some very basic rules
     *
     * @param word The word
     * @return the result
     */
    public static String pluralize(String word) {
        return new Pluralizer().pluralize(word);
    }
    
    /**
     * Capitalize every word in a string. This treats underscores as a space
     *
     * @param string The string
     * @return The result
     */
    public static String titlize(String string) {
        string = string.toLowerCase();
        String[] words = string.split("_");
        StringBuilder name = new StringBuilder();
        for (int w = 0; w < words.length; w++) {
            String word = words[w];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                if (i == 0) {
                    sb.append(Character.toUpperCase(word.charAt(0)));
                } else {
                    sb.append(word.charAt(i));
                }
            }
            name.append(sb);
            if (w < words.length - 1) {
                name.append(" ");
            }
        }
        
        return name.toString();
    }
    
    /**
     * Joins the array using a separator
     *
     * @param array     The array to join
     * @param separator The separator to use
     * @return The result
     */
    public static String join(Object[] array, String separator) {
        if (array == null || array.length == 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(array[i]);
        }
        
        return sb.toString();
    }
    
    /**
     * Joins a collection using a separator
     *
     * @param collection The collection
     * @param separator  The seperator
     * @return The result
     */
    public static String join(Collection<?> collection, String separator) {
        Iterator<?> iterator = collection.iterator();
        if (!iterator.hasNext()) {
            return "";
        }
        
        Object first = iterator.next();
        if (first == null) {
            return "";
        }
        
        if (!iterator.hasNext()) {
            return first.toString();
        }
        
        StringBuilder buf = new StringBuilder();
        buf.append(first);
        
        while (iterator.hasNext()) {
            buf.append(separator);
            
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        
        return buf.toString();
    }
    
    /**
     * Converts the string into a uuid<br>
     * The string can have dashes or not. This method will detect it
     *
     * @param id The string version of a uuid
     * @return The result
     */
    public static UUID toUUID(String id) {
        if (uuidCache.containsKey(id)) {
            return uuidCache.get(id);
        }
        
        if (UUID_PATTERN.matcher(id).matches()) {
            UUID uuid = UUID.fromString(id);
            uuidCache.put(id, uuid);
            return uuid;
        }
        
        id = id.substring(0, 8) + "-" +
                id.substring(8, 12) + "-" +
                id.substring(12, 16) + "-" +
                id.substring(16, 20) + "-" +
                id.substring(20, 32);
        
        UUID uuid = UUID.fromString(id);
        uuidCache.put(id, uuid);
        return uuid;
    }
    
    /**
     * A null safe utility method for strings
     *
     * @param value The value
     * @return The value or an empty string if null
     */
    public static String getStringSafe(String value) {
        return value == null ? "" : value;
    }
    
    /**
     * Null safe version of {@link String#isEmpty()}
     *
     * @param str The string
     * @return If it is empty, returning true if it is null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
