package com.stardevllc.starlib.table.set;

import com.stardevllc.starlib.table.Table;

import java.util.*;

public interface SetTable<R, C, V> extends Table<R, C, Set<V>> {
    void add(R row, C column, V value);
    
    void remove(R row, C column, V value);
    
    default boolean containElement(Object element) {
        for (V v : elements()) {
            if (element.equals(v)) {
                return true;
            }
        }
        
        return false;
    }
    
    Collection<V> elements();
}