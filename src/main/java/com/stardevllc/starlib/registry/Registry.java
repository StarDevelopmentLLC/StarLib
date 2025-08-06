package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.builder.IBuilder;
import com.stardevllc.starlib.observable.collections.ObservableHashSet;
import com.stardevllc.starlib.registry.functions.*;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.*;
import java.util.stream.Collectors;

public class Registry<K extends Comparable<K>, V> implements Iterable<V>, SortedMap<K, V> {
    protected final TreeMap<K, V> objects = new TreeMap<>();
    protected final KeyNormalizer<K> keyNormalizer;
    protected final KeyRetriever<V, K> keyRetriever;
    protected final KeyGenerator<V, K> keyGenerator;
    protected final KeySetter<K, V> keySetter;
    protected final List<RegisterListener<K, V>> registerListeners = new ArrayList<>();
    protected final List<UnregisterListener<K, V>> unregisterListeners = new ArrayList<>();
    
    protected final Lock lock = new ReentrantLock();

    public Registry(Map<K, V> initialObjects, KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever, KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
        if (initialObjects != null && !initialObjects.isEmpty()) {
            objects.putAll(initialObjects);
        }
        this.keyNormalizer = keyNormalizer;
        this.keyRetriever = keyRetriever;
        this.keyGenerator = keyGenerator;
        this.keySetter = keySetter;
    }

    public Registry() {
        this(null, null, null, null, null);
    }
    
    public Builder<K, V, ?, ?> toBuilder() {
        Builder<K, V, ?, ?> builder = new Builder<>();
        builder.keyNormalizer(this.keyNormalizer);
        builder.keyRetriever(this.keyRetriever);
        builder.keyGenerator(this.keyGenerator);
        builder.keySetter(this.keySetter);
        for (RegisterListener<K, V> registerListener : this.registerListeners) {
            builder.addRegisterListener(registerListener);
        }
        
        for (UnregisterListener<K, V> unregisterListener : this.unregisterListeners) {
            builder.addUnregisterListener(unregisterListener);
        }
        return builder;
    }

    public RegistryObject<K, V> register(V object) {
        K key = null;
        
        if (keyRetriever != null) {
            key = keyRetriever.apply(object);
        }
        
        if (key == null) {
            key = keyGenerator.apply(object);
        }
        
        return register(key, object);
    }
    
    public RegistryObject<K, V> register(K key, V object) {
        if (key == null) {
            return null;
        }
        
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        
        if (keySetter != null) {
            keySetter.accept(key, object);
        }
        
        lock.lock();
        this.objects.put(key, object);
        lock.unlock();
        for (RegisterListener<K, V> registerListener : this.registerListeners) {
            registerListener.onRegister(key, object);
        }
        return new RegistryObject<>(key, object);
    }

    public List<RegistryObject<K, V>> registerAll(Map<K, V> map) {
        List<RegistryObject<K, V>> registeredObjects = new LinkedList<>();
        map.forEach((k, v) -> registeredObjects.add(register(k, v)));
        return registeredObjects;
    }
    
    public List<RegistryObject<K, V>> registerAll(Collection<V> collection) {
        return collection.stream().map(this::register).collect(Collectors.toCollection(LinkedList::new));
    }
    
    @SafeVarargs
    public final List<RegistryObject<K, V>> registerAll(V... values) {
        List<RegistryObject<K, V>> registeredObjects = new LinkedList<>();
        if (values != null) {
            for (V value : values) {
                registeredObjects.add(register(value));
            }
        }
        
        return registeredObjects;
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
        for (UnregisterListener<K, V> unregisterListener : this.unregisterListeners) {
            unregisterListener.onUnregister(key, removed);
        }
        return removed;
    }
    
    public void addRegisterListener(RegisterListener<K, V> registerListener) {
        this.registerListeners.add(registerListener);
    }
    
    public void addUnregisterListener(UnregisterListener<K, V> unregisterListener) {
        this.unregisterListeners.add(unregisterListener);
    }
    
