package com.stardevllc.starlib.objects.registry;

import com.stardevllc.starlib.objects.registry.RegistryChangeListener.Change;
import com.stardevllc.starlib.objects.registry.functions.*;
import com.stardevllc.starlib.observable.collections.set.ObservableTreeSet;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Represents a Registry of objects for storage in memory
 *
 * @param <K> The key type for the registry
 * @param <V> The type for the objects actually stored
 */
@Deprecated(since = "0.24.0")
public class Registry<K extends Comparable<K>, V> implements Set<RegistryObject<K, V>> {
    
    private final ObservableTreeSet<RegistryObject<K, V>> backingSet = new ObservableTreeSet<>();
    private final KeyNormalizer<K> keyNormalizer;
    private final KeyRetriever<V, K> keyRetriever;
    private final KeyGenerator<V, K> keyGenerator;
    private final KeySetter<K, V> keySetter;
    
    private boolean frozen;
    
    private final List<RegistryChangeListener<K, V>> changeListeners = new ArrayList<>();
    
    /**
     * Constructs a registry with the functions
     *
     * @param keyNormalizer The key normalizer
     * @param keyRetriever  The key retriever
     * @param keyGenerator  The key generator
     * @param keySetter     The key setter
     */
    public Registry(KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever, KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
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
    
    /**
     * Options when used for the copy constructor
     */
    public enum CopyOption {
        /**
         * This is to specify if the objects are to be copied
         */
        COPY_OBJECTS,
        
        /**
         * This specified if the frozen status is to be copied. Otherwise it false (Boolean default)
         */
        COPY_FROZEN
    }
    
    /**
     * Copy constructor <br>
     * This references the same KeyNormalizer, KeyRetriever, KeyGenerator, KeySetter from the passed in registry. But these are final and functional interfaces so that is perfectly fine as they are immutable<br>
     * If the {@link CopyOption#COPY_OBJECTS} option is provided it will call the {@link Registry#asMap()} method on the passed in registry and creates new {@link RegistryObject}s for each entry <br>
     * If the {@link CopyOption#COPY_FROZEN} option is provided, it will copy the frozen status
     *
     * @param registry The registry to copy
     * @param options  The options to use when copying the registry
     */
    public Registry(Registry<K, V> registry, CopyOption... options) {
        this(registry.keyNormalizer, registry.keyRetriever, registry.keyGenerator, registry.keySetter);
        boolean copyObjects = false, copyFrozen = false;
        if (options != null) {
            for (CopyOption option : options) {
                if (option == CopyOption.COPY_OBJECTS) {
                    copyObjects = true;
                } else if (option == CopyOption.COPY_FROZEN) {
                    copyFrozen = true;
                }
            }
        }
        
        if (copyObjects) {
            registry.asMap().forEach((key, value) -> this.backingSet.add(new RegistryObject<>(this, key, value)));
        }
        
        if (copyFrozen) {
            this.frozen = registry.frozen;
        }
    }
    
    /**
     * Constructs a Registry with a normalizer and retriever
     *
     * @param keyNormalizer The normalizer
     * @param keyRetriever  The retriever
     */
    public Registry(KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever) {
        this(keyNormalizer, keyRetriever, null, null);
    }
    
    /**
     * Constructs a registry with just a retriever
     *
     * @param keyRetriever The retriever
     */
    public Registry(KeyRetriever<V, K> keyRetriever) {
        this((KeyNormalizer<K>) null, keyRetriever);
    }
    
    /**
     * Constrcts a registry with a normalizer, generator and setter
     *
     * @param keyNormalizer The normalizer
     * @param keyGenerator  The generator
     * @param keySetter     The setter
     */
    public Registry(KeyNormalizer<K> keyNormalizer, KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
        this(keyNormalizer, null, keyGenerator, keySetter);
    }
    
    /**
     * Constructs a registry with a generator and setter
     *
     * @param keyGenerator The generator
     * @param keySetter    The setter
     */
    public Registry(KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
        this((KeyNormalizer<K>) null, keyGenerator, keySetter);
    }
    
    /**
     * Constructs a registry with a normalizer and a generator
     *
     * @param keyNormalizer The normalizer
     * @param keyGenerator  The generator
     */
    public Registry(KeyNormalizer<K> keyNormalizer, KeyGenerator<V, K> keyGenerator) {
        this(keyNormalizer, keyGenerator, null);
    }
    
    /**
     * Constructs a registry with a normalizer and setter
     *
     * @param keyNormalizer The normalizer
     * @param keySetter     The setter
     */
    public Registry(KeyNormalizer<K> keyNormalizer, KeySetter<K, V> keySetter) {
        this(keyNormalizer, null, null, keySetter);
    }
    
    /**
     * Constructs a register with a setter
     *
     * @param keySetter The setter
     */
    public Registry(KeySetter<K, V> keySetter) {
        this((KeyNormalizer<K>) null, null, null, keySetter);
    }
    
    /**
     * Constructs a registry with no functions or predefined registered objects
     */
    public Registry() {
        this((KeyNormalizer<K>) null, null, null, null);
    }
    
    /**
     * Constructs a registry with the functions
     *
     * @param existingValues The existing values
     * @param keyNormalizer  The key normalizer
     * @param keyRetriever   The key retriever
     * @param keyGenerator   The key generator
     * @param keySetter      The key setter
     */
    public Registry(Map<K, V> existingValues, KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever, KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
        this(keyNormalizer, keyRetriever, keyGenerator, keySetter);
        if (existingValues != null) {
            existingValues.forEach(this::register);
        }
    }
    
    /**
     * Constructs a Registry with a normalizer and retriever
     *
     * @param existingValues The existing values
     * @param keyNormalizer  The normalizer
     * @param keyRetriever   The retriever
     */
    public Registry(Map<K, V> existingValues, KeyNormalizer<K> keyNormalizer, KeyRetriever<V, K> keyRetriever) {
        this(existingValues, keyNormalizer, keyRetriever, null, null);
    }
    
    /**
     * Constructs a registry with just a retriever
     *
     * @param existingValues The existing values
     * @param keyRetriever   The retriever
     */
    public Registry(Map<K, V> existingValues, KeyRetriever<V, K> keyRetriever) {
        this(existingValues, null, keyRetriever);
    }
    
    /**
     * Constrcts a registry with a normalizer, generator and setter
     *
     * @param existingValues The existing values
     * @param keyNormalizer  The normalizer
     * @param keyGenerator   The generator
     * @param keySetter      The setter
     */
    public Registry(Map<K, V> existingValues, KeyNormalizer<K> keyNormalizer, KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
        this(existingValues, keyNormalizer, null, keyGenerator, keySetter);
    }
    
    /**
     * Constructs a registry with a generator and setter
     *
     * @param existingValues The existing values
     * @param keyGenerator   The generator
     * @param keySetter      The setter
     */
    public Registry(Map<K, V> existingValues, KeyGenerator<V, K> keyGenerator, KeySetter<K, V> keySetter) {
        this(existingValues, null, keyGenerator, keySetter);
    }
    
    /**
     * Constructs a registry with a normalizer and a generator
     *
     * @param existingValues The existing values
     * @param keyNormalizer  The normalizer
     * @param keyGenerator   The generator
     */
    public Registry(Map<K, V> existingValues, KeyNormalizer<K> keyNormalizer, KeyGenerator<V, K> keyGenerator) {
        this(existingValues, keyNormalizer, keyGenerator, null);
    }
    
    /**
     * Constructs a registry with a normalizer and setter
     *
     * @param existingValues The existing values
     * @param keyNormalizer  The normalizer
     * @param keySetter      The setter
     */
    public Registry(Map<K, V> existingValues, KeyNormalizer<K> keyNormalizer, KeySetter<K, V> keySetter) {
        this(existingValues, keyNormalizer, null, null, keySetter);
    }
    
    /**
     * Constructs a register with a setter
     *
     * @param existingValues The existing values
     * @param keySetter      The setter
     */
    public Registry(Map<K, V> existingValues, KeySetter<K, V> keySetter) {
        this(existingValues, null, null, null, keySetter);
    }
    
    /**
     * Constructs a registry with no functions
     *
     * @param existingValues The existing values
     */
    public Registry(Map<K, V> existingValues) {
        this(existingValues, null, null, null, null);
    }
    
    /**
     * Freezes registration of objects to this registry
     */
    public final void freeze() {
        this.frozen = true;
    }
    
    /**
     * Unfreezes registration of objects to this registry <br>
     * Subclasses must make it public to actually allow it, by default it is not allowed
     */
    protected void unfreeze() {
        this.frozen = false;
    }
    
    /**
     * Checks to see if this registry is frozen
     *
     * @return The frozen status of the registry
     */
    public final boolean isFrozen() {
        return frozen;
    }
    
    /**
     * Fires changes listeners for when things change
     *
     * @param change The change that occured
     * @return If the change was cancelled
     */
    protected final boolean fireChangeListeners(Change<K, V> change) {
        if (frozen) {
            return true;
        }
        
        for (RegistryChangeListener<K, V> listener : this.changeListeners) {
            listener.changed(change);
        }
        
        return change.cancelled().get();
    }
    
    /**
     * Adds a change listener to this registry
     *
     * @param listener the listener to add
     */
    public void addListener(RegistryChangeListener<K, V> listener) {
        this.changeListeners.add(listener);
    }
    
    /**
     * Gets the full RegistryObject associated with the key
     *
     * @param key The key
     * @return The object or null if not found
     */
    public RegistryObject<K, V> getObject(K key) {
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            if (registryObject.getKey().equals(key)) {
                return registryObject;
            }
        }
        
        return null;
    }
    
