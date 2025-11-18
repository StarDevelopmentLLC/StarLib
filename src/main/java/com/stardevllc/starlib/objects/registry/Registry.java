package com.stardevllc.starlib.objects.registry;

import com.stardevllc.starlib.builder.IBuilder;
import com.stardevllc.starlib.objects.registry.RegistryChangeListener.Change;
import com.stardevllc.starlib.objects.registry.functions.*;
import com.stardevllc.starlib.observable.collections.ObservableTreeSet;

import java.util.*;
import java.util.stream.Collectors;

public class Registry<K extends Comparable<K>, V> implements Set<RegistryObject<K, V>> {
    
    protected final ObservableTreeSet<RegistryObject<K, V>> backingSet = new ObservableTreeSet<>();
    protected final KeyNormalizer<K> keyNormalizer;
    protected final KeyRetriever<V, K> keyRetriever;
    protected final KeyGenerator<V, K> keyGenerator;
    protected final KeySetter<K, V> keySetter;
    
    private boolean frozen;
    
    protected final List<RegistryChangeListener<K, V>> changeListeners = new ArrayList<>();
    
    public Registry() {
        this(null);
    }
    
    public Registry(Collection<RegistryObject<K, V>> collection) {
        this(collection, null, null, null, null);
    }
    
    public Registry(Collection<RegistryObject<K, V>> collection, KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever, KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
//        super(collection);
        this.keyNormalizer = keyNormalizer;
        this.keyRetriever = keyRetriever;
        this.keyGenerator = keyGenerator;
        this.keySetter = keySetter;
        this.backingSet.addListener(c -> {
            if (isFrozen()) {
                if (c.added() != null || c.removed() != null) {
                    c.cancelled().set(true);
                }
            }
        });
        this.backingSet.addListener(c -> {
            RegistryObject<K, V> added = c.added();
            RegistryObject<K, V> removed = c.removed();
            //The keys are the same for both the added and removed objects
            if (added != null && removed != null && added.getKey().equals(removed.getKey())) {
                Change<K, V> change = Change.full(Registry.this, added.getKey(), added.get(), removed.get());
                fireChangeListeners(change);
                c.cancelled().set(change.cancelled().get());
            } else {
                if (added != null) {
                    Change<K, V> change = Change.added(Registry.this, added.getKey(), added.get());
                    fireChangeListeners(change);
                    c.cancelled().set(change.cancelled().get());
                }
                
                if (removed != null && !c.cancelled().get()) {
                    Change<K, V> change = Change.removed(Registry.this, removed.getKey(), removed.get());
                    fireChangeListeners(change);
                    c.cancelled().set(change.cancelled().get());
                }
            }
        });
    }
    
    public final void freeze() {
        this.frozen = true;
    }
    
    public final boolean isFrozen() {
        return frozen;
    }
    
    protected boolean fireChangeListeners(Change<K, V> change) {
        if (frozen) {
            return true;
        }
        
        boolean cancelled = false;
        for (RegistryChangeListener<K, V> listener : this.changeListeners) {
            listener.changed(change);
            if (!cancelled) {
                if (change.cancelled().get()) {
                    cancelled = true;
                }
            }
        }
        
        return cancelled;
    }
    
    public void addChangeListener(RegistryChangeListener<K, V> listener) {
        this.changeListeners.add(listener);
    }
    
    public RegistryObject<K, V> getObject(K key) {
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            if (registryObject.getKey().equals(key)) {
                return registryObject;
            }
        }
        
