package com.stardevllc.starlib.registry;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Registry<K extends Comparable<K>, V> implements Iterable<V> {
    protected final TreeMap<K, V> objects = new TreeMap<>();
    protected final Function<K, K> keyNormalizer;

    public Registry(Map<K, V> initialObjects, Function<K, K> keyNormalizer) {
        if (initialObjects != null && !initialObjects.isEmpty()) {
            objects.putAll(initialObjects);
        }
        this.keyNormalizer = keyNormalizer;
    }

    public Registry(Map<K, V> initialObjects) {
        this(initialObjects, null);
    }

    public Registry(Function<K, K> keyNormalizer) {
        this(null, keyNormalizer);
    }
    
    public Registry() {
        this(null, null);
    }
    
    public void register(K key, V object) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        this.objects.put(key, object);
    }

    public void registerAll(Map<K, V> map) {
        map.forEach(this::register);
    }

    public V get(K key) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return objects.get(key);
    }

    public Map<K, V> getObjects() {
        return new HashMap<>(this.objects);
    }

    public boolean contains(K key) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return objects.containsKey(key);
    }

    public void deregister(K key) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        objects.remove(key);
    }

    @Override
    public Iterator<V> iterator() {
        return new ArrayList<>(objects.values()).iterator();
    }

    @Override
    public void forEach(Consumer<? super V> action) {
        new ArrayList<>(objects.values()).forEach(action);
    }

    @Override
    public Spliterator<V> spliterator() {
        return new ArrayList<>(objects.values()).spliterator();
    }
}