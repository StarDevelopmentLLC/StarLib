package com.stardevllc.starlib.repository;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.key.Key;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class HashRepository<K, V> extends AbstractRepository<K, V> {
    public HashRepository(Class<K> keyType, Class<V> valueType, Key key, String name, Function<K, V> loader, EventDispatcher dispatcher, long timeout, TaskSubmitter taskSubmitter) {
        super(keyType, valueType, new HashMap<>(), key, name, loader, dispatcher, timeout, taskSubmitter);
    }
    
    public HashRepository(Class<K> keyType, Class<V> valueType, Key key, String name) {
        super(keyType, valueType, new HashMap<>(), key, name);
    }
    
    public static <K, V> Builder<K, V> newBuilder(Class<K> keyType, Class<V> valueType) {
        return new Builder<>(keyType, valueType);
    }
    
    public static class Builder<K, V> extends AbstractRepository.Builder<K, V, HashRepository<K, V>, Builder<K, V>> {
        protected Builder(Class<K> keyType, Class<V> valueType) {
            super(keyType, valueType);
            this.mapSupplier = HashMap::new;
        }
        
        protected Builder(Builder<K, V> builder) {
            super(builder);
            this.mapSupplier = HashMap::new;
        }
        
        @Override
        public Builder<K, V> withSupplier(Supplier<Map<K, V>> supplier) {
            return self();
        }
        
        @Override
        public HashRepository<K, V> build() {
            preBuild();
            HashRepository<K, V> repository = new HashRepository<>(keyType, valueType, key, name, loader, dispatcher, timeout, taskSubmitter);
            return postBuild(repository);
        }
        
        @Override
        public Builder<K, V> clone() {
            return new Builder<>(this);
        }
    }
 }
