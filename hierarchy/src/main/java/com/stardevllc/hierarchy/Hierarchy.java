package com.stardevllc.hierarchy;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class Hierarchy<T> {
    private SortedMap<Integer, T> entries = new TreeMap<>();
    
    //This method initializes the hierarchy itself and establishes the entries
    public abstract void init();
    
    public int getWeight(T object) {
        for (Map.Entry<Integer, T> entry : entries.entrySet()) {
            if (entry.getValue().equals(object)) {
                return entry.getKey();
            }
        }
        
        return Integer.MIN_VALUE;
    }
    
    public T set(int weight, T object) {
        if (entries.containsValue(object)) {
            entries.remove(getWeight(object));
        }
        
        return entries.put(weight, object);
    }
}