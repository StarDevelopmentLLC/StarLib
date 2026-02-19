package com.stardevllc.starlib.objects.registry.functions;

import java.util.function.Function;

/**
 * Normalizes keys for a registry
 * @param <K> The key type
 */
@FunctionalInterface
@Deprecated(since = "0.24.0")
public interface KeyNormalizer<K extends Comparable<K>> extends Function<K, K> {
}
