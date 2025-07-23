package com.stardevllc.starlib.observable.collections.event;

import com.stardevllc.starlib.observable.collections.ObservableMap;

public record MapChangeEvent<K, V>(ObservableMap<K, V> map, K key, V added, V removed) {
}
