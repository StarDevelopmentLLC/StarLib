package com.stardevllc.starlib.registry;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class UUIDRegistry<V> extends Registry<UUID, V> {
    public UUIDRegistry(Map<UUID, V> initialObjects, Function<UUID, UUID> keyNormalizer) {
        super(initialObjects, keyNormalizer);
    }

    public UUIDRegistry(Map<UUID, V> initialObjects) {
        super(initialObjects);
    }

    public UUIDRegistry(Function<UUID, UUID> keyNormalizer) {
        super(keyNormalizer);
    }

    public UUIDRegistry() {
    }
}