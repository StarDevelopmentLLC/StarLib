package com.stardevllc.starlib.registry.functions;

/**
 * Called when something is removed from a registry
 * @param <K> The key type
 * @param <V> The value type
 */
@FunctionalInterface
public interface UnregisterListener<K extends Comparable<K>, V> {
    /**
     * Handler method
     * @param key The key
     * @param value The value (can be null)
     */
    void onUnregister(K key, V value);
}
