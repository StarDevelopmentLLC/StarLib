package com.stardevllc.starlib.table;

import com.stardevllc.starlib.tuple.triple.Triple;

import java.util.*;
import java.util.function.BiFunction;

public interface Table<R, C, V> {
    Set<? extends Cell<R, C, V>> getCellSet();
    
    void clear();
    
    Map<R, V> column(C columnKey);
    
    Set<C> columnKeySet();
    
    Map<C, Map<R, V>> columnMap();
    
    boolean contains(R rowKey, C columnKey);
    
    boolean containsColumn(C columnKey);
    
    boolean containsRow(R rowKey);
    
    boolean containsValue(V value);
    
    V get(R rowKey, C columnKey);
    
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
    
    default V computeIfAbsent(R row, C column, BiFunction<R, C, V> mapper) {
        V value = get(row, column);
        if (value == null) {
            value = mapper.apply(row, column);
            put(row, column, value);
        }
        
        return value;
    }
    
    default V putIfAbsent(R row, C column, V value) {
        V v = get(row, column);
        if (v == null) {
            put(row, column, value);
        }
        
        return v;
    }
    
    V remove(R rowKey, C columnKey);
    
    Map<C, V> remove(R rowKey);
    
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
