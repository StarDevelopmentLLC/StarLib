package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyGenerator;
import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;
import com.stardevllc.starlib.registry.functions.KeySetter;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class LongRegistry<V> extends Registry<Long, V> {
    public LongRegistry(Map<Long, V> initialObjects, KeyNormalizer<Long> keyNormalizer, KeyRetriever<V, Long> keyRetriever, KeyGenerator<V, Long> keyGenerator, KeySetter<Long, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }

    public LongRegistry() {
    }

    @Override
    public SortedMap<Long, V> subMap(Long k1, Long k2) {
        return new LongRegistry<>(this.objects.subMap(k1, k2), this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
    }

    @Override
    public SortedMap<Long, V> headMap(Long k1) {
        return new LongRegistry<>(this.objects.headMap(k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
    }

    @Override
    public SortedMap<Long, V> tailMap(Long k1) {
        return new LongRegistry<>(this.objects.tailMap(k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
    }

    public static class Builder<V> extends Registry.Builder<Long, V> {
        @Override
        public Builder<V> initialObjects(TreeMap<Long, V> objects) {
            return (Builder<V>) super.initialObjects(objects);
        }

        @Override
        public Builder<V> keyNormalizer(KeyNormalizer<Long> keyNormalizer) {
            return (Builder<V>) super.keyNormalizer(keyNormalizer);
        }

        @Override
        public Builder<V> keyRetriever(KeyRetriever<V, Long> keyRetriever) {
            return (Builder<V>) super.keyRetriever(keyRetriever);
        }

        @Override
        public Builder<V> keyGenerator(KeyGenerator<V, Long> keyGenerator) {
            return (Builder<V>) super.keyGenerator(keyGenerator);
        }

        @Override
        public Builder<V> keySetter(KeySetter<Long, V> keySetter) {
            return (Builder<V>) super.keySetter(keySetter);
        }

        @Override
        public LongRegistry<V> build() {
            return new LongRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
        }
    }
}