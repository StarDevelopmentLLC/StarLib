package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.handler.MapListenerHandler;
import com.stardevllc.starlib.observable.collections.listener.MapChangeListener;

import java.util.Map;

/**
 * Represents a map that can be observed for changes
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public interface ObservableMap<K, V> extends Observable, Map<K, V> {
    
    /**
     * Gets the handler for change listeners
     *
     * @return The handler
     */
    MapListenerHandler<K, V> getHandler();
    
    /**
     * Adds a change listener to this ObservableMap
     *
     * @param listener the listener to add
     */
    default void addChangeListener(MapChangeListener<K, V> listener) {
        getHandler().addListener(listener);
    }
    
    /**
     * Removes the change listener from this ObservableMap
     *
     * @param listener The listener to remove
     */
    default void removeChangeListener(MapChangeListener<K, V> listener) {
        getHandler().removeListener(listener);
    }
}