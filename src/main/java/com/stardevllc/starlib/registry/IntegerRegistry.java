package com.stardevllc.starlib.registry;

import java.util.Map;
import java.util.function.Function;

public class IntegerRegistry<V> extends Registry<Integer, V> {
    public IntegerRegistry(Map<Integer, V> initialObjects, Function<Integer, Integer> keyNormalizer) {
        super(initialObjects, keyNormalizer);
    }

    public IntegerRegistry(Function<Integer, Integer> keyNormalizer) {
        super(keyNormalizer);
    }

    public IntegerRegistry(Map<Integer, V> initialObjects) {
        super(initialObjects);
    }

    public IntegerRegistry() {
    }
}
