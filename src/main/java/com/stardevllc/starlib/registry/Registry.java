package com.stardevllc.starlib.registry;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

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