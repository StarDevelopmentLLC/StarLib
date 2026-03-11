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
    
    public static void addRegistry(IRegistry<?> registry) {
        if (registry.hasId()) {
            REGISTRIES.put(registry.getId(), registry);
        }
    }
    
    public static class RegistryBuilder<V> {
        private final Class<V> valueType;
        private Supplier<Map<RegistryKey, V>> mapSupplier;
        private RegistryKey id;
        private String name;
        private EventDispatcher<?> dispatcher;
        private Set<IRegistry.Flag> flags = EnumSet.noneOf(IRegistry.Flag.class);
        private boolean global;
        
        private RegistryBuilder(Class<V> valueType) {
            this.valueType = valueType;
        }
        
        public RegistryBuilder<V> withSupplier(Supplier<Map<RegistryKey, V>> mapSupplier) {
            this.mapSupplier = mapSupplier;
            return this;
        }
        
        public RegistryBuilder<V> withId(RegistryKey key) {
            this.id = key;
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
            if (id == null && name != null) {
                this.id = RegistryKey.of(name);
            } else if (id != null && name == null) {
                this.name = this.id.toString();
            }
            
            if (mapSupplier == null) {
                throw new IllegalStateException("Map Supplier cannot be null");
            }
            
            Map<RegistryKey, V> backingMap = mapSupplier.get();
            if (backingMap == null) {
                throw new IllegalStateException("Map Supplier cannot return a null map");
            }
            
            IRegistry<V> registry = new AbstractRegistry<>(valueType, id, name, backingMap, false, dispatcher, this.flags) {};
            
            if (global) {
                if (id != null && id.isNotEmpty()) {
                    REGISTRIES.put(id, registry);
                }
            }
            
            return registry;
        }
    }
    
    public static <V> RegistryBuilder<V> create(Class<V> type) {
        return new RegistryBuilder<>(type);
    }
    
    public static <V> RegistryBuilder<V> create(Class<V> type, Supplier<Map<RegistryKey, V>> supplier) {
        return new RegistryBuilder<>(type).withSupplier(supplier);
    }
}