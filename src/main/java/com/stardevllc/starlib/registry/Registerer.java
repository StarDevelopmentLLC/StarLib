package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.objects.key.Keyable;
import com.stardevllc.starlib.registry.IRegistry.RegisterResult;

import java.util.*;

public class Registerer<V> {
    
    public static <V> Registerer<V> create(IRegistry<V> registry) {
        return new Registerer<>(registry);
    }
    
    private final IRegistry<V> registry;
    private final Map<Key, RegistryObject<V>> entries = new HashMap<>();
    private final Map<Key, RegisterResult<V>> results = new HashMap<>();
    
    public Registerer(IRegistry<V> registry) {
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
        RegisterResult<V> result = registry.register(key, object);
        results.put(key, result);
        
        if (object instanceof Keyable keyable && !keyable.hasKey() && keyable.supportsSettingKey()) {
            keyable.setKey(key);
        }
        
        return registryObject;
    }
    
    protected Key createKey(String key) {
        return registry.createKey(key);
    }
    
    public Collection<RegistryObject<V>> getEntries() {
        return new ArrayList<>(this.entries.values());
    }
    
    public RegisterResult<V> getResult(Key key) {
        return this.results.get(key);
    }
    
    public Map<Key, RegisterResult<V>> getResults() {
        return new HashMap<>(this.results);
    }
}