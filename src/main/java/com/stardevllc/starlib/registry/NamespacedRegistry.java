package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.NamespacedKey;
import com.stardevllc.starlib.registry.functions.Normalizer;
import com.stardevllc.starlib.registry.functions.Register;

import java.util.*;

public class NamespacedRegistry<V> extends Registry<NamespacedKey, V> {
    public NamespacedRegistry(Map<NamespacedKey, V> initialObjects, Normalizer<NamespacedKey> keyNormalizer, Register<V, NamespacedKey> registerFunction) {
        super(initialObjects, keyNormalizer, registerFunction);
    }

    public NamespacedRegistry() {
    }

    public NamespacedRegistry(Map<NamespacedKey, V> initialObjects) {
        super(initialObjects);
    }

    public NamespacedRegistry(Map<NamespacedKey, V> initialObjects, Normalizer<NamespacedKey> normalizer) {
        super(initialObjects, normalizer);
    }

    public NamespacedRegistry(Map<NamespacedKey, V> initialObjects, Register<V, NamespacedKey> register) {
        super(initialObjects, register);
    }

    public NamespacedRegistry(Normalizer<NamespacedKey> keyNormalizer, Register<V, NamespacedKey> registerFunction) {
        super(keyNormalizer, registerFunction);
    }

    public NamespacedRegistry(Normalizer<NamespacedKey> keyNormalizer) {
        super(keyNormalizer);
    }

    public NamespacedRegistry(Register<V, NamespacedKey> registerFunction) {
        super(registerFunction);
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