package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyGenerator;
import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class StringRegistry<V> extends Registry<String, V> {
    public StringRegistry(Map<String, V> initialObjects, KeyNormalizer<String> keyNormalizer, KeyRetriever<V, String> keyRetriever, KeyGenerator<V, String> keyGenerator) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator);
    }

    public StringRegistry() {
    }

    @Override
    public SortedMap<String, V> subMap(String k1, String k2) {
        return new StringRegistry<>(this.objects.subMap(k1, k2), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }

    @Override
    public SortedMap<String, V> headMap(String k1) {
        return new StringRegistry<>(this.objects.headMap(k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }

    @Override
    public SortedMap<String, V> tailMap(String k1) {
        return new StringRegistry<>(this.objects.tailMap(k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }

    public static class Builder<V> extends Registry.Builder<String, V> {
        @Override
        public Builder<V> initialObjects(TreeMap<String, V> objects) {
            return (Builder<V>) super.initialObjects(objects);
        }

        @Override
        public  Builder<V> keyNormalizer(KeyNormalizer<String> keyNormalizer) {
            return (Builder<V>) super.keyNormalizer(keyNormalizer);
        }

        @Override
        public  Builder<V> keyRetriever(KeyRetriever<V, String> keyRetriever) {
            return (Builder<V>) super.keyRetriever(keyRetriever);
        }

        @Override
        public  Builder<V> keyGenerator(KeyGenerator<V, String> keyGenerator) {
            return (Builder<V>) super.keyGenerator(keyGenerator);
        }

        @Override
        public StringRegistry<V> build() {
            return new StringRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator);
        }
    }
}