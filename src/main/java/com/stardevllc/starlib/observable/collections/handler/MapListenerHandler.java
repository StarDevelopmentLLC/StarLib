package com.stardevllc.starlib.observable.collections.handler;

import com.stardevllc.starlib.observable.collections.map.ObservableMap;
import com.stardevllc.starlib.observable.collections.listener.MapChangeListener;
import com.stardevllc.starlib.observable.collections.listener.MapChangeListener.Change;

import java.util.*;

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
     * Handles a change in the map
     *
     * @param change The change information
     * @return If the change was cancelled
     */
    public boolean handleChange(MapChangeListener.Change<K, V> change) {
        boolean cancelled = false;
        for (MapChangeListener<K, V> listener : listeners) {
            listener.changed(change);
            if (!cancelled) {
                cancelled = change.cancelled().get();
            }
        }
        return cancelled;
    }
    
    /**
     * Handles the change. Delegates to {@link #handleChange(Change)}
     *
     * @param collection The map that changed
     * @param key        The key that was operated on
     * @param added      The added value
     * @param removed    The removed (or replaced) value
     */
    public boolean handleChange(ObservableMap<K, V> collection, K key, V added, V removed) {
        return handleChange(new Change<>(collection, key, added, removed));
    }
}