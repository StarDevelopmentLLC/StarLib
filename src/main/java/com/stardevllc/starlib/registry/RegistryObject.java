package com.stardevllc.starlib.registry;

public class RegistryObject<K extends Comparable<K>, V> {
    protected final K key;
    protected V object; //Planned on some other things, not sure what to do right now
    
    public RegistryObject(K key, V object) {
        this.key = key;
        this.object = object;
    }
    
    public K getKey() {
        return key;
    }
    
    public V getObject() {
        return object;
    }
}