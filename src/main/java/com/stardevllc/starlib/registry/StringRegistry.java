package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.Normalizer;
import com.stardevllc.starlib.registry.functions.Register;

import java.util.Map;

public class StringRegistry<V> extends Registry<String, V> {
    public StringRegistry(Map<String, V> initialObjects, Normalizer<String> keyNormalizer, Register<V, String> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public StringRegistry() {
    }

    public StringRegistry(Map<String, V> initialObjects) {
        super(initialObjects);
    }

    public StringRegistry(Map<String, V> initialObjects, Normalizer<String> normalizer) {
        super(initialObjects, normalizer);
    }

    public StringRegistry(Map<String, V> initialObjects, Register<V, String> register) {
        super(initialObjects, register);
    }

    public StringRegistry(Normalizer<String> keyNormalizer, Register<V, String> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public StringRegistry(Normalizer<String> keyNormalizer) {
        super(keyNormalizer);
    }

    public StringRegistry(Register<V, String> registerFunction) {
        super(registerFunction);
    }
}