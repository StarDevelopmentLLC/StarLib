package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.eventbus.SubscribeEvent;
import com.stardevllc.starlib.observable.collections.map.ObservableMap;
import com.stardevllc.starlib.value.WritableBooleanValue;
import com.stardevllc.starlib.value.impl.SimpleBooleanValue;

/**
 * Listener for map change events
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@SubscribeEvent
@FunctionalInterface
public interface MapChangeListener<K, V> {
    
    record Change<K, V>(ObservableMap<K, V> map, K key, V added, V removed, WritableBooleanValue cancelled) {
        public Change(ObservableMap<K, V> map, K key, V added, V removed, WritableBooleanValue cancelled) {
            this.map = map;
            this.key = key;
            this.added = added;
            this.removed = removed;
            if (cancelled != null) {
                this.cancelled = cancelled;
            } else {
                this.cancelled = new SimpleBooleanValue();
            }
        }
        
        public Change(ObservableMap<K, V> map, K key, V added, V removed) {
            this(map, key, added, removed, null);
        }
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
