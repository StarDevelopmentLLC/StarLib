package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starlib.eventbus.impl.SimpleEventBus;
import com.stardevllc.starlib.observable.collections.event.MapChangeEvent;

import java.util.Map;
import java.util.function.*;

/**
 * An abstract implementation of an Obserable Map.
 *
 * @param <K> The Key Type
 * @param <V> The value type
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractObservableMap<K, V> implements ObservableMap<K, V> {
    
    /**
     * The event bus that is used to listen to changes
     */
    protected final IEventBus<MapChangeEvent> eventBus = new SimpleEventBus<>();
    
    /**
     * The actual map that backs this observable map
     *
     * @return The back ing map
     */
    protected abstract Map<K, V> getBackingMap();
    
    /**
     * Constructs a new empty AbstractObservableMap
     */
    public AbstractObservableMap() {
        
    }
    
    /**
     * Constructs a new AbstractObserableMap with an existing map
     *
     * @param map The map that has existing values
     */
    public AbstractObservableMap(Map<K, V> map) {
        
    }
    
    @Override
    public IEventBus<MapChangeEvent> eventBus() {
        return this.eventBus;
    }
    
    @Override
    public int size() {
        return getBackingMap().size();
    }
    
    @Override
    public boolean isEmpty() {
        return getBackingMap().isEmpty();
    }
    
    @Override
    public boolean containsKey(Object key) {
        return getBackingMap().containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return getBackingMap().containsValue(value);
    }
    
    @Override
    public V get(Object key) {
        return getBackingMap().get(key);
    }
    
    @Override
    public V put(K key, V value) {
        V removed = this.getBackingMap().put(key, value);
        this.eventBus.post(new MapChangeEvent<>(this, key, value, removed));
        return removed;
    }
    
    @Override
    public V remove(Object key) {
        V removed = this.getBackingMap().remove(key);
        this.eventBus.post(new MapChangeEvent<>(this, (K) key, null, removed));
        return removed;
    }
    
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void clear() {
        for (Entry<K, V> entry : this.entrySet()) {
            this.eventBus.post(new MapChangeEvent<>(this, entry.getKey(), null, entry.getValue()));
        }
        this.entrySet().clear();
    }
    
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return getBackingMap().getOrDefault(key, defaultValue);
    }
    
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.getBackingMap().forEach(action);
    }
    
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        ObservableMap.super.replaceAll(function);
    }
    
    @Override
    public V putIfAbsent(K key, V value) {
        V result = getBackingMap().putIfAbsent(key, value);
        if (result != null) {
            this.eventBus.post(new MapChangeEvent<>(this, key, value, null));
        }
        
        return result;
    }
    
    @Override
    public boolean remove(Object key, Object value) {
        boolean result = getBackingMap().remove(key, value);
        if (result) {
            this.eventBus.post(new MapChangeEvent<>(this, (K) key, null, (V) value));
        }
        
        return result;
    }
    
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        boolean result = getBackingMap().replace(key, oldValue, newValue);
        if (result) {
            this.eventBus.post(new MapChangeEvent<>(this, key, newValue, oldValue));
        }
        
        return result;
    }
    
    @Override
    public V replace(K key, V value) {
        V oldValue = getBackingMap().replace(key, value);
        this.eventBus.post(new MapChangeEvent<>(this, key, value, oldValue));
        return oldValue;
    }
    
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V value;
        if ((value = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                this.eventBus.post(new MapChangeEvent<>(this, key, newValue, null));
                return newValue;
            }
        }
        
        return value;
    }
    
    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V oldValue;
        if ((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                this.eventBus.post(new MapChangeEvent<>(this, key, newValue, null));
                return newValue;
            } else {
                V existing = remove(key);
                this.eventBus.post(new MapChangeEvent<>(this, key, null, existing));
                return null;
            }
        } else {
            return null;
        }
    }
    
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V oldValue = get(key);
        
        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            if (oldValue != null || containsKey(key)) {
                V existing = remove(key);
                this.eventBus.post(new MapChangeEvent<>(this, key, null, existing));
            }
            return null;
        } else {
            put(key, newValue);
            this.eventBus.post(new MapChangeEvent<>(this, key, newValue, oldValue));
            return newValue;
        }
    }
    
    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V oldValue = get(key);
        V newValue = oldValue == null ? value : remappingFunction.apply(oldValue, value);
        if (newValue == null) {
            remove(key);
            this.eventBus.post(new MapChangeEvent<>(this, key, null, oldValue));
        } else {
            put(key, newValue);
            this.eventBus.post(new MapChangeEvent<>(this, key, newValue, oldValue));
        }
        return newValue;
    }
}
