package com.stardevllc.calculator;

import java.util.*;
import java.util.Map.Entry;

public class ModeCalculator<T> {
    private Map<T, Long> counts = new HashMap<>();
    
    public void add(T count) {
        if (counts.containsKey(count)) {
            counts.put(count, counts.get(count) + 1);
        } else {
            counts.put(count, 1L);
        }
    }
    
    public List<ModeResult<T>> getAll() {
        List<ModeResult<T>> results = new ArrayList<>();
        for (Entry<T, Long> entry : counts.entrySet()) {
            results.add(new ModeResult<>(entry.getKey(), entry.getValue()));
        }
        
        return results;
    }
    
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
    
    public record ModeResult<T>(T key, long count) {}
}
