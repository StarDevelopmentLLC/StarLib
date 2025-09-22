package com.stardevllc.starlib.observable.collections;

import java.util.*;

/**
 * Represents an ObservableHashMap
 *
 * @param <K> The Key type
 * @param <V> The vlaue type
 */
public class ObservableHashMap<K, V> extends AbstractObservableMap<K, V> {
    
    private final HashMap<K, V> backingHashMap = new HashMap<>();
    
    /**
     * Creates an empty observable hash map
     */
    public ObservableHashMap() {
    }
    
    /**
     * Creates an observable hash map from exsting map
     *
     * @param map The map
     */
    public ObservableHashMap(Map<K, V> map) {
        this.backingHashMap.putAll(map);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<K, V> getBackingMap() {
        return backingHashMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        return new ObservableHashSet<>(this.backingHashMap.keySet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        return new ObservableLinkedList<>(this.backingHashMap.values());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new ObservableHashSet<>(this.backingHashMap.entrySet());
    }
}
