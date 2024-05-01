package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;

import java.util.Map;
import java.util.SortedMap;

public class LongRegistry<V> extends Registry<Long, V> {
    public LongRegistry(Map<Long, V> initialObjects, KeyNormalizer<Long> keyNormalizer, KeyRetriever<V, Long> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public LongRegistry() {
    }

    public LongRegistry(Map<Long, V> initialObjects) {
        super(initialObjects);
    }

    public LongRegistry(Map<Long, V> initialObjects, KeyNormalizer<Long> normalizer) {
        super(initialObjects, normalizer);
    }

    public LongRegistry(Map<Long, V> initialObjects, KeyRetriever<V, Long> register) {
        super(initialObjects, register);
    }

    public LongRegistry(KeyNormalizer<Long> keyNormalizer, KeyRetriever<V, Long> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public LongRegistry(KeyNormalizer<Long> keyNormalizer) {
        super(keyNormalizer);
    }

    public LongRegistry(KeyRetriever<V, Long> registerFunction) {
        super(registerFunction);
    }
    
    @Override
    public SortedMap<Long, V> subMap(Long k1, Long k2) {
        return new LongRegistry<>(this.objects.subMap(k1, k2), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public SortedMap<Long, V> headMap(Long k1) {
        return new LongRegistry<>(this.objects.headMap(k1), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public SortedMap<Long, V> tailMap(Long k1) {
        return new LongRegistry<>(this.objects.tailMap(k1), this.keyNormalizer, this.keyRetriever);
    }
}