    /**
     * Returns the value associated with the key
     *
     * @param key The key
     * @return The value or null if not found
     */
    public V get(K key) {
        RegistryObject<K, V> object = getObject(key);
        if (object != null) {
            return object.get();
        }
        return null;
    }
    
    /**
     * Returns the object associated with the key or the default provided
     *
     * @param key          the key
     * @param defaultValue The value to return if not null
     * @return The key or default value
     */
    public RegistryObject<K, V> getObjectOrDefault(K key, V defaultValue) {
        RegistryObject<K, V> object = getObject(key);
        return object != null ? object : new RegistryObject<>(this, key, defaultValue);
    }
    
    /**
     * Gets the value with the key or a default value if not found
     *
     * @param key          The key
     * @param defaultValue The default value
     * @return The value or the default value if not found
     */
    public V getOrDefault(K key, V defaultValue) {
        return getObjectOrDefault(key, defaultValue).get();
    }
    
    /**
     * Similar to {@link #getObjectOrDefault(Comparable, Object)} but registers the default value to the key if not found
     *
     * @param key          The key
     * @param defaultValue The default value
     * @return The RegistryObject
     */
    public RegistryObject<K, V> computeObjectIfAbsent(K key, V defaultValue) {
        RegistryObject<K, V> registryObject = getObjectOrDefault(key, defaultValue);
        if (!frozen) {
            if (this.backingSet.add(registryObject)) {
                return registryObject;
            }
        }
        return null;
    }
    
