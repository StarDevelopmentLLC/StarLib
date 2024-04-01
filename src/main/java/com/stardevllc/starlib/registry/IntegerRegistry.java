package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.Normalizer;
import com.stardevllc.starlib.registry.functions.Register;

import java.util.Map;

public class IntegerRegistry<V> extends Registry<Integer, V> {
    public IntegerRegistry(Map<Integer, V> initialObjects, Normalizer<Integer> keyNormalizer, Register<V, Integer> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public IntegerRegistry() {
    }

    public IntegerRegistry(Map<Integer, V> initialObjects) {
        super(initialObjects);
    }

    public IntegerRegistry(Map<Integer, V> initialObjects, Normalizer<Integer> normalizer) {
        super(initialObjects, normalizer);
    }

    public IntegerRegistry(Map<Integer, V> initialObjects, Register<V, Integer> register) {
        super(initialObjects, register);
    }

    public IntegerRegistry(Normalizer<Integer> keyNormalizer, Register<V, Integer> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public IntegerRegistry(Normalizer<Integer> keyNormalizer) {
        super(keyNormalizer);
    }

    public IntegerRegistry(Register<V, Integer> registerFunction) {
        super(registerFunction);
    }
}
