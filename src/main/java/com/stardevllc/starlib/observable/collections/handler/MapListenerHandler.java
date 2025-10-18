package com.stardevllc.starlib.observable.collections.handler;

import com.stardevllc.starlib.observable.collections.ObservableMap;
import com.stardevllc.starlib.observable.collections.listener.MapChangeListener;

import java.util.ArrayList;
import java.util.List;

public class MapListenerHandler<K, V> {
    private final List<MapChangeListener<K, V>> listeners = new ArrayList<>();
    
    public void addListener(MapChangeListener<K, V> listener) {
        this.listeners.add(listener);
    }
    
    public void removeListener(MapChangeListener<K, V> listener) {
        this.listeners.remove(listener);
    }
    
    public void handleChange(ObservableMap<K, V> collection, K key, V added, V removed) {
        for (MapChangeListener<K, V> listener : listeners) {
            listener.changed(collection, key, added, removed);
        }
    }
}