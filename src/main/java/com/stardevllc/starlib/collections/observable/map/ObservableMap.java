package com.stardevllc.starlib.collections.observable.map;

import com.stardevllc.starlib.collections.observable.listener.MapChangeListener;
import com.stardevllc.starlib.values.Observable;

import java.util.Map;

/**
 * Represents a map that can be observed for changes
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public interface ObservableMap<K, V> extends Observable, Map<K, V> {
    
    /**
     * Adds a listener that mirrors changes in this observable map to the one passed in.
     *
     * @param map The map to mirror changes into
     * @return The same mapped passed in (For inline registration)
     */
    <M extends Map<K, V> > M addContentMirror(M map);
    
    /**
     * Adds a change listener to this ObservableMap
     *
     * @param listener the listener to add
     */
    void addListener(MapChangeListener<K, V> listener);
    
    /**
     * Removes the change listener from this ObservableMap
     *
     * @param listener The listener to remove
     */
    void removeListener(MapChangeListener<K, V> listener);
}