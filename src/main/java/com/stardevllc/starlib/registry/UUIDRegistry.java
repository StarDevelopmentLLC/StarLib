package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.Normalizer;
import com.stardevllc.starlib.registry.functions.Register;

import java.util.Map;
import java.util.UUID;

public class UUIDRegistry<V> extends Registry<UUID, V> {
    public UUIDRegistry(Map<UUID, V> initialObjects, Normalizer<UUID> keyNormalizer, Register<V, UUID> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public UUIDRegistry() {
    }

    public UUIDRegistry(Map<UUID, V> initialObjects) {
        super(initialObjects);
    }

    public UUIDRegistry(Map<UUID, V> initialObjects, Normalizer<UUID> normalizer) {
        super(initialObjects, normalizer);
    }

    public UUIDRegistry(Map<UUID, V> initialObjects, Register<V, UUID> register) {
        super(initialObjects, register);
    }

    public UUIDRegistry(Normalizer<UUID> keyNormalizer, Register<V, UUID> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public UUIDRegistry(Normalizer<UUID> keyNormalizer) {
        super(keyNormalizer);
    }

    public UUIDRegistry(Register<V, UUID> registerFunction) {
        super(registerFunction);
    }
}