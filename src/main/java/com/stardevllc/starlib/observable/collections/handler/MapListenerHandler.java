package com.stardevllc.starlib.observable.collections.handler;

import com.stardevllc.starlib.observable.collections.ObservableMap;
import com.stardevllc.starlib.observable.collections.listener.MapChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A handler for map listeners
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@SuppressWarnings("RedundantNoArgConstructor")
public class MapListenerHandler<K, V> {
    private final List<MapChangeListener<K, V>> listeners = new ArrayList<>();
    
    /**
     * Constructs a new handler
     */
    public MapListenerHandler() {
    }
    
    /**
     * Adds a listener to this handler
     *
     * @param listener The listener
     */
    public void addListener(MapChangeListener<K, V> listener) {
        this.listeners.add(listener);
    }
    
    /**
     * Removes a listener from this handler
     *
     * @param listener The listener
     */
    public void removeListener(MapChangeListener<K, V> listener) {
        this.listeners.remove(listener);
    }
    
    /**
     * Handles the change
     *
     * @param collection The map that changed
     * @param key        The key that was operated on
     * @param added      The added value
     * @param removed    The removed (or replaced) value
     */
    public void handleChange(ObservableMap<K, V> collection, K key, V added, V removed) {
        for (MapChangeListener<K, V> listener : listeners) {
            listener.changed(collection, key, added, removed);
        }
    }
}