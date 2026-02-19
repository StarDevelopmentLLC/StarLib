package com.stardevllc.starlib.objects.registry.functions;

import java.util.function.BiConsumer;

/**
 * Sets a key to an object for a registry
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@FunctionalInterface
@Deprecated(since = "0.24.0")
public interface KeySetter<K extends Comparable<K>, V> extends BiConsumer<K, V> {
}
