package com.stardevllc.starlib.observable.collections;

import java.util.*;

public class ObservableHashMap<K, V> extends AbstractObservableMap<K, V> {
    
    private final HashMap<K, V> backingHashMap = new HashMap<>();
    
    public ObservableHashMap() {
    }
    
    public ObservableHashMap(Map<K, V> map) {
        this.backingHashMap.putAll(map);
    }
    
    @Override
    protected Map<K, V> getBackingMap() {
        return backingHashMap;
    }
    
    @Override
    public Set<K> keySet() {
        return new ObservableHashSet<>(this.backingHashMap.keySet());
    }

    @Override
    public Collection<V> values() {
        return new ObservableLinkedList<>(this.backingHashMap.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new ObservableHashSet<>(this.backingHashMap.entrySet());
    }
}
