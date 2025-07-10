package com.stardevllc.helper;

import java.util.*;
import java.util.regex.Pattern;

public class StringHelper {
    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
    
    private static Map<String, UUID> uuidCache = new HashMap<>();

    public static String pluralize(String word) {
        if (word.charAt(word.length() - 1) == 's') {
            word += "'";
        } else {
            word += "'s";
        }
        return word;
    }
    
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
    
    public static String join(Collection<?> collection, String separator) {
        Iterator<?> iterator = collection.iterator();
        if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (first == null) {
                return "";
            }
            if (!iterator.hasNext()) {
                return first.toString();
            } else {
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
        }
    }

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

    public static String getStringSafe(String value) {
        return value == null ? "" : value;
    }
    
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
