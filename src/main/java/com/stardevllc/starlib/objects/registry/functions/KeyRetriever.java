package com.stardevllc.starlib.objects.registry.functions;

import java.util.function.Function;

/**
 * Retrieves keys from a value in a registry
 * @param <V> The value type
 * @param <K> The key type
 */
@FunctionalInterface
@Deprecated(since = "0.24.0")
public interface KeyRetriever<V, K extends Comparable<K>> extends Function<V, K>  {
}