    /**
     * Same as {@link #computeObjectIfAbsent(Comparable, Object)}
     *
     * @param key          The key
     * @param defaultValue The default value
     * @return The value that was assigned
     */
    public V computeIfAbsent(K key, V defaultValue) {
        RegistryObject<K, V> registryObject = computeObjectIfAbsent(key, defaultValue);
        if (registryObject != null) {
            return registryObject.get();
        }
        return null;
    }
    
    /**
     * Similar to {@link #computeObjectIfAbsent(Comparable, Object)}
     *
     * @param key          The key
     * @param defaultValue The default value
     * @return The registry object
     */
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
    
    /**
     * Registers an object to this registry if it is not frozen
     *
     * @param key   The key
     * @param value The value
     * @return The RegistryObject associated with the value
     */
    public RegistryObject<K, V> register(K key, V value) {
        if (frozen) {
            throw new IllegalStateException("Cannot register to a frozen registry.");
        }
        
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        
        RegistryObject<K, V> object = getObject(key);
        if (object == null) {
            object = new RegistryObject<>(this, key, value);
        } else {
            object.set(value);
        }
        
        return register(object);
    }
    
    /**
     * Registers a registry object to this registry
     *
     * @param registryObject The registry object to register
     * @return The instance of the registry object
     */
    public RegistryObject<K, V> register(RegistryObject<K, V> registryObject) {
        if (frozen) {
            throw new IllegalStateException("Cannot register to a frozen registry.");
        }
        
        if (registryObject == null) {
            throw new NullPointerException("Cannot register a null object");
        }
        
        if (registryObject.getKey() == null) {
            throw new NullPointerException("Cannot register a null key");
        }
        
        RegistryObject<K, V> object = getObject(registryObject.getKey());
        if (object == null) {
            object = registryObject;
            this.backingSet.add(object);
        } else {
            if (!Objects.equals(object.get(), registryObject.get())) {
                object.set(registryObject.get());
            }
        }
        
        if (keySetter != null) {
            keySetter.accept(object.getKey(), object.get());
        }
        
        return object;
    }
    
