package com.stardevllc.starlib.table;

import java.util.*;

/**
 * This is different from the google guava table as it is a Map<R, Map<C, List<V>>> implementation
 *
 * @param <R> The row type
 * @param <C> The column type
 * @param <V> The value type
 */
public class ListTable<R, C, V> {
    private final Map<R, Map<C, List<V>>> backing = new HashMap<>();
    
    public void removeAll(R r) {
        backing.remove(r);
    }
    
    public void removeAll(R r, C c) {
        Map<C, List<V>> cMap = backing.get(r);
        if (cMap != null) {
            cMap.remove(c);
        }
    }
    
    public void remove(R r, C c, V v) {
        List<V> list = get(r, c);
        if (list != null) {
            list.remove(v);
        }
    }
    
    public void remove(R r, C c, int index) {
        List<V> list = get(r, c);
        if (list != null && !list.isEmpty() && list.size() >= index) { //TODO May need to rework this
            list.remove(index);
        }
    }
    
    public List<V> get(R r, C c) {
        Map<C, List<V>> cMap = backing.get(r);
        if (cMap == null || cMap.isEmpty()) {
            return List.of();
        }
        
        return cMap.getOrDefault(c, List.of());
    }
    
    public V get(R r, C c, int index) {
        List<V> list = get(r, c);
        if (list.isEmpty()) {
            return null;
        }
        
        return list.get(index);
    }
    
    public void add(R row, C column, V value) {
        backing.computeIfAbsent(row, r -> new HashMap<>()).computeIfAbsent(column, c -> new ArrayList<>()).add(value);
    }
    
    public void addUnique(R row, C column, V value) {
        List<V> list = backing.computeIfAbsent(row, r -> new HashMap<>()).computeIfAbsent(column, c -> new ArrayList<>());
        if (!list.contains(value)) {
            list.add(value);
        }
    }
    
    public V getRandom(R row, C column, Random random) {
        List<V> list = get(row, column);
        return list.get(random.nextInt(list.size()));
    }
}