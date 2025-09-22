package com.stardevllc.starlib.registry.functions;

import java.util.function.Function;

/**
 * Normalizes keys for a registry
 * @param <K> The key type
 */
@FunctionalInterface
public interface KeyNormalizer<K extends Comparable<K>> extends Function<K, K> {
}
