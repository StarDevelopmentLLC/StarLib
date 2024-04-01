package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.Normalizer;
import com.stardevllc.starlib.registry.functions.Register;

import java.util.Map;

public class LongRegistry<V> extends Registry<Long, V> {
    public LongRegistry(Map<Long, V> initialObjects, Normalizer<Long> keyNormalizer, Register<V, Long> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public LongRegistry() {
    }

    public LongRegistry(Map<Long, V> initialObjects) {
        super(initialObjects);
    }

    public LongRegistry(Map<Long, V> initialObjects, Normalizer<Long> normalizer) {
        super(initialObjects, normalizer);
    }

    public LongRegistry(Map<Long, V> initialObjects, Register<V, Long> register) {
        super(initialObjects, register);
    }

    public LongRegistry(Normalizer<Long> keyNormalizer, Register<V, Long> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public LongRegistry(Normalizer<Long> keyNormalizer) {
        super(keyNormalizer);
    }

    public LongRegistry(Register<V, Long> registerFunction) {
        super(registerFunction);
    }
}