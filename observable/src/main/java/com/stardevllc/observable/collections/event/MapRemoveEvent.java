package com.stardevllc.observable.collections.event;

import java.util.Map;

public record MapRemoveEvent<K, V>(Map<K, V> map, K key, V removed) implements MapChangeEvent {
    
}
