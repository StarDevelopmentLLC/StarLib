package com.stardevllc.starlib.registry;

import java.util.*;
import java.util.function.Supplier;

public class DeferredRegisterer<V> {
    
    public static <V> DeferredRegisterer<V> create(IRegistry<V> registry) {
        return new DeferredRegisterer<>(registry);
    }
    
    private final IRegistry<V> registry;
    
    private final Map<RegistryKey, Supplier<V>> suppliers = new LinkedHashMap<>();
    private final Map<RegistryKey, RegistryObject<V>> entries = new HashMap<>();
    
    private boolean hasRegisteredEntries;
    
    protected DeferredRegisterer(IRegistry<V> registry) {
        this.registry = registry;
    }
    
    public RegistryObject<V> register(String key, Supplier<V> supplier) {
        return register(createKey(key), supplier);
    }
    
    public RegistryObject<V> register(RegistryKey key, Supplier<V> supplier) {
        if (entries.containsKey(key)) {
            return entries.get(key);
        }
        
        RegistryObject<V> registryObject = new RegistryObject<>(this.registry, key);
        this.entries.put(key, registryObject);
        this.suppliers.put(key, supplier);
        
        return registryObject;
    }
    
    protected RegistryKey createKey(String key) {
        return registry.createKey(key);
    }
    
    public void registerEntries() {
        if (hasRegisteredEntries) {
            return;
        }
        this.suppliers.forEach((key, supplier) -> registry.register(key, supplier.get()));
        this.hasRegisteredEntries = true;
    }
    
    public Collection<RegistryObject<V>> getEntries() {
        return new ArrayList<>(this.entries.values());
    }
}