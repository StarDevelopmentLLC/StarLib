package com.stardevllc.starlib.objects.registry.functions;

import java.util.function.Function;

/**
 * Retrieves keys from a value in a registry
 * @param <V> The value type
 * @param <K> The key type
 */
@FunctionalInterface
public interface KeyRetriever<V, K extends Comparable<K>> extends Function<V, K>  {
}
