package com.stardevllc.starlib.table;

import com.stardevllc.starlib.tuple.triple.Triple;

import java.util.*;

public interface Table<R, C, V> {
    Set<? extends Cell<R, C, V>> getCellSet();
    
    void clear();
    
    Map<R, V> column(C columnKey);
    
    Set<C> columnKeySet();
    
    Map<C, Map<R, V>> columnMap();
    
    boolean contains(Object rowKey, Object columnKey);
    
    boolean containsColumn(Object columnKey);
    
    boolean containsRow(Object rowKey);
    
    boolean containsValue(Object value);
    
    V get(Object rowKey, Object columnKey);
    
    boolean isEmpty();
    
    V put(R rowKey, C columnKey, V value);
    
    default V put(Cell<R, C, V> cell) {
        return put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
    }
    
    default V put(Triple<R, C, V> triple) {
        return put(triple.getLeft(), triple.getMiddle(), triple.getRight());
    }
    
    void putAll(Table<? extends R, ? extends C, ? extends V> table);
    
    default void putAll(Collection<? extends Triple<? extends R, ? extends C, ? extends V>> collection) {
        collection.forEach(t -> put((Triple<R, C, V>) t));
    }
    
    V remove(Object rowKey, Object columnKey);
    
    Map<C, V> remove(Object rowKey);
    
    Map<C, V> row(R rowKey);
    
    Set<R> rowKeySet();
    
    Map<R, Map<C, V>> rowMap();
    
    int size();
    
    Collection<V> values();
    
    interface Cell<R, C, V> extends Triple<R, C, V> {
        boolean equals(Object obj);
        int hashCode();
        C getColumnKey();
        R getRowKey();
        V getValue();
        
        @Override
        default R getLeft() {
            return getRowKey();
        }
        
        @Override
        default C getMiddle() {
            return getColumnKey();
        }
        
        @Override
        default V getRight() {
            return getValue();
        }
    }
}