        return null;
    }
    
    public V get(K key) {
        RegistryObject<K, V> object = getObject(key);
        if (object != null) {
            return object.get();
        }
        return null;
    }
    
    public RegistryObject<K, V> getObjectOrDefault(K key, V defaultValue) {
        RegistryObject<K, V> object = getObject(key);
        return object != null ? object : new RegistryObject<>(this, key, defaultValue);
    }
    
    public V getOrDefault(K key, V defaultValue) {
        return getObjectOrDefault(key, defaultValue).get();
    }
    
    public RegistryObject<K, V> computeObjectIfAbsent(K key, V defaultValue) {
        RegistryObject<K, V> registryObject = getObjectOrDefault(key, defaultValue);
        if (!frozen) {
            if (this.backingSet.add(registryObject)) {
                return registryObject;
            }
        }
        return null;
    }
    
    public V computeIfAbsent(K key, V defaultValue) {
        RegistryObject<K, V> registryObject = computeObjectIfAbsent(key, defaultValue);
        if (registryObject != null) {
            return registryObject.get();
        }
        return null;
    }
    
    public RegistryObject<K, V> computeIfAbsent(K key, RegistryObject<K, V> defaultValue) {
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            if (registryObject.getKey().equals(key)) {
                return registryObject;
            }
        }
        
        if (!frozen) {
            if (this.backingSet.add(defaultValue)) {
                return defaultValue;
            }
        }
        return null;
    }
    
    public RegistryObject<K, V> register(K key, V value) {
        if (frozen) {
            return null;
        }
        
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        
        return computeObjectIfAbsent(key, value);
    }
    
    public RegistryObject<K, V> register(RegistryObject<K, V> registryObject) {
        if (frozen) {
            return null;
        }
        return computeIfAbsent(registryObject.getKey(), registryObject);
    }
    
    public List<RegistryObject<K, V>> registerAll(Map<K, V> map) {
        if (frozen) {
            return List.of();
        }
        List<RegistryObject<K, V>> objects = new LinkedList<>();
        map.forEach((key, value) -> objects.add(register(key, value)));
        return objects;
    }
    
    public RegistryObject<K, V> register(V value) {
        if (frozen) {
            return null;
        }
        
        K key = null;
        if (keyRetriever != null) {
            key = keyRetriever.apply(value);
        }
        
        if (key == null && keyGenerator != null) {
            key = keyGenerator.apply(value);
        }
        
        if (key == null) {
            throw new IllegalArgumentException("Could not find a key when registering " + value);
        }
        
        return register(key, value);
    }
    
    public List<RegistryObject<K, V>> registerAll(Collection<V> collection) {
        if (frozen) {
            return List.of();
        }
        return collection.stream().map(this::register).collect(Collectors.toCollection(LinkedList::new));
    }
    
    @SafeVarargs
    public final List<RegistryObject<K, V>> registerAll(V... values) {
        if (values == null || frozen) {
            return List.of();
        }
        
        List<RegistryObject<K, V>> objects = new LinkedList<>();
        for (V value : values) {
            objects.add(register(value));
        }
        return objects;
    }
    
    public boolean unregister(K key) {
        if (frozen) {
            return false;
        }
        if (keyNormalizer != null) {
            key = keyNormalizer.apply(key);
        }
        K finalKey = key;
        return this.backingSet.removeIf(object -> object.getKey().equals(finalKey));
    }
    
    public boolean unregister(V value) {
        if (frozen) {
            return false;
        }
        return this.backingSet.removeIf(object -> object.get() != null && object.get().equals(value));
    }
    
    public boolean containsKey(Object key) {
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            if (registryObject.getKey().equals(key)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean containsValue(Object value) {
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            if (registryObject.get() != null && registryObject.get().equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            keySet.add(registryObject.getKey());
        }
        return keySet;
    }
    
    public Collection<V> values() {
        List<V> values = new LinkedList<>();
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            values.add(registryObject.get());
        }
        return values;
    }
    
    @Override
    public int size() {
        return this.backingSet.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.backingSet.isEmpty();
    }
    
    @Override
    public boolean contains(Object o) {
        return this.backingSet.contains(o);
    }
    
    @Override
    public Iterator<RegistryObject<K, V>> iterator() {
        return this.backingSet.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return this.backingSet.toArray();
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        return this.backingSet.toArray(a);
    }
    
    @Override
    public boolean add(RegistryObject<K, V> kvRegistryObject) {
        if (frozen) {
            return false;
        }
        RegistryObject<K, V> object = register(kvRegistryObject);
        return object != null;
    }
    
    @Override
    public boolean remove(Object o) {
        if (frozen) {
            return false;
        }
        return unregister((K) o);
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        return this.backingSet.containsAll(c);
    }
    
    @Override
    public boolean addAll(Collection<? extends RegistryObject<K, V>> c) {
        if (frozen) {
            return false;
        }
        return this.backingSet.addAll(c);
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        if (frozen) {
            return false;
        }
        return this.backingSet.retainAll(c);
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        if (frozen) {
            return false;
        }
        return this.backingSet.removeAll(c);
    }
    
    @Override
    public void clear() {
        if (frozen) {
            return;
        }
        this.backingSet.clear();
    }
    
    public Map<K, V> asMap() {
        Map<K, V> map = new TreeMap<>();
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            map.put(registryObject.getKey(), registryObject.get());
        }
        
        return map;
    }
    
    public Builder<K, V, ?, ?> asBuilder() {
        Builder<K, V, ?, ?> builder = new Builder<>();
        builder.initialObjects(this.backingSet).keyGenerator(this.keyGenerator).keyNormalizer(this.keyNormalizer).keyRetriever(this.keyRetriever);
        for (RegistryChangeListener<K, V> changeListener : this.changeListeners) {
            builder.addChangeListener(changeListener);
        }
        return builder;
    }
    
    public static <K extends Comparable<K>, V> Builder<K, V, Registry<K, V>, ?> builder() {
        return new Builder<>();
    }
    
    /**
     * A builder to make creating registries easier
     *
     * @param <K> The key type
     * @param <V> The value type
     * @param <R> The registry type
     * @param <B> The builder type
     */
    public static class Builder<K extends Comparable<K>, V, R extends Registry<K, V>, B extends Registry.Builder<K, V, R, B>> implements IBuilder<R, B> {
        /**
         * Initial objects
         */
        protected final TreeSet<RegistryObject<K, V>> objects = new TreeSet<>();
        
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
        
        protected final List<RegistryChangeListener<K, V>> changeListeners = new LinkedList<>();
        
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
            this.objects.addAll(builder.objects);
            this.keyNormalizer = builder.keyNormalizer;
            this.keyRetriever = builder.keyRetriever;
            this.keyGenerator = builder.keyGenerator;
            this.keySetter = builder.keySetter;
            this.changeListeners.addAll(builder.changeListeners);
        }
        
        /**
         * Initial objects
         *
         * @param objects The objects
         * @return This builder
         */
        public B initialObjects(Collection<RegistryObject<K, V>> objects) {
            this.objects.addAll(objects);
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
        
        public B addChangeListener(RegistryChangeListener<K, V> listener) {
            this.changeListeners.add(listener);
            return self();
        }
        
        /**
         * Builds the registry
         *
         * @return The new registry
         */
        public R build() {
            Registry<K, V> registry = new Registry<>(objects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
            this.changeListeners.forEach(registry::addChangeListener);
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