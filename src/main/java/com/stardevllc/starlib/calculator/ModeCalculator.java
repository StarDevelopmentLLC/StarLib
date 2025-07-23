package com.stardevllc.starlib.calculator;

import java.util.*;
import java.util.Map.Entry;

/**
 * <pre>A class that helps to track and calculate the mode of a set of data.
 * This class will be replaced or removed eventually </pre>
 *
 * @param <T> The object type for the mode
 * @deprecated This class is deprecated. Use Google Guava's Multiset. It's just better
 */
@Deprecated
public class ModeCalculator<T> {
    private Map<T, Long> counts = new HashMap<>();
    
    /**
     * Adds an element to the backing map for calculation
     *
     * @param count The object to count
     */
    public void add(T count) {
        if (counts.containsKey(count)) {
            counts.put(count, counts.get(count) + 1);
        } else {
            counts.put(count, 1L);
        }
    }
    
    /**
     * Gets all of the objects in this Calculator as a {@link ModeResult}
     *
     * @return The List of results
     */
    public List<ModeResult<T>> getAll() {
        List<ModeResult<T>> results = new ArrayList<>();
        for (Entry<T, Long> entry : counts.entrySet()) {
            results.add(new ModeResult<>(entry.getKey(), entry.getValue()));
        }
        
        return results;
    }
    
    /**
     * Calculates the Mode of the data and returns as a {@link ModeResult}
     *
     * @return The mode
     */
    public ModeResult<T> get() {
        Map.Entry<T, Long> highest = null;
        for (Map.Entry<T, Long> entry : counts.entrySet()) {
            if (highest == null || entry.getValue() > highest.getValue()) {
                highest = entry;
            }
        }
        
        if (highest == null) {
            return new ModeResult<>(null, 0);
        }
        
        return new ModeResult<>(highest.getKey(), highest.getValue());
    }
    
    public record ModeResult<T>(T key, long count) {
    }
}
