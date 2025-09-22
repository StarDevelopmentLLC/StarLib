package com.stardevllc.starlib.registry.functions;

import java.util.function.Function;

/**
 * Generates keys for a value for a registry
 *
 * @param <V> The value type
 * @param <K> The key type
 */
@FunctionalInterface
public interface KeyGenerator<V, K extends Comparable<K>> extends Function<V, K> {
}