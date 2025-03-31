package com.stardevllc.observable.collections.event;

import com.stardevllc.observable.collections.ObservableMap;

public record MapChangeEvent<K, V>(ObservableMap<K, V> map, K key, V added, V removed) {
}
