package com.stardevllc.starlib.registry;

import java.util.*;

public class Registerer<V> {
    
    public static <V> Registerer<V> create(IRegistry<V> registry) {
        return new Registerer<>(registry);
    }
    
    private final IRegistry<V> registry;
    private final Map<RegistryKey, RegistryObject<V>> entries = new HashMap<>();
    
    private Registerer(IRegistry<V> registry) {
        this.registry = registry;
    }
    
    public RegistryObject<V> register(RegistryKey key, V object) {
        if (entries.containsKey(key)) {
            return entries.get(key);
        }
        
        RegistryObject<V> registryObject = new RegistryObject<>(registry, key);
        entries.put(key, registryObject);
        return registryObject;
    }
    
    public Collection<RegistryObject<V>> getEntries() {
        return new ArrayList<>(this.entries.values());
    }
}