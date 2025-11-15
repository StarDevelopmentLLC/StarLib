package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.eventbus.SubscribeEvent;
import com.stardevllc.starlib.observable.collections.ObservableMap;

/**
 * Listener for map change events
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@SubscribeEvent
@FunctionalInterface
public interface MapChangeListener<K, V> {
    
    record Change<K, V>(ObservableMap<K, V> map, K key, V added, V removed) {
    }
    
    /**
     * Called when changes occur
     *
     * @param change The change information
     */
    void changed(Change<K, V> change);
    
    /**
     * Called when changes occur. This passes the arguments into a {@link Change} and then to the {@link #changed(Change)} method
     *
     * @param map     The Map
     * @param key     The key
     * @param added   The added value (if any)
     * @param removed The removed value (if any)
     */
    default void changed(ObservableMap<K, V> map, K key, V added, V removed) {
        changed(new Change<>(map, key, added, removed));
    }
}
