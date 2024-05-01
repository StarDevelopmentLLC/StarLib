package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.Register;

import java.util.Map;

public class IntegerRegistry<V> extends Registry<Integer, V> {
    public IntegerRegistry(Map<Integer, V> initialObjects, KeyNormalizer<Integer> keyNormalizer, Register<V, Integer> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public IntegerRegistry() {
    }

    public IntegerRegistry(Map<Integer, V> initialObjects) {
        super(initialObjects);
    }

    public IntegerRegistry(Map<Integer, V> initialObjects, KeyNormalizer<Integer> normalizer) {
        super(initialObjects, normalizer);
    }

    public IntegerRegistry(Map<Integer, V> initialObjects, Register<V, Integer> register) {
        super(initialObjects, register);
    }

    public IntegerRegistry(KeyNormalizer<Integer> keyNormalizer, Register<V, Integer> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public IntegerRegistry(KeyNormalizer<Integer> keyNormalizer) {
        super(keyNormalizer);
    }

    public IntegerRegistry(Register<V, Integer> registerFunction) {
        super(registerFunction);
    }
}
