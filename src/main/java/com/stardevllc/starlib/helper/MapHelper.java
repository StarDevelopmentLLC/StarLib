package com.stardevllc.starlib.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper utilities for maps
 */
public final class MapHelper {
    
    private MapHelper() {}
    
    /**
     * Creates a map with provided values
     *
     * @param firstKey   The first key
     * @param firstValue The second key
     * @param rawValues  The raw values (Must be divisible by 2 and match the types)
     * @param <K>        The key type
     * @param <V>        The value type
     * @return The created map
     */
    public static <K, V> Map<K, V> of(K firstKey, V firstValue, Object... rawValues) {
        Map<K, V> map = new HashMap<>();
        map.put(firstKey, firstValue);
        
        if (rawValues != null) {
            if (rawValues.length % 2 != 0) {
                throw new IllegalArgumentException("Number of raw values must be even");
            }
            
            for (int i = 0; i < rawValues.length; i += 2) {
                try {
                    map.put((K) rawValues[i], (V) rawValues[i + 1]);
                } catch (Throwable t) {
                }
            }
        }
        
        return map;
    }
}
