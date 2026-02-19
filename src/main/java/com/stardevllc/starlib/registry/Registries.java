package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.event.EventDispatcher;

import java.util.*;
import java.util.function.Supplier;

public final class Registries {
    
    private static final Map<RegistryKey, IRegistry<?>> REGISTRIES = new HashMap<>();
    
    public static <V> IRegistry<V> getRegistry(RegistryKey key) {
        if (REGISTRIES.containsKey(key)) {
            return (IRegistry<V>) REGISTRIES.get(key);
        }
        
        return null;
    }
    
    public static class RegistryBuilder<V> {
        private Supplier<Map<RegistryKey, V>> mapSupplier;
        private RegistryKey key;
        private String name;
        private EventDispatcher<?> dispatcher;
        private Set<IRegistry.Flag> flags = EnumSet.noneOf(IRegistry.Flag.class);
        private boolean global;
        
        public RegistryBuilder<V> withSupplier(Supplier<Map<RegistryKey, V>> mapSupplier) {
            this.mapSupplier = mapSupplier;
            return this;
        }
        
        public RegistryBuilder<V> withKey(RegistryKey key) {
            this.key = key;
            return this;
        }
        
        public RegistryBuilder<V> withName(String name) {
            this.name = name;
            return this;
        }
        
        public RegistryBuilder<V> withDispatcher(EventDispatcher<?> dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }
        
        public RegistryBuilder<V> withFlags(IRegistry.Flag... flags) {
            if (flags != null) {
                this.flags.addAll(List.of(flags));
            }
            
            return this;
        }
        
        public RegistryBuilder<V> allowFreezing() {
            this.flags.add(IRegistry.Flag.FREEZING);
            return this;
        }
        
        public RegistryBuilder<V> allowUnfreezing() {
            this.flags.add(IRegistry.Flag.UNFREEZING);
            return this;
        }
        
        public RegistryBuilder<V> asGlobal() {
            this.global = true;
            return this;
        }
        
        public IRegistry<V> build() {
            if (key == null && name != null) {
                this.key = RegistryKey.of(name);
            } else if (key != null && name == null) {
                this.name = this.key.toString();
            }
            
            if (mapSupplier == null) {
                throw new IllegalStateException("Map Supplier cannot be null");
            }
            
            IRegistry<V> registry = new AbstractRegistry<>(key, name, mapSupplier.get(), this.flags.toArray(new IRegistry.Flag[0])) {
                @Override
                public Map<RegistryKey, V> toMapCopy() {
                    Map<RegistryKey, V> map = mapSupplier.get();
                    map.putAll(this.backingMap);
                    return map;
                }
            };
            
            registry.setDispatcher(dispatcher);
            
            if (global) {
                if (key != null && !RegistryKey.EMPTY.equals(key)) {
                    REGISTRIES.put(key, registry);
                }
            }
            
            return registry;
        }
    }
    
    public static <V> RegistryBuilder<V> create(Class<V> type) {
        return new RegistryBuilder<>();
    }
    
    public static <V> RegistryBuilder<V> create(Class<V> type, Supplier<Map<RegistryKey, V>> supplier) {
        return new RegistryBuilder<V>().withSupplier(supplier);
    }
}