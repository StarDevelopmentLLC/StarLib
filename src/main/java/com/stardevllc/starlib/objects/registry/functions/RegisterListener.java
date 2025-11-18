package com.stardevllc.starlib.objects.registry.functions;

/**
 * Called when something is registered to a registry
 *
 * @param <K> The key type
 * @param <V> The value type
 * @deprecated This is being removed in favor of the the new registry system and {@link com.stardevllc.starlib.objects.registry.RegistryChangeListener}
 */
@Deprecated(forRemoval = true, since = "0.20.0")
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
