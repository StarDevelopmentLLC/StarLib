package com.stardevllc.starlib.numbers;

import java.util.HashMap;
import java.util.Map;

public class ModeCalculator<T> {
    private Map<T, Long> counts = new HashMap<>();
    
    public void add(T count) {
        if (counts.containsKey(count)) {
            counts.put(count, counts.get(count) + 1);
        } else {
            counts.put(count, 1L);
        }
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
