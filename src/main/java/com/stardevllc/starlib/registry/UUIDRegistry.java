package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyGenerator;
import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;

import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

public class UUIDRegistry<V> extends Registry<UUID, V> {
    public UUIDRegistry(Map<UUID, V> initialObjects, KeyNormalizer<UUID> keyNormalizer, KeyRetriever<V, UUID> keyRetriever, KeyGenerator<V, UUID> keyGenerator) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator);
    }

    public UUIDRegistry() {
    }

    @Override
    public SortedMap<UUID, V> subMap(UUID k1, UUID k2) {
        return new UUIDRegistry<>(this.objects.subMap(k1, k2), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }

    @Override
    public SortedMap<UUID, V> headMap(UUID k1) {
        return new UUIDRegistry<>(this.objects.headMap(k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }

    @Override
    public SortedMap<UUID, V> tailMap(UUID k1) {
        return new UUIDRegistry<>(this.objects.tailMap(k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }

    public static class Builder<V, K extends Comparable<K>> extends Registry.Builder<UUID, V> {
        @Override
        public UUIDRegistry<V> build() {
            return new UUIDRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator);
        }
    }
}