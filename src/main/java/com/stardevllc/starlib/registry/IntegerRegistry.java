package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;

import java.util.Map;
import java.util.SortedMap;

public class IntegerRegistry<V> extends Registry<Integer, V> {
    public IntegerRegistry(Map<Integer, V> initialObjects, KeyNormalizer<Integer> keyNormalizer, KeyRetriever<V, Integer> keyRetriever) {
        super(initialObjects, keyNormalizer, keyRetriever);
    }

    public IntegerRegistry() {
    }

    public IntegerRegistry(Map<Integer, V> initialObjects) {
        super(initialObjects);
    }

    public IntegerRegistry(Map<Integer, V> initialObjects, KeyNormalizer<Integer> normalizer) {
        super(initialObjects, normalizer);
    }

    public IntegerRegistry(Map<Integer, V> initialObjects, KeyRetriever<V, Integer> keyRetriever) {
        super(initialObjects, keyRetriever);
    }

    public IntegerRegistry(KeyNormalizer<Integer> keyNormalizer, KeyRetriever<V, Integer> keyRetriever) {
        super(keyNormalizer, keyRetriever);
    }

    public IntegerRegistry(KeyNormalizer<Integer> keyNormalizer) {
        super(keyNormalizer);
    }

    public IntegerRegistry(KeyRetriever<V, Integer> keyRetriever) {
        super(keyRetriever);
    }

    @Override
    public SortedMap<Integer, V> subMap(Integer integer, Integer k1) {
        return new IntegerRegistry<>(this.objects.subMap(integer, k1), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public SortedMap<Integer, V> headMap(Integer integer) {
        return new IntegerRegistry<>(this.objects.headMap(integer), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public SortedMap<Integer, V> tailMap(Integer integer) {
        return new IntegerRegistry<>(this.objects.tailMap(integer), this.keyNormalizer, this.keyRetriever);
    }
}
