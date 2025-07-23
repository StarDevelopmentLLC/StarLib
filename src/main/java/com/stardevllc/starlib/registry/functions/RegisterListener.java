package com.stardevllc.starlib.registry.functions;

@FunctionalInterface
public interface RegisterListener<K extends Comparable<K>, V> {
    void onRegister(K key, V value);
}
