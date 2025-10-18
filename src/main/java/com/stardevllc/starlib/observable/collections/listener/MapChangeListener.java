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
    /**
     * Called when changes occur
     *
     * @param map     The map that changed
     * @param key     The key that changed
     * @param added   The value added
     * @param removed The value removed
     */
    void changed(ObservableMap<K, V> map, K key, V added, V removed);
}
