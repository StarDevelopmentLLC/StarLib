package com.stardevllc.starlib.observable.collections.impl.map;

import com.stardevllc.starlib.observable.collections.ObservableMap;
import com.stardevllc.starlib.observable.collections.listeners.MapChangeListener;
import com.stardevllc.starlib.observable.collections.listeners.MapChangeListener.Change;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw new UnsupportedOperationException("Cannot call replaceAll on an ObservableMap");
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V v = this.value.putIfAbsent(key, value);
        Change<K, V> change = new Change<>(this, key, value, v);
        for (MapChangeListener<K, V> changeListener : this.changeListeners) {
            changeListener.onChange(change);
        }
        return v;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean remove = this.value.remove(key, value);
        if (remove) {
            Change<K, V> change = new Change<>(this, (K) key, null, (V) value);
            this.changeListeners.forEach(listener -> listener.onChange(change));
        }
        return remove;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        boolean replace = this.value.replace(key, oldValue, newValue);
        if (replace) {
            Change<K, V> change = new Change<>(this, key, oldValue, newValue);
            this.changeListeners.forEach(listener -> listener.onChange(change));
        }
        return replace;
    }

    @Override
    public V replace(K key, V value) {
        return ObservableMap.super.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        throw new UnsupportedOperationException("Cannot call computeIfAbsent on an ObservableMap");
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException("Cannot call computeIfPresent on an ObservableMap");
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException("Cannot call compute on an ObservableMap");
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException("Cannot call merge on an ObservableMap");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.value.putAll(m);
        m.forEach((key, value) -> {
            Change<K, V> change = new Change<>(this, key, value, null);
            this.changeListeners.forEach(listener -> listener.onChange(change));
        });
    }
}
