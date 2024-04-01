package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.Normalizer;
import com.stardevllc.starlib.registry.functions.Register;

import java.util.*;
import java.util.function.Consumer;

/**
 * This class represents a list of objects that are mapped to keys. Yes, there is the Java Map and this uses a TreeMap behind the scenes. <br>
 * This does provide a way to "normalize" keys to make them all the same. This function is fine to be null as there are checks where it would normally be used. <br>
 * Another thing though is that this class implements the Iterable interface for the values, so you can use this in enhanced for-loops directly. <br>
 * Changes made to returned collections do not affect the underlying Map as these methods return a copy of the underlying collection(s) <br>
 * That makes it safe to modify the Registry if you are iterating over the contents technically.
 * @param <K> The Key Type, Must implement the Comparable interface
 * @param <V> The Value Type
 */
public class Registry<K extends Comparable<K>, V> implements Iterable<V> {
    protected final TreeMap<K, V> objects = new TreeMap<>();
    protected final Normalizer<K> keyNormalizer;
    protected final Register<V, K> registerFunction;

    public Registry(Map<K, V> initialObjects, Normalizer<K> keyNormalizer, Register<V, K> registerFunction) {
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
    
    public Registry(Map<K, V> initialObjects, Normalizer<K> normalizer) {
        this(initialObjects, normalizer, null);
    }

    public Registry(Map<K, V> initialObjects, Register<V, K> register) {
        this(initialObjects, null, register);
    }

    public Registry(Normalizer<K> keyNormalizer, Register<V, K> registerFunction) {
        this(null, keyNormalizer, registerFunction);
    }

    public Registry(Normalizer<K> keyNormalizer) {
        this(null, keyNormalizer, null);
    }

    public Registry(Register<V, K> registerFunction) {
        this(null, null, registerFunction);
    }

    public void register(V object) {
        if (registerFunction == null) {
            return;
        }

        K key = registerFunction.apply(object);
        register(key, object);
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