package com.stardevllc.starlib.registry.functions;

/**
 * Called when something is registered to a registry
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@FunctionalInterface
public interface RegisterListener<K extends Comparable<K>, V> {
    /**
     * Handler method
     *
     * @param key   The key
     * @param value The value
     */
    void onRegister(K key, V value);
}
