package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.NamespacedKey;

import java.util.*;

public class NamespacedRegistry<V> extends Registry<NamespacedKey, V> {
    public NamespacedRegistry(Map<NamespacedKey, V> initialObjects) {
        super(initialObjects);
    }

    public NamespacedRegistry() {
    }
    
    public Collection<V> getObjectsInNamespace(String namespace) {
        List<V> objects = new ArrayList<>();
        
        this.getObjects().forEach((key, value) -> {
            if (key.namespace().equalsIgnoreCase(namespace)) {
                objects.add(value);
            }
        });
        
        return objects;
    }
    
    public Map<NamespacedKey, V> getObjectsMatchingKey(String key) {
        Map<NamespacedKey, V> objects = new TreeMap<>();
        this.getObjects().forEach((k, value) -> {
            if (k.key().equalsIgnoreCase(key)) {
                objects.put(k, value);
            }
        });
        
        return objects;
    }
}