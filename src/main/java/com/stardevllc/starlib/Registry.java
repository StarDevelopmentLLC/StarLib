package com.stardevllc.starlib;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Registry<T> implements Iterable<T> {
    protected final Map<String, T> objects = new HashMap<>();
    protected final Function<String, String> keyNormalizer;

    public Registry(Map<String, T> initialObjects, Function<String, String> keyNormalizer) {
        this.keyNormalizer = keyNormalizer;
        if (initialObjects != null && !initialObjects.isEmpty()) {
            objects.putAll(initialObjects);
        }
    }
    
    public Registry(Function<String, String> keyNormalizer) {
        this(null, keyNormalizer);
    }

    public void register(String key, T object) {
        this.objects.put(keyNormalizer.apply(key), object);
    }

    public void registerAll(Map<String, T> map) {
        map.forEach(this::register);
    }

    public T get(String key) {
        return objects.get(keyNormalizer.apply(key));
    }

    public Map<String, T> getObjects() {
        return new HashMap<>(this.objects);
    }

    public boolean contains(String key) {
        return objects.containsKey(keyNormalizer.apply(key));
    }

    public void deregister(String key) {
        objects.remove(keyNormalizer.apply(key));
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<>(objects.values()).iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        new ArrayList<>(objects.values()).forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return new ArrayList<>(objects.values()).spliterator();
    }
}