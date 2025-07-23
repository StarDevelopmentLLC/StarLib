package com.stardevllc.starlib.registry.functions;

import java.util.function.Function;

public interface KeyGenerator<V, K extends Comparable<K>> extends Function<V, K> {
}