    /**
     * Registers all key-values in the map this this registry
     *
     * @param map The map with the key and value pairs
     * @return A list of all registry objects in order of the map if they were successful
     */
    public List<RegistryObject<K, V>> registerAll(Map<K, V> map) {
        if (frozen) {
            return List.of();
        }
        List<RegistryObject<K, V>> objects = new LinkedList<>();
        map.forEach((key, value) -> objects.add(register(key, value)));
        return objects;
    }
    
    /**
     * Registers an object to this registry <br>
     * A KeyRetriever or a KeyGenerator must be present in order for this to work properly
     *
     * @param value The value
     * @return The registry object instance
     */
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
    
    /**
     * Similar to {@link #register(Object)} but goes through the Iterable provided
     *
     * @param iterable The iterable
     * @return The list of registry objects in the order of the iterable
     */
    public List<RegistryObject<K, V>> registerAll(Iterable<V> iterable) {
        if (frozen) {
            return List.of();
        }
        
        List<RegistryObject<K, V>> objects = new LinkedList<>();
        for (V value : iterable) {
            objects.add(this.register(value));
        }
        
        return objects;
    }
    
    /**
     * Similar to {@link #registerAll(Iterable)} but in varargs form
     *
     * @param values The values
     * @return The list of registry objects in the order of the varargs
     */
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
    
    /**
     * Removes a registration
     *
     * @param key The key
     * @return If the removal was successful
     */
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
    
    /**
     * Similar to {@link #unregister(Comparable)} but removes all of the values associated
     *
     * @param value The value
     * @return If the removal was successful in any way
     */
    public boolean unregister(V value) {
        if (frozen) {
            return false;
        }
        return this.backingSet.removeIf(object -> object.get() != null && object.get().equals(value));
    }
    
    /**
     * Checks to see if this registry contains the key
     *
     * @param key The key
     * @return If the key exists
     */
    public boolean containsKey(K key) {
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            if (registryObject.getKey().equals(key)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks to see if this registry contains the value
     *
     * @param value The value
     * @return If the value exists
     */
    public boolean containsValue(V value) {
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            if (registryObject.get() != null && registryObject.get().equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns a set of keys that this registry has. <br>
     * NOTE: This is a copy of the key set and is not backed by the registry in any way (This is WIP)
     *
     * @return The keyset copy
     */
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            keySet.add(registryObject.getKey());
        }
        return keySet;
    }
    
    /**
     * Returns a collection of values that this registry has. <br>
     * NOTE: This is a copy of the values and is not backed by the registry in any way (This is WIP)
     *
     * @return The values copy
     */
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
    
    /**
     * Returns this registry as a Map. <br>
     * This is a copy and is not backed by the Registry in any way (This is WIP)
     *
     * @return The registry as a map copy
     */
    public Map<K, V> asMap() {
        Map<K, V> map = new TreeMap<>();
        for (RegistryObject<K, V> registryObject : this.backingSet) {
            map.put(registryObject.getKey(), registryObject.get());
        }
        
        return map;
    }
    
    /**
     * The KeyNormalizer transforms the keys into a standard form or format and is applied to all keys when registering and when used as a check
     *
     * @return the key normalizer
     */
    public KeyNormalizer<K> getKeyNormalizer() {
        return keyNormalizer;
    }
    
    /**
     * The KeyRetriever is used to retrieve keys from an object
     *
     * @return The key retriever
     */
    public KeyRetriever<V, K> getKeyRetriever() {
        return keyRetriever;
    }
    
    /**
     * The KeyGenerator is used to generate keys automatically
     *
     * @return The Key Generator
     */
    public KeyGenerator<V, K> getKeyGenerator() {
        return keyGenerator;
    }
    
    /**
     * The key setter is used to set the key to an object
     *
     * @return The key setter
     */
    public KeySetter<K, V> getKeySetter() {
        return keySetter;
    }
    
    /**
     * A for-each implementation using a lambda to make things a bit cleaner
     *
     * @param biConsumer The consumer for each element
     */
    public void forEach(BiConsumer<K, V> biConsumer) {
        for (RegistryObject<K, V> object : this) {
            biConsumer.accept(object.getKey(), object.get());
        }
    }
}