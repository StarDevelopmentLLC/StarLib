package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.builder.IBuilder;
import com.stardevllc.starlib.objects.registry.functions.*;
import com.stardevllc.starlib.observable.collections.ObservableHashSet;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Represents a Registry that maps objects of type {@link V} to keys of type {@link V}
 *
 * @param <K> The key type
 * @param <V> The value type
 * @deprecated See {@link com.stardevllc.starlib.objects.registry.Registry}
 */
@SuppressWarnings("removal")
@Deprecated(forRemoval = true, since = "0.20.0")
public class Registry<K extends Comparable<K>, V> implements Iterable<V>, SortedMap<K, V> {
    /**
     * The objects of the registry
     */
    protected final TreeMap<K, V> objects = new TreeMap<>();
    
    /**
     * The key normalizer
     */
    protected final KeyNormalizer<K> keyNormalizer;
    
    /**
     * The key retriever
     */
    protected final KeyRetriever<V, K> keyRetriever;
    
    /**
     * The key generator
     */
    protected final KeyGenerator<V, K> keyGenerator;
    
    /**
     * The key setter
     */
    protected final KeySetter<K, V> keySetter;
    
    /**
     * The listeners for registration
     */
    protected final List<RegisterListener<K, V>> registerListeners = new ArrayList<>();
    
    /**
     * The listeners for unregistration
     */
    protected final List<UnregisterListener<K, V>> unregisterListeners = new ArrayList<>();
    
    /**
     * Lock for thread safety
     */
    protected final Lock lock = new ReentrantLock();
    
    /**
     * Creates a new registry
     *
     * @param initialObjects The initial objects
     * @param keyNormalizer  The key normalizer
     * @param keyRetriever   The key retriever
     * @param keyGenerator   The key generator
     * @param keySetter      The key setter
     */
    public Registry(Map<K, V> initialObjects, KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever, KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
        if (initialObjects != null && !initialObjects.isEmpty()) {
            objects.putAll(initialObjects);
        }
        this.keyNormalizer = keyNormalizer;
        this.keyRetriever = keyRetriever;
        this.keyGenerator = keyGenerator;
        this.keySetter = keySetter;
    }
    
    /**
     * Creates a blank registry
     */
    public Registry() {
        this(null, null, null, null, null);
    }
    
    /**
     * Converts this registry into a Builder instance
     *
     * @return The new builder instance
     */
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
    
    /**
     * Registers an object to this registry
     *
     * @param object The object
     * @return A RegistryObject representing the registered information
     */
    public RegistryObject<K, V> register(V object) {
        K key = null;
        
        if (keyRetriever != null) {
            key = keyRetriever.apply(object);
        }
        
        if (key == null) {
            if (keyGenerator != null) {
                key = keyGenerator.apply(object);
            }
        }
        
        return register(key, object);
    }
    
