package com.stardevllc.starlib.registry;

import java.util.*;
import java.util.function.Supplier;

public final class DeferredRegisterer<V> {
    
    public static <V> DeferredRegisterer<V> create(IRegistry<V> registry) {
        return new DeferredRegisterer<>(registry);
    }
    
    public static <V> DeferredRegisterer<V> clone(IRegistry<V> registry, DeferredRegisterer<V> registerer) {
        return new DeferredRegisterer<>(registry, registerer);
    }
    
    public static <V> DeferredRegisterer<V> clone(DeferredRegisterer<V> registerer) {
        return new DeferredRegisterer<>(registerer.registry, registerer);
    }
    
    private final IRegistry<V> registry;
    
    private final Map<RegistryKey, Supplier<V>> suppliers = new LinkedHashMap<>();
    private final Map<RegistryKey, RegistryObject<V>> entries = new HashMap<>();
    
    private boolean hasRegisteredEntries;
    
    private DeferredRegisterer(IRegistry<V> registry) {
        this.registry = registry;
    }
    
    private DeferredRegisterer(IRegistry<V> registry, DeferredRegisterer<V> registerer) {
        this.registry = registry;
        this.suppliers.putAll(registerer.suppliers);
    }
    
    public RegistryObject<V> register(String key, Supplier<V> supplier) {
        return register(registry.createKey(key), supplier);
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