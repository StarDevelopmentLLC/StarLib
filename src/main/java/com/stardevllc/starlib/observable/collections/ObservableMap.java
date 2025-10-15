package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.event.MapChangeEvent;
import com.stardevllc.starlib.observable.collections.listener.MapChangeListener;

import java.util.Map;

/**
 * Represents a map that can be observed for changes
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@SuppressWarnings("rawtypes")
public interface ObservableMap<K, V> extends Observable, Map<K, V> {
    /**
     * The event bus that controls listening for changes
     *
     * @return The EventBus
     */
    IEventBus<MapChangeEvent> eventBus();
    
    /**
     * Adds a change listener to this ObservableMap
     *
     * @param listener the listener to add
     */
    void addChangeListener(MapChangeListener<K, V> listener);
    
    /**
     * Removes the change listener from this ObservableMap
     *
     * @param listener The listener to remove
     */
    void removeChangeListener(MapChangeListener<K, V> listener);
}