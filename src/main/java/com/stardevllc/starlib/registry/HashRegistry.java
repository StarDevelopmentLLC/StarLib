package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.key.Key;

import java.util.*;
import java.util.function.Supplier;

public class HashRegistry<V> extends AbstractRegistry<V> {
    public HashRegistry(Class<V> valueType, Key id, String name, IRegistry<? super V> parentRegistry, boolean frozen, EventDispatcher dispatcher, Set<Flag> flags) {
        super(valueType, id, name, new HashMap<>(), parentRegistry, frozen, dispatcher, flags);
    }
    
    public HashRegistry(Class<V> valueType, Key id, String name, Flag[] flags) {
        super(valueType, id, name, new HashMap<>(), flags);
    }
    
    public HashRegistry(Class<V> valueType, Key id, String name, IRegistry<? super V> parentRegistry, Flag[] flags) {
        super(valueType, id, name, new HashMap<>(), parentRegistry, flags);
    }
    
    public HashRegistry(Class<V> valueType) {
        super(valueType, new HashMap<>());
    }
    
    public HashRegistry(Class<V> valueType, Key id) {
        super(valueType, id, new HashMap<>());
    }
    
    public HashRegistry(Class<V> valueType, String name) {
        super(valueType, name, new HashMap<>());
    }
    
    @Deprecated(forRemoval = true)
    public static <V> Builder<V> builder(Class<V> valueType) {
        return new Builder<>(valueType);
    }
    
    public static <V> Builder<V> newBuilder(Class<V> valueType) {
        return new Builder<>(valueType);
    }
    
    public static class Builder<V> extends AbstractRegistry.Builder<V, HashRegistry<V>, Builder<V>> {
        protected Builder(Class<V> valueType) {
            super(valueType);
            mapSupplier = HashMap::new;
        }
        
        protected Builder(Builder<V> builder) {
            super(builder);
        }
        
        @Override
        public Builder<V> withSupplier(Supplier<Map<Key, V>> supplier) {
            return self();
        }
        
        @Override
        public HashRegistry<V> build() {
            this.prebuild();
            HashRegistry<V> registry = new HashRegistry<>(valueType, key, name, parentRegistry, false, dispatcher, flags);
            return this.postBuild(registry);
        }
        
        @Override
        public Builder<V> clone() {
            return new Builder<>(this);
        }
    }
}
