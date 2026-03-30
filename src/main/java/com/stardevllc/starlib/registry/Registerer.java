package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.objects.key.Key;

import java.util.*;

public class Registerer<V> {
    
    public static <V> Registerer<V> create(IRegistry<V> registry) {
        return new Registerer<>(registry);
    }
    
    private final IRegistry<V> registry;
    private final Map<Key, RegistryObject<V>> entries = new HashMap<>();
    
    protected Registerer(IRegistry<V> registry) {
        this.registry = registry;
    }
    
    public RegistryObject<V> register(String key, V object) {
        return register(createKey(key), object);
    }
    
    public RegistryObject<V> register(Key key, V object) {
        if (entries.containsKey(key)) {
            return entries.get(key);
        }
        
        RegistryObject<V> registryObject = new RegistryObject<>(registry, key);
        entries.put(key, registryObject);
        registry.register(key, object);
        return registryObject;
    }
    
    protected Key createKey(String key) {
        return registry.createKey(key);
    }
    
    public Collection<RegistryObject<V>> getEntries() {
        return new ArrayList<>(this.entries.values());
    }
}