package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.KeyNormalizer;
import com.stardevllc.starlib.registry.functions.KeyRetriever;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Registry<K extends Comparable<K>, V> implements Iterable<V>, SortedMap<K, V> {
    protected final TreeMap<K, V> objects = new TreeMap<>();
    protected final KeyNormalizer<K> keyNormalizer;
    protected final KeyRetriever<V, K> keyRetriever;
    
    protected Lock lock = new ReentrantLock();

    public Registry(Map<K, V> initialObjects, KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever) {
        if (initialObjects != null && !initialObjects.isEmpty()) {
            objects.putAll(initialObjects);
        }
        this.keyNormalizer = keyNormalizer;
        this.keyRetriever = keyRetriever;
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

    public Registry(Map<K, V> initialObjects, KeyRetriever<V, K> keyRetriever) {
        this(initialObjects, null, keyRetriever);
    }

    public Registry(KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever) {
        this(null, keyNormalizer, keyRetriever);
    }

    public Registry(KeyNormalizer<K> keyNormalizer) {
        this(null, keyNormalizer, null);
    }

    public Registry(KeyRetriever<V, K> keyRetriever) {
        this(null, null, keyRetriever);
    }

    public V register(V object) {
        if (keyRetriever == null) {
            return null;
        }

        K key = keyRetriever.apply(object);
        return register(key, object);
    }
    
    public V register(K key, V object) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        lock.lock();
        this.objects.put(key, object);
        lock.unlock();
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

    public V unregister(K key) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        lock.lock();
        V removed = objects.remove(key);
        lock.unlock();
        return removed;
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

    @Override
    public int size() {
        return objects.size();
    }

    @Override
    public boolean isEmpty() {
        return objects.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return objects.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return objects.containsValue(o);
    }

    @Override
    public V get(Object o) {
        return get((K) o);
    }

    @Override
    public V put(K k, V v) {
        return register(k, v);
    }

    @Override
    public V remove(Object o) {
        return unregister((K) o);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        registerAll((Map<K, V>) map);
    }

    @Override
    public void clear() {
        this.objects.clear();
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.objects.comparator();
    }

    @Override
    public SortedMap<K, V> subMap(K k, K k1) {
        return new Registry<>(this.objects.subMap(k, k1), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public SortedMap<K, V> headMap(K k) {
        return new Registry<>(this.objects.headMap(k), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public SortedMap<K, V> tailMap(K k) {
        return new Registry<>(this.objects.tailMap(k), this.keyNormalizer, this.keyRetriever);
    }

    @Override
    public K firstKey() {
        return this.objects.firstKey();
    }

    @Override
    public K lastKey() {
        return this.objects.lastKey();
    }

    @Override
    public Set<K> keySet() {
        return new HashSet<>(this.objects.keySet());
    }

    @Override
    public Collection<V> values() {
        return new LinkedList<>(this.objects.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>(this.objects.entrySet());
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply((K) key);
        }
        
        return this.objects.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.objects.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.objects.replaceAll(function);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply((K) key);
        }
        return this.objects.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.merge(key, value, remappingFunction);
    }
}