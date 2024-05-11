package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyGenerator;
import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class IntegerRegistry<V> extends Registry<Integer, V> {
    public IntegerRegistry(Map<Integer, V> initialObjects, KeyNormalizer<Integer> keyNormalizer, KeyRetriever<V, Integer> keyRetriever, KeyGenerator<V, Integer> keyGenerator) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator);
    }

    public IntegerRegistry() {
    }

    @Override
    public SortedMap<Integer, V> subMap(Integer integer, Integer k1) {
        return new IntegerRegistry<>(this.objects.subMap(integer, k1), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }

    @Override
    public SortedMap<Integer, V> headMap(Integer integer) {
        return new IntegerRegistry<>(this.objects.headMap(integer), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }

    @Override
    public SortedMap<Integer, V> tailMap(Integer integer) {
        return new IntegerRegistry<>(this.objects.tailMap(integer), this.keyNormalizer, this.keyRetriever, this.keyGenerator);
    }
    
    public static class Builder<V> extends Registry.Builder<Integer, V> {
        @Override
        public IntegerRegistry.Builder<V> initialObjects(TreeMap<Integer, V> objects) {
            return (Builder<V>) super.initialObjects(objects);
        }

        @Override
        public  IntegerRegistry.Builder<V> keyNormalizer(KeyNormalizer<Integer> keyNormalizer) {
            return (Builder<V>) super.keyNormalizer(keyNormalizer);
        }

        @Override
        public  IntegerRegistry.Builder<V> keyRetriever(KeyRetriever<V, Integer> keyRetriever) {
            return (Builder<V>) super.keyRetriever(keyRetriever);
        }

        @Override
        public  IntegerRegistry.Builder<V> keyGenerator(KeyGenerator<V, Integer> keyGenerator) {
            return (Builder<V>) super.keyGenerator(keyGenerator);
        }

        @Override
        public IntegerRegistry<V> build() {
            return new IntegerRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator);
        }
    }
}
