package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.objects.key.Key;

import java.util.HashMap;
import java.util.Map;

@Deprecated(since = "0.25.0", forRemoval = true)
public final class Registries {
    
    private static final Map<Key, IRegistry<?>> REGISTRIES = new HashMap<>();
    
    public static <V> IRegistry<V> getRegistry(Key key) {
        if (REGISTRIES.containsKey(key)) {
            return (IRegistry<V>) REGISTRIES.get(key);
        }
        
        return null;
    }
    
    public static void addRegistry(IRegistry<?> registry) {
        if (registry.hasKey()) {
            REGISTRIES.put(registry.getKey(), registry);
        }
    }
}