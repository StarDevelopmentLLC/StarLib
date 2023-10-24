package com.stardevllc.starlib.observable.collections.impl.map;

import com.stardevllc.starlib.observable.collections.ObservableMap;
import com.stardevllc.starlib.observable.collections.listeners.MapChangeListener;
import com.stardevllc.starlib.observable.collections.listeners.MapChangeListener.Change;

import java.util.*;
import java.util.function.BiConsumer;

public abstract class AbstractObservableMap<K, V> implements ObservableMap<K, V> {
    
    protected Map<K, V> value;
    protected List<MapChangeListener<K, V>> changeListeners = new ArrayList<>();

    public AbstractObservableMap(Map<K, V> value) {
        this.value = value;
    }

    @Override
    public void addChangeListener(MapChangeListener<K, V> listener) {
        changeListeners.add(listener);
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return value.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.value.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return value.get(key);
    }

    @Override
    public V put(K key, V value) {
        V previous = this.value.put(key, value);
        Change<K, V> change = new Change<>(this, key, value, previous);
        this.changeListeners.forEach(listener -> listener.onChange(change));
        return previous;
    }

    @Override
    public V remove(Object key) {
        V previous = this.value.remove(key);
        Change<K, V> change = new Change<>(this, (K) key, null, previous);
        this.changeListeners.forEach(listener -> listener.onChange(change));
        return previous;
    }

    @Override
    public void clear() {
        this.value.clear();
    }

    @Override
    public Set<K> keySet() {
        return new HashSet<>(value.keySet());
    }

    @Override
    public Collection<V> values() {
        return new ArrayList<>(value.values());
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new HashSet<>(value.entrySet());
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return value.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        value.forEach(action);
    }
}