    public void removeRegisterListener(RegisterListener<K, V> registerListener) {
        this.registerListeners.remove(registerListener);
    }
    
    public void removeUnregisterListener(UnregisterListener<K, V> unregisterListener) {
        this.unregisterListeners.remove(unregisterListener);
    }

    @Override
    public Iterator<V> iterator() {
        return new ArrayList<>(objects.values()).iterator();
    }

    @Override
    public void forEach(Consumer<? super V> action) {
        for (V value : this.objects.values()) {
            action.accept(value);
        }
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
        RegistryObject<K, V> registryObject = register(k, v);
        if (registryObject == null) {
            return null;
        }
        
        return registryObject.getObject();
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
        Builder<K, V, ?, ?> builder = toBuilder();
        builder.addRegisterListener(this::register);
        builder.addUnregisterListener((key, value) -> unregister(key));
        return builder.build();
    }

    @Override
    public SortedMap<K, V> headMap(K k) {
        Builder<K, V, ?, ?> builder = toBuilder();
        builder.addRegisterListener(this::register);
        builder.addUnregisterListener((key, value) -> unregister(key));
        return builder.build();
    }

    @Override
    public SortedMap<K, V> tailMap(K k) {
        Builder<K, V, ?, ?> builder = toBuilder();
        builder.addRegisterListener(this::register);
        builder.addUnregisterListener((key, value) -> unregister(key));
        return builder.build();
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
        ObservableHashSet<Entry<K, V>> set = new ObservableHashSet<>(this.objects.entrySet());
        set.addListener(event -> {
            if (event.added() != null) {
                register(event.added().getKey(), event.added().getValue());
            } else if (event.removed() != null) {
                unregister(event.removed().getKey());
            }
        });
        return set;
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
    
    public static class Builder<K extends Comparable<K>, V, R extends Registry<K, V>, B extends Builder<K, V, R, B>> implements IBuilder<R, B> {
        protected TreeMap<K, V> objects = new TreeMap<>();
        protected KeyNormalizer<K> keyNormalizer;
        protected KeyRetriever<V, K> keyRetriever;
        protected KeyGenerator<V, K> keyGenerator;
        protected KeySetter<K, V> keySetter;
        protected List<RegisterListener<K, V>> registerListeners = new ArrayList<>();
        protected List<UnregisterListener<K, V>> unregisterListeners = new ArrayList<>();
        
        public Builder() {}
        
        public Builder(Builder<K, V, R, B> builder) {
            this.objects.putAll(builder.objects);
            this.keyNormalizer = builder.keyNormalizer;
            this.keyRetriever = builder.keyRetriever;
            this.keyGenerator = builder.keyGenerator;
            this.keySetter = builder.keySetter;
            this.registerListeners.addAll(builder.registerListeners);
            this.unregisterListeners.addAll(builder.unregisterListeners);
        }
        
        public B initialObjects(TreeMap<K, V> objects) {
            this.objects = objects;
            return self();
        }

        public B keyNormalizer(KeyNormalizer<K> keyNormalizer) {
            this.keyNormalizer = keyNormalizer;
            return self();
        }

        public B keyRetriever(KeyRetriever<V, K> keyRetriever) {
            this.keyRetriever = keyRetriever;
            return self();
        }

        public B keyGenerator(KeyGenerator<V, K> keyGenerator) {
            this.keyGenerator = keyGenerator;
            return self();
        }
        
        public B keySetter(KeySetter<K, V> keySetter) {
            this.keySetter = keySetter;
            return self();
        }
        
        public B addRegisterListener(RegisterListener<K, V> listener) {
            this.registerListeners.add(listener);
            return self();
        }
        
        public B addUnregisterListener(UnregisterListener<K, V> listener) {
            this.unregisterListeners.add(listener);
            return self();
        }
        
        public R build() {
            Registry<K, V> registry = new Registry<>(objects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
            
            this.registerListeners.forEach(registry::addRegisterListener);
            this.unregisterListeners.forEach(registry::addUnregisterListener);
            
            return (R) registry;
        }
        
        @Override
        public B clone() {
            return null;
        }
    }
}