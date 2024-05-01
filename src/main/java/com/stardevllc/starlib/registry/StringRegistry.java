package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;

import java.util.Map;

public class StringRegistry<V> extends Registry<String, V> {
    public StringRegistry(Map<String, V> initialObjects, KeyNormalizer<String> keyNormalizer, KeyRetriever<V, String> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public StringRegistry() {
    }

    public StringRegistry(Map<String, V> initialObjects) {
        super(initialObjects);
    }

    public StringRegistry(Map<String, V> initialObjects, KeyNormalizer<String> normalizer) {
        super(initialObjects, normalizer);
    }

    public StringRegistry(Map<String, V> initialObjects, KeyRetriever<V, String> register) {
        super(initialObjects, register);
    }

    public StringRegistry(KeyNormalizer<String> keyNormalizer, KeyRetriever<V, String> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public StringRegistry(KeyNormalizer<String> keyNormalizer) {
        super(keyNormalizer);
    }

    public StringRegistry(KeyRetriever<V, String> registerFunction) {
        super(registerFunction);
    }
}