package com.stardevllc.starlib.observable.collections.map;

import com.stardevllc.starlib.observable.collections.set.ObservableHashSet;

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
        if (map != null) {
            this.backingHashMap.putAll(map);
        }
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
        ObservableHashSet<K> keySet = new ObservableHashSet<>(this.backingHashMap.keySet());
        keySet.addListener(c -> {
            if (c.removed() != null) {
                remove(c.removed());
            }
        });
        return keySet;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        ObservableHashSet<Map.Entry<K, V>> entrySet = new ObservableHashSet<>();
        for (Map.Entry<K, V> entry : this.backingHashMap.entrySet()) {
            entrySet.add(new ObservableEntry<>(this, entry));
        }
        
        return entrySet;
    }
}
