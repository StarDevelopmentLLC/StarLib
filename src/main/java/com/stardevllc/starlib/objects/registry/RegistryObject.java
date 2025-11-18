package com.stardevllc.starlib.objects.registry;

import com.stardevllc.starlib.objects.registry.RegistryChangeListener.Change;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents an object in a registry
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public class RegistryObject<K extends Comparable<K>, V> implements Comparable<RegistryObject<K, V>> {
    private final Registry<K, V> registry;
    
    private final K key;
    
    private V object; //Planned on some other things, not sure what to do right now
    
    /**
     * Constructs a new RegistryObject
     *
     * @param key    The key
     * @param object The value
     */
    public RegistryObject(Registry<K, V> registry, K key, V object) {
        this.registry = registry;
        if (registry.getKeyNormalizer() != null) {
            this.key = registry.getKeyNormalizer().apply(key);
        } else {
            this.key = key;
        }
        
        this.object = object;
    }
    
    /**
     * Gets the registry that this RegistryObject is assigned to
     *
     * @return The registry
     */
    public Registry<K, V> getRegistry() {
        return registry;
    }
    
    /**
     * Retrieves the key
     *
     * @return The key
     */
    public K getKey() {
        return key;
    }
    
    /**
     * Retrieves the value
     *
     * @return The value or null
     */
    public V get() {
        return object;
    }
    
    /**
     * Similar to the {@link Optional#ifPresent(Consumer)} method, just makes it easier for handling null
     *
     * @param presentConsumer The consumer to call if the value here is not null
     */
    public void ifPresent(Consumer<V> presentConsumer) {
        if (this.object != null) {
            presentConsumer.accept(this.object);
        }
    }
    
    public final void set(V newValue) {
        if (registry.isFrozen()) {
            return;
        }
        if (!registry.fireChangeListeners(Change.full(registry, key, newValue, this.object))) {
            this.object = newValue;
        }
    }
    
    @Override
    public int compareTo(RegistryObject<K, V> o) {
        return key.compareTo(o.key);
    }
    
    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof RegistryObject<?, ?> that)) {
            return false;
        }
        
        return Objects.equals(registry, that.registry) && Objects.equals(key, that.key);
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(registry);
        result = 31 * result + Objects.hashCode(key);
        return result;
    }
}