package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public abstract class AbstractRegistry<V> implements IRegistry<V> {
    
    private final RegistryKey key;
    private final String name;
    
    private boolean frozen;
    private EventDispatcher<?> dispatcher;
    
    private final Set<Flag> flags = EnumSet.noneOf(Flag.class);
    protected final Map<RegistryKey, V> backingMap;
    
    public AbstractRegistry(RegistryKey key, String name, Map<RegistryKey, V> backingMap, Flag... flags) {
        this.key = key;
        this.name = name;
        this.backingMap = backingMap;
        if (flags != null) {
            this.flags.addAll(List.of(flags));
        }
    }
    
    public AbstractRegistry(Map<RegistryKey, V> backingMap) {
        this(null, null, backingMap);
    }
    
    public AbstractRegistry(RegistryKey key, Map<RegistryKey, V> backingMap) {
        this(key, key.toString(), backingMap);
    }
    
    public AbstractRegistry(String name, Map<RegistryKey, V> backingMap) {
        this.key = createKey(name);
        this.name = name;
        this.backingMap = backingMap;
    }
    
    @Override
    public final @NotNull RegistryKey getKey() {
        if (this.key == null) {
            return IRegistry.super.getKey();
        }
        return this.key;
    }
    
    @Override
    public final @NotNull String getName() {
        if (this.name == null) {
            return IRegistry.super.getName();
        }
        return this.name;
    }
    
    @Override
    public void setDispatcher(EventDispatcher<?> dispatcher) {
        this.dispatcher = dispatcher;
    }
    
    @Override
    public @NotNull <E extends Event> EventDispatcher<E> getDispatcher() {
        return (EventDispatcher<E>) (dispatcher != null ? dispatcher : (EventDispatcher<E>) EventDispatcher.NOOP);
    }
    
    @Override
    public <E extends Event> void addListener(RegistryListener<E> listener) {
        getDispatcher().addListener(listener);
    }
    
    @Override
    public final @NotNull Set<Flag> getFlags() {
        return flags;
    }
    
    @Override
    public final int size() {
        return this.backingMap.size();
    }
    
    @Override
    public final boolean isFrozen() {
        return frozen;
    }
    
    @Override
    public final FreezeResult freeze() {
        if (!supportsFreezing()) {
            return FreezeResult.UNSUPPORTED;
        }
        
        if (this.frozen) {
            return FreezeResult.ALREADY_FROZEN;
        }
        
        FreezeEvent e = getDispatcher().dispatch(new FreezeEvent(this));
        if (e.isCancelled()) {
            return FreezeResult.EVENT_CANCELLED;
        }
        
        this.frozen = true;
        return FreezeResult.SUCCESS;
    }
    
    @Override
    public final UnfreezeResult unfreeze() {
        if (!supportsUnfreezing()) {
            return UnfreezeResult.UNSUPPORTED;
        }
        
        if (!this.frozen) {
            return UnfreezeResult.NOT_FROZEN;
        }
        
        UnfreezeEvent e = getDispatcher().dispatch(new UnfreezeEvent(this));
        if (e.isCancelled()) {
            return UnfreezeResult.EVENT_CANCELLED;
        }
        
        this.frozen = false;
        return UnfreezeResult.SUCCESS;
    }
    
    @Override
    public final boolean containsKey(RegistryKey key) {
        return this.backingMap.containsKey(key);
    }
    
    @Override
    public final boolean containsValue(V value) {
        return this.backingMap.containsValue(value);
    }
    
    @Override
    public final V get(RegistryKey key) {
        return this.backingMap.get(key);
    }
    
    @Override
    public final Collection<RegistryKey> get(V value) {
        List<RegistryKey> keys = new ArrayList<>();
        this.backingMap.forEach((k, v) -> {
            if (value == null && v == null) {
                keys.add(k);
            } else if (value != null && v != null) {
                if (value.equals(v)) {
                    keys.add(k);
                }
            }
        });
        
        return keys;
    }
    
    @Override
    public final V register(RegistryKey key, V value) {
        if (this.frozen) {
            return null;
        }
        
        V oldValue = get(key);
        
        RegisterEvent<V> e = getDispatcher().dispatch(new RegisterEvent<>(this, key, value, oldValue));
        if (e.isCancelled()) {
            return null;
        }
        
        return this.backingMap.put(key, value);
    }
    
    @Override
    public final V remove(RegistryKey key) {
        if (this.frozen) {
            return null;
        }
        
        V value = get(key);
        
        RemoveEvent<V> e = getDispatcher().dispatch(new RemoveEvent<>(this, key, value));
        if (e.isCancelled()) {
            return null;
        }
        
        return this.backingMap.remove(key);
    }
    
    @Override
    public final void clear() {
        if (this.frozen) {
            return;
        }
        
        ClearEvent<V> e = getDispatcher().dispatch(new ClearEvent<>(this));
        if (e.isCancelled()) {
            return;
        }
        
        for (ImmutablePair<RegistryKey, V> pair : e.getValues()) {
            this.backingMap.remove(pair.getLeft());
        }
    }
    
    @Override
    public final Set<RegistryKey> keySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<RegistryKey> iterator() {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return AbstractRegistry.this.backingMap.keySet().iterator().hasNext();
                    }
                    
                    @Override
                    public RegistryKey next() {
                        return AbstractRegistry.this.backingMap.keySet().iterator().next();
                    }
                };
            }
            
            @Override
            public int size() {
                return AbstractRegistry.this.size();
            }
        };
    }
    
    @Override
    public final Collection<V> values() {
        return new AbstractCollection<>() {
            @Override
            public Iterator<V> iterator() {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return AbstractRegistry.this.backingMap.values().iterator().hasNext();
                    }
                    
                    @Override
                    public V next() {
                        return AbstractRegistry.this.backingMap.values().iterator().next();
                    }
                };
            }
            
            @Override
            public int size() {
                return AbstractRegistry.this.size();
            }
        };
    }
    
    @Override
    public final void forEach(BiConsumer<RegistryKey, ? super V> action) {
        if (action != null) {
            this.backingMap.forEach(action);
        }
    }
    
    @Override
    public final Iterator<V> iterator() {
        return values().iterator();
    }
}
