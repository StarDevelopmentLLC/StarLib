package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;

import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

public class UUIDRegistry<V> extends Registry<UUID, V> {
    public UUIDRegistry(Map<UUID, V> initialObjects, KeyNormalizer<UUID> keyNormalizer, KeyRetriever<V, UUID> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public UUIDRegistry() {
    }

    public UUIDRegistry(Map<UUID, V> initialObjects) {
        super(initialObjects);
    }

    public UUIDRegistry(Map<UUID, V> initialObjects, KeyNormalizer<UUID> normalizer) {
        super(initialObjects, normalizer);
    }

    public UUIDRegistry(Map<UUID, V> initialObjects, KeyRetriever<V, UUID> register) {
        super(initialObjects, register);
    }

    public UUIDRegistry(KeyNormalizer<UUID> keyNormalizer, KeyRetriever<V, UUID> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public UUIDRegistry(KeyNormalizer<UUID> keyNormalizer) {
        super(keyNormalizer);
    }

    public UUIDRegistry(KeyRetriever<V, UUID> registerFunction) {
        super(registerFunction);
    }

    @Override
    public SortedMap<UUID, V> subMap(UUID k1, UUID k2) {
        return new UUIDRegistry<>(this.objects.subMap(k1, k2), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public SortedMap<UUID, V> headMap(UUID k1) {
        return new UUIDRegistry<>(this.objects.headMap(k1), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public SortedMap<UUID, V> tailMap(UUID k1) {
        return new UUIDRegistry<>(this.objects.tailMap(k1), this.keyNormalizer, this.keyRetriever);
    }
}