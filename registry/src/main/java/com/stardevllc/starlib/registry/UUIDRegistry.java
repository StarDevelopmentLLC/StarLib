package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyGenerator;
import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;
import com.stardevllc.starlib.registry.functions.KeySetter;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

public class UUIDRegistry<V> extends Registry<UUID, V> {
    public UUIDRegistry(Map<UUID, V> initialObjects, KeyNormalizer<UUID> keyNormalizer, KeyRetriever<V, UUID> keyRetriever, KeyGenerator<V, UUID> keyGenerator, KeySetter<UUID, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }

    public UUIDRegistry() {
    }

    @Override
    public SortedMap<UUID, V> subMap(UUID k1, UUID k2) {
        return new UUIDRegistry<>(this.objects.subMap(k1, k2), this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
    }

    @Override
    public SortedMap<UUID, V> headMap(UUID k1) {
        return new UUIDRegistry<>(this.objects.headMap(k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
    }

    @Override
    public SortedMap<UUID, V> tailMap(UUID k1) {
        return new UUIDRegistry<>(this.objects.tailMap(k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
    }

    public static class Builder<V> extends Registry.Builder<UUID, V> {
        @Override
        public Builder<V> initialObjects(TreeMap<UUID, V> objects) {
            return (Builder<V>) super.initialObjects(objects);
        }

        @Override
        public Builder<V> keyNormalizer(KeyNormalizer<UUID> keyNormalizer) {
            return (Builder<V>) super.keyNormalizer(keyNormalizer);
        }

        @Override
        public Builder<V> keyRetriever(KeyRetriever<V, UUID> keyRetriever) {
            return (Builder<V>) super.keyRetriever(keyRetriever);
        }

        @Override
        public Builder<V> keyGenerator(KeyGenerator<V, UUID> keyGenerator) {
            return (Builder<V>) super.keyGenerator(keyGenerator);
        }

        @Override
        public Builder<V> keySetter(KeySetter<UUID, V> keySetter) {
            return (Builder<V>) super.keySetter(keySetter);
        }

        @Override
        public UUIDRegistry<V> build() {
            return new UUIDRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
        }
    }
}