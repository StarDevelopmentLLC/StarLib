package com.stardevllc.starlib.observable.collections.event;

import com.stardevllc.starlib.observable.collections.ObservableMap;

/**
 * Info class for changes in maps
 *
 * @param map     The map that changed
 * @param key     The key that changed
 * @param added   The added value
 * @param removed The removed value
 * @param <K>     The key type
 * @param <V>     The value type
 */
public record MapChangeEvent<K, V>(ObservableMap<K, V> map, K key, V added, V removed) {
}
