package com.stardevllc.starlib.registry;

import java.util.Map;
import java.util.function.Function;

public class LongRegistry<V> extends Registry<Long, V> {
    public LongRegistry(Map<Long, V> initialObjects, Function<Long, Long> keyNormalizer) {
        super(initialObjects, keyNormalizer);
    }

    public LongRegistry(Map<Long, V> initialObjects) {
        super(initialObjects);
    }

    public LongRegistry(Function<Long, Long> keyNormalizer) {
        super(keyNormalizer);
    }

    public LongRegistry() {
    }
}