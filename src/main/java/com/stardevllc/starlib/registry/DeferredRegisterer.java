package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.objects.key.Keyable;

import java.util.*;
import java.util.function.Supplier;

public class DeferredRegisterer<V> {
    
    public static <V> DeferredRegisterer<V> create(IRegistry<V> registry) {
        return new DeferredRegisterer<>(registry);
    }
    
    private final IRegistry<V> registry;
    
    private final Map<Key, Supplier<V>> suppliers = new LinkedHashMap<>();
    private final Map<Key, RegistryObject<V>> entries = new HashMap<>();
    
    private boolean hasRegisteredEntries;
    
    public DeferredRegisterer(IRegistry<V> registry) {
        this.registry = registry;
    }
    
    public RegistryObject<V> register(String key, Supplier<V> supplier) {
        return register(createKey(key), supplier);
    }
    
    public RegistryObject<V> register(Key key, Supplier<V> supplier) {
        if (entries.containsKey(key)) {
            return entries.get(key);
        }
        
        RegistryObject<V> registryObject = new RegistryObject<>(this.registry, key);
        this.entries.put(key, registryObject);
        this.suppliers.put(key, supplier);
        return registryObject;
    }
    
    protected Key createKey(String key) {
        return registry.createKey(key);
    }
    
    public void registerEntries() {
        if (hasRegisteredEntries) {
            return;
        }
        this.suppliers.forEach((key, supplier) -> {
            V value = supplier.get();
            registry.register(key, value);
            if (value instanceof Keyable keyable && !keyable.hasKey() && keyable.supportsSettingKey()) {
                keyable.setKey(key);
            }
        });
        this.hasRegisteredEntries = true;
    }
    
    public Collection<RegistryObject<V>> getEntries() {
        return new ArrayList<>(this.entries.values());
    }
}