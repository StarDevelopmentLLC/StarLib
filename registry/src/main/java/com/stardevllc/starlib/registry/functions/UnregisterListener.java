package com.stardevllc.starlib.registry.functions;

@FunctionalInterface
public interface UnregisterListener<K extends Comparable<K>, V> {
    void onUnregister(K key, V value);
}
