package com.stardevllc.starlib.repository;

import com.stardevllc.starlib.objects.key.Key;

import java.util.HashMap;
import java.util.Map;

public final class Repositories {
    
    private static final Map<Key, IRepository<?, ?>> REPOSITORIES = new HashMap<>();
    
    public static <K, V> IRepository<K, V> getRepository(Key id) {
        return (IRepository<K, V>) REPOSITORIES.get(id);
    }
    
    public static <K, V> void addRepository(IRepository<K, V> repository) {
        if (repository.getKey() != null) {
            REPOSITORIES.put(repository.getKey(), repository);
        }
    }
}