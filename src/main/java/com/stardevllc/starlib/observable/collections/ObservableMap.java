package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.impl.map.ObservableHashMap;
import com.stardevllc.starlib.observable.collections.impl.map.ObservableTreeMap;
import com.stardevllc.starlib.observable.collections.listeners.MapChangeListener;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * A Map that can be observed for changes.<br>
 * Use {@link MapChangeListener} to listen to these changes. <br>
 *  * Use {@link ObservableHashMap} and {@link ObservableTreeMap} for the implementations of this.
 * @param <K>
 * @param <V>
 */
public interface ObservableMap<K, V> {
    void addChangeListener(MapChangeListener<K, V> listener);
    int size();
    boolean isEmpty();
    boolean containsKey(Object key);
    boolean containsValue(Object value);
    V get(Object key);
    V put(K key, V value);
    V remove(Object key);
    void clear();
    Set<K> keySet();
    Collection<V> values();
    Set<Map.Entry<K, V>> entrySet();
    V getOrDefault(Object key, V defaultValue);
    void forEach(BiConsumer<? super K, ? super V> action);
}