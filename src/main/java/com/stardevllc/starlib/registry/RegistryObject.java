package com.stardevllc.starlib.registry;

/**
 * Represents an object in a registryt
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public class RegistryObject<K extends Comparable<K>, V> {
    /**
     * The key of the object
     */
    protected final K key;
    
    /**
     * The value of the object
     */
    protected V object; //Planned on some other things, not sure what to do right now
    
    /**
     * Constructs a new RegistryObject
     *
     * @param key    The key
     * @param object The value
     */
    public RegistryObject(K key, V object) {
        this.key = key;
        this.object = object;
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
    public V getObject() {
        return object;
    }
}