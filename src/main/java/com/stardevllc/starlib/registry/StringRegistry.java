package com.stardevllc.starlib.registry;

import java.util.Map;
import java.util.function.Function;

public class StringRegistry<V> extends Registry<String, V> {
    public StringRegistry(Map<String, V> initialObjects, Function<String, String> keyNormalizer) {
        super(initialObjects, keyNormalizer);
    }

    public StringRegistry(Function<String, String> keyNormalizer) {
        super(keyNormalizer);
    }

    public StringRegistry(Map<String, V> initialObjects) {
        super(initialObjects);
    }

    public StringRegistry() {
    }
}