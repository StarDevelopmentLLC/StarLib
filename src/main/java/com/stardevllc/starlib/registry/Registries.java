package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.objects.key.Keys;

import java.util.*;
import java.util.function.Supplier;

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
    
    public static class RegistryBuilder<V> {
        private final Class<V> valueType;
        private Supplier<Map<Key, V>> mapSupplier;
        private IRegistry<? super V> parentRegistry;
        private Key id;
        private String name;
        private EventDispatcher dispatcher;
        private Set<IRegistry.Flag> flags = EnumSet.noneOf(IRegistry.Flag.class);
        private boolean global;
        
        private RegistryBuilder(Class<V> valueType) {
            this.valueType = valueType;
        }
        
        public RegistryBuilder<V> withSupplier(Supplier<Map<Key, V>> mapSupplier) {
            this.mapSupplier = mapSupplier;
            return this;
        }
        
        public RegistryBuilder<V> withId(Key key) {
            this.id = key;
            return this;
        }
        
        public RegistryBuilder<V> withName(String name) {
            this.name = name;
            return this;
        }
        
        public RegistryBuilder<V> withDispatcher(EventDispatcher dispatcher) {
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
        
        public RegistryBuilder<V> withParent(IRegistry<? super V> parent) {
            this.parentRegistry = parent;
            return this;
        }
        
        public IRegistry<V> build() {
            if (id == null && name != null) {
                this.id = Keys.of(name);
            } else if (id != null && name == null) {
                this.name = this.id.toString();
            }
            
            if (mapSupplier == null) {
                throw new IllegalStateException("Map Supplier cannot be null");
            }
            
            Map<Key, V> backingMap = mapSupplier.get();
            if (backingMap == null) {
                throw new IllegalStateException("Map Supplier cannot return a null map");
            }
            
            IRegistry<V> registry = new AbstractRegistry<>(valueType, id, name, backingMap, parentRegistry, false, dispatcher, this.flags) {};
            
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
    
    public static <V> RegistryBuilder<V> create(Class<V> type, Supplier<Map<Key, V>> supplier) {
        return new RegistryBuilder<>(type).withSupplier(supplier);
    }
}