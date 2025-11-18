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
     * Adds a listener that mirrors changes in this observable map to the one passed in.
     *
     * @param map The map to mirror changes into
     * @return The same mapped passed in (For inline registration)
     */
    default <M extends Map<K, V> > M addContentMirror(M map) {
        map.putAll(this);
        getHandler().addListener(c -> {
            if (c.added() != null && !map.containsValue(c.added())) {
                map.put(c.key(), c.added());
            } else if (c.removed() != null && c.added() == null) {
                map.remove(c.key());
            }
        });
        return map;
    }
    
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
    default void addListener(MapChangeListener<K, V> listener) {
        getHandler().addListener(listener);
    }
    
    /**
     * Removes the change listener from this ObservableMap
     *
     * @param listener The listener to remove
     */
    default void removeListener(MapChangeListener<K, V> listener) {
        getHandler().removeListener(listener);
    }
    
    @SuppressWarnings("ClassCanBeRecord")
    class ObservableEntry<K, V> implements Map.Entry<K, V> {
        private final ObservableMap<K, V> backingMap;
        private final Map.Entry<K, V> backingEntry;
        
        public ObservableEntry(ObservableMap<K, V> backingMap, Map.Entry<K, V> backingEntry) {
            this.backingMap = backingMap;
            this.backingEntry = backingEntry;
        }
        
        @Override
        public K getKey() {
            return backingEntry.getKey();
        }
        
        @Override
        public V getValue() {
            return backingEntry.getValue();
        }
        
        @Override
        public V setValue(V value) {
            if (backingEntry.getValue() != null && !backingEntry.getValue().equals(value)) {
                if (!backingMap.getHandler().handleChange(backingMap, backingEntry.getKey(), value, backingEntry.getValue())) {
                    return backingEntry.setValue(value);
                }
            }
            return null;
        }
    }
}