package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.impl.map.ObservableHashMap;
import com.stardevllc.starlib.observable.collections.impl.map.ObservableTreeMap;
import com.stardevllc.starlib.observable.collections.listeners.MapChangeListener;

import java.util.Map;

/**
 * A Map that can be observed for changes.<br>
 * Use {@link MapChangeListener} to listen to these changes. <br>
 *  * Use {@link ObservableHashMap} and {@link ObservableTreeMap} for the implementations of this.
 * @param <K>
 * @param <V>
 */
public interface ObservableMap<K, V> extends Map<K, V> {
    void addChangeListener(MapChangeListener<K, V> listener);
}