package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.Register;

import java.util.*;
import java.util.function.Consumer;

public class Registry<K extends Comparable<K>, V> implements Iterable<V> {
    protected final TreeMap<K, V> objects = new TreeMap<>();
    protected final KeyNormalizer<K> keyNormalizer;
    protected final Register<V, K> registerFunction;

    public Registry(Map<K, V> initialObjects, KeyNormalizer<K> keyNormalizer, Register<V, K> registerFunction) {
        if (initialObjects != null && !initialObjects.isEmpty()) {
            objects.putAll(initialObjects);
        }
        this.keyNormalizer = keyNormalizer;
        this.registerFunction = registerFunction;
    }

    public Registry() {
        this(null, null, null);
    }

    public Registry(Map<K, V> initialObjects) {
        this(initialObjects, null, null);
    }
    
    public Registry(Map<K, V> initialObjects, KeyNormalizer<K> normalizer) {
        this(initialObjects, normalizer, null);
    }

    public Registry(Map<K, V> initialObjects, Register<V, K> register) {
        this(initialObjects, null, register);
    }

    public Registry(KeyNormalizer<K> keyNormalizer, Register<V, K> registerFunction) {
        this(null, keyNormalizer, registerFunction);
    }

    public Registry(KeyNormalizer<K> keyNormalizer) {
        this(null, keyNormalizer, null);
    }

    public Registry(Register<V, K> registerFunction) {
        this(null, null, registerFunction);
    }

    public V register(V object) {
        if (registerFunction == null) {
            return null;
        }

        K key = registerFunction.apply(object);
        return register(key, object);
    }
    
    public V register(K key, V object) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        this.objects.put(key, object);
        return object;
    }

    public void registerAll(Map<K, V> map) {
        map.forEach(this::register);
    }
    
    public void registerAll(Collection<V> collection) {
        collection.forEach(this::register);
    }
    
    public void registerAll(V... values) {
        if (values != null) {
            for (V value : values) {
                register(value);
            }
        }
    }

    public V get(K key) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return objects.get(key);
    }
    
    public Map<K, V> getObjects() {
        return new TreeMap<>(this.objects);
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