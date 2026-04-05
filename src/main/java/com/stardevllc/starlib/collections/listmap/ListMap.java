package com.stardevllc.starlib.collections.listmap;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public interface ListMap<K, V> {
    
    default void addAll(ListMap<K, V> listMap) {
        listMap.forEach(this::add);
    }
    
    default void addAll(Map<K, List<V>> map) {
        map.forEach((k, l) -> l.forEach(v -> add(k, v)));
    }
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(K key);
    
    boolean containsValue(V value);
    
    V get(K key, int index);
    
    List<V> get(K key);
    
    V remove(K key, int index);
    
    boolean remove(K key, V value);
    
    void add(K key, V value);
    
    void clear();
    
    @NotNull Set<K> keySet();
    
    @NotNull Collection<V> values();
    
    boolean equals(Object o);
    
    int hashCode();
    
    void forEach(BiConsumer<K, V> consumer);
}