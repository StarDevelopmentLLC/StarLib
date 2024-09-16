package com.stardevllc.starlib.numbers;

import java.util.HashMap;
import java.util.Map;

public class ModeCalculator {
    private Map<Long, Long> counts = new HashMap<>();
    
    public void add(long count) {
        if (counts.containsKey(count)) {
            counts.put(count, counts.get(count) + 1);
        } else {
            counts.put(count, 1L);
        }
    }
    
    public long[] get() {
        Map.Entry<Long, Long> highest = null;
        for (Map.Entry<Long, Long> entry : counts.entrySet()) {
            if (highest == null || entry.getValue() > highest.getValue()) {
                highest = entry;
            }
        }
        
        if (highest == null) {
            return new long[2];
        }
        
        return new long[] {highest.getKey(), highest.getValue()};
    }
}
