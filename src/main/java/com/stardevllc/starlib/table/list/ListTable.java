package com.stardevllc.starlib.table.list;

import com.stardevllc.starlib.table.Table;

import java.util.*;

public interface ListTable<R, C, V> extends Table<R, C, List<V>> {
    void add(R row, C column, V value);
    
    void add(R row, C column, int index, V value);
    
    V set(R row, C column, int index, V value);
    
    V remove(R row, C column, int index);
    
    void remove(R row, C column, V value);
    
    V get(R row, C column, int index);
    
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