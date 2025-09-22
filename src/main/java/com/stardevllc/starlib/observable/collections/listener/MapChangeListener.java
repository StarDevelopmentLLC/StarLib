package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.eventbus.SubscribeEvent;
import com.stardevllc.starlib.observable.collections.event.MapChangeEvent;

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
     * Handler method
     *
     * @param event The event information
     */
    void changed(MapChangeEvent<K, V> event);
}