    /**
     * Registers an object to this registry
     *
     * @param key    The key of for the object
     * @param object The object itself
     * @return A RegistryObject representing the registered information
     */
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
        return new RegistryObject<>(this, key, object);
    }
    
    /**
     * Registers all objects in the map to this registry
     *
     * @param map The map of objects to register
     * @return A list of registered information
     */
    public List<RegistryObject<K, V>> registerAll(Map<K, V> map) {
        List<RegistryObject<K, V>> registeredObjects = new LinkedList<>();
        map.forEach((k, v) -> registeredObjects.add(register(k, v)));
        return registeredObjects;
    }
    
    /**
     * Registers all objects in the collection to this registry
     *
     * @param collection The collection of objects to register
     * @return A list of registered information
     */
    public List<RegistryObject<K, V>> registerAll(Collection<V> collection) {
        return collection.stream().map(this::register).collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * Registers all objects in the varargs to this registry
     *
     * @param values The objects to register
     * @return The registered information
     */
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
    
    /**
     * Gets an object from the registry
     *
     * @param key The key
     * @return The value
     */
    public V get(K key) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return objects.get(key);
    }
    
    /**
     * Gets all objects as a Map copy of this registry
     *
     * @return A copy of the objects in a TreeMap
     */
    public Map<K, V> getObjects() {
        return new TreeMap<>(this.objects);
    }
    
    /**
     * Determines if this registry contains the key
     *
     * @param key The key to test
     * @return If this registry contains that key
     */
    public boolean contains(K key) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return objects.containsKey(key);
    }
    
    /**
     * Unregisters an object from this registry
     *
     * @param key The key of the object to unregister
     * @return The value that was associated (if any) with that key
     */
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
    
    /**
     * Adds a register listener
     *
     * @param registerListener The listener
     */
    public void addRegisterListener(RegisterListener<K, V> registerListener) {
        this.registerListeners.add(registerListener);
    }
    
    /**
     * Adds an unregister listener
     *
     * @param unregisterListener The listener
     */
    public void addUnregisterListener(UnregisterListener<K, V> unregisterListener) {
        this.unregisterListeners.add(unregisterListener);
    }
    
    /**
     * Removes a register listener
     *
     * @param registerListener The listener
     */
    public void removeRegisterListener(RegisterListener<K, V> registerListener) {
        this.registerListeners.remove(registerListener);
    }
    
    /**
     * Removes an unregister listener
     *
     * @param unregisterListener The listener
     */
    public void removeUnregisterListener(UnregisterListener<K, V> unregisterListener) {
        this.unregisterListeners.remove(unregisterListener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<V> iterator() {
        return new ArrayList<>(objects.values()).iterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(Consumer<? super V> action) {
        for (V value : this.objects.values()) {
            action.accept(value);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Spliterator<V> spliterator() {
        return new ArrayList<>(objects.values()).spliterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return objects.size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return objects.isEmpty();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object o) {
        return objects.containsKey(o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object o) {
        return objects.containsValue(o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V get(Object o) {
        return get((K) o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K k, V v) {
        RegistryObject<K, V> registryObject = register(k, v);
        if (registryObject == null) {
            return null;
        }
        
        return registryObject.get();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(Object o) {
        return unregister((K) o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        registerAll((Map<K, V>) map);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.objects.clear();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<? super K> comparator() {
        return this.objects.comparator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> subMap(K k, K k1) {
        Builder<K, V, ?, ?> builder = toBuilder();
        builder.addRegisterListener(this::register);
        builder.addUnregisterListener((key, value) -> unregister(key));
        return builder.build();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> headMap(K k) {
        Builder<K, V, ?, ?> builder = toBuilder();
        builder.addRegisterListener(this::register);
        builder.addUnregisterListener((key, value) -> unregister(key));
        return builder.build();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> tailMap(K k) {
        Builder<K, V, ?, ?> builder = toBuilder();
        builder.addRegisterListener(this::register);
        builder.addUnregisterListener((key, value) -> unregister(key));
        return builder.build();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public K firstKey() {
        return this.objects.firstKey();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public K lastKey() {
        return this.objects.lastKey();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        return new HashSet<>(this.objects.keySet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        return new LinkedList<>(this.objects.values());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        ObservableHashSet<Entry<K, V>> set = new ObservableHashSet<>(this.objects.entrySet());
        set.addListener(c -> {
            if (c.added() != null) {
                register(c.added().getKey(), c.added().getValue());
            } else if (c.removed() != null) {
                unregister(c.removed().getKey());
            }
        });
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply((K) key);
        }
        
        return this.objects.getOrDefault(key, defaultValue);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.objects.forEach(action);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.objects.replaceAll(function);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V putIfAbsent(K key, V value) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.putIfAbsent(key, value);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object key, Object value) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply((K) key);
        }
        return this.objects.remove(key, value);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.replace(key, oldValue, newValue);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V replace(K key, V value) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.replace(key, value);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.computeIfAbsent(key, mappingFunction);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.computeIfPresent(key, remappingFunction);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.compute(key, remappingFunction);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        return this.objects.merge(key, value, remappingFunction);
    }
    
    /**
     * A builder to make creating registries easier
     *
     * @param <K> The key type
     * @param <V> The value type
     * @param <R> The registry type
     * @param <B> The builder type
     */
    public static class Builder<K extends Comparable<K>, V, R extends Registry<K, V>, B extends Builder<K, V, R, B>> implements IBuilder<R, B> {
        /**
         * Initial objects
         */
        protected TreeMap<K, V> objects = new TreeMap<>();
        
        /**
         * Key normalizer
         */
        protected KeyNormalizer<K> keyNormalizer;
        
        /**
         * Key retriever
         */
        protected KeyRetriever<V, K> keyRetriever;
        
        /**
         * Key generator
         */
        protected KeyGenerator<V, K> keyGenerator;
        
        /**
         * Key setter
         */
        protected KeySetter<K, V> keySetter;
        
        /**
         * Register listeners
         */
        protected List<RegisterListener<K, V>> registerListeners = new ArrayList<>();
        
        /**
         * Unregister listeners
         */
        protected List<UnregisterListener<K, V>> unregisterListeners = new ArrayList<>();
        
        /**
         * Emtpy builder
         */
        public Builder() {
        }
        
        /**
         * Copy constructor
         *
         * @param builder other builder
         */
        public Builder(Builder<K, V, R, B> builder) {
            this.objects.putAll(builder.objects);
            this.keyNormalizer = builder.keyNormalizer;
            this.keyRetriever = builder.keyRetriever;
            this.keyGenerator = builder.keyGenerator;
            this.keySetter = builder.keySetter;
            this.registerListeners.addAll(builder.registerListeners);
            this.unregisterListeners.addAll(builder.unregisterListeners);
        }
        
        /**
         * Initial objects
         *
         * @param objects The objects
         * @return This builder
         */
        public B initialObjects(TreeMap<K, V> objects) {
            this.objects = objects;
            return self();
        }
        
        /**
         * Key normalizer
         *
         * @param keyNormalizer The normalizer
         * @return This builder
         */
        public B keyNormalizer(KeyNormalizer<K> keyNormalizer) {
            this.keyNormalizer = keyNormalizer;
            return self();
        }
        
        /**
         * Key retriever
         *
         * @param keyRetriever The retriever
         * @return This builder
         */
        public B keyRetriever(KeyRetriever<V, K> keyRetriever) {
            this.keyRetriever = keyRetriever;
            return self();
        }
        
        /**
         * Key generator
         *
         * @param keyGenerator The generator
         * @return This builder
         */
        public B keyGenerator(KeyGenerator<V, K> keyGenerator) {
            this.keyGenerator = keyGenerator;
            return self();
        }
        
        /**
         * Key setter
         *
         * @param keySetter The setter
         * @return This builder
         */
        public B keySetter(KeySetter<K, V> keySetter) {
            this.keySetter = keySetter;
            return self();
        }
        
        /**
         * Adds register listener
         *
         * @param listener The listener
         * @return This builder
         */
        public B addRegisterListener(RegisterListener<K, V> listener) {
            this.registerListeners.add(listener);
            return self();
        }
        
        /**
         * Adds unregister listener
         *
         * @param listener The listener
         * @return This builder
         */
        public B addUnregisterListener(UnregisterListener<K, V> listener) {
            this.unregisterListeners.add(listener);
            return self();
        }
        
        /**
         * Builds the registry
         *
         * @return The new registry
         */
        public R build() {
            Registry<K, V> registry = new Registry<>(objects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
            
            this.registerListeners.forEach(registry::addRegisterListener);
            this.unregisterListeners.forEach(registry::addUnregisterListener);
            
            return (R) registry;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public B clone() {
            return (B) new Builder<>(this);
        }
    }
}