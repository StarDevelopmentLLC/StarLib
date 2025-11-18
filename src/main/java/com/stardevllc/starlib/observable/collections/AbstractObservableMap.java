package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.handler.MapListenerHandler;

import java.util.Collection;
import java.util.Map;
import java.util.function.*;

/**
 * An abstract implementation of an Obserable Map.
 *
 * @param <K> The Key Type
 * @param <V> The value type
 */
public abstract class AbstractObservableMap<K, V> implements ObservableMap<K, V> {
    
    /**
     * This is the handler for listeners
     */
    protected final MapListenerHandler<K, V> handler = new MapListenerHandler<>();
    
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
    public MapListenerHandler<K, V> getHandler() {
        return handler;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return getBackingMap().size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return getBackingMap().isEmpty();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return getBackingMap().containsKey(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        return getBackingMap().containsValue(value);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V get(Object key) {
        return getBackingMap().get(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        V removed = this.getBackingMap().put(key, value);
        boolean cancelled = this.handler.handleChange(this, key, value, removed);
        if (cancelled) {
            this.getBackingMap().put(key, removed);
            return null;
        }
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(Object key) {
        V removed = this.getBackingMap().remove(key);
        boolean cancelled = this.handler.handleChange(this, (K) key, null, removed);
        if (cancelled) {
            this.getBackingMap().put((K) key, removed);
        }
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        boolean cancelled = false;
        for (Map.Entry<K, V> entry : this.entrySet()) {
            boolean handlerCancelled = this.handler.handleChange(this, entry.getKey(), null, entry.getValue());
            if (!cancelled) {
                cancelled = handlerCancelled;
            }
        }
        if (!cancelled) {
            this.entrySet().clear();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return getBackingMap().getOrDefault(key, defaultValue);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.getBackingMap().forEach(action);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        ObservableMap.super.replaceAll(function);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V putIfAbsent(K key, V value) {
        V result = getBackingMap().putIfAbsent(key, value);
        if (result != null && result == value) {
            boolean cancelled = this.handler.handleChange(this, key, value, null);
            if (cancelled) {
                getBackingMap().remove(key);
                return null;
            }
        }
        
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object key, Object value) {
        if (!this.handler.handleChange(this, (K) key, null, (V) value)) {
            return getBackingMap().remove(key, value);
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if (!this.handler.handleChange(this, key, newValue, oldValue)) {
            return getBackingMap().replace(key, oldValue, newValue);
        }
        
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V replace(K key, V value) {
        V oldValue = getBackingMap().replace(key, value);
        boolean cancelled = this.handler.handleChange(this, key, value, oldValue);
        if (cancelled) {
            this.getBackingMap().replace(key, oldValue);
        }
        return oldValue;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V value;
        if ((value = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                if (!this.handler.handleChange(this, key, newValue, null)) {
                    put(key, newValue);
                    return newValue;
                }
            }
        }
        
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V oldValue;
        if ((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                if (!this.handler.handleChange(this, key, newValue, null)) {
                    getBackingMap().put(key, newValue);
                    return newValue;
                }
            } else {
                V existing = getBackingMap().remove(key);
                boolean cancelled = this.handler.handleChange(this, key, null, existing);
                if (cancelled) {
                    getBackingMap().put(key, existing);
                }
                return null;
            }
        } else {
            return null;
        }
        
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V oldValue = get(key);
        
        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            if (oldValue != null || containsKey(key)) {
                V existing = getBackingMap().remove(key);
                boolean cancelled = this.handler.handleChange(this, key, null, existing);
                if (cancelled) {
                    getBackingMap().put(key, existing);
                }
            }
            return null;
        } else {
            if (!this.handler.handleChange(this, key, newValue, oldValue)) {
                getBackingMap().put(key, newValue);
            }
            return newValue;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V oldValue = get(key);
        V newValue = oldValue == null ? value : remappingFunction.apply(oldValue, value);
        if (newValue == null) {
            if (!this.handler.handleChange(this, key, null, oldValue)) {
                getBackingMap().remove(key);
            }
        } else {
            if (!this.handler.handleChange(this, key, newValue, oldValue)) {
                getBackingMap().put(key, newValue);
            }
        }
        return newValue;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        ObservableLinkedList<V> values = new ObservableLinkedList<>(this.getBackingMap().values());
        values.addListener(c -> {
            if (c.removed() != null) {
                getBackingMap().values().remove(c.removed());
            }
        });
        return values;
    }
    
    @Override
    public String toString() {
        return getBackingMap().toString();
    }
}
