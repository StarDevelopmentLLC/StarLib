package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.tuple.pair.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public abstract class AbstractRegistry<V> implements IRegistry<V> {
    
    private final Class<V> valueType;
    private final RegistryKey id;
    private final String name;
    private final Map<RegistryKey, V> backingMap;
    private final IRegistry<? super V> parentRegistry;
    
    private boolean frozen;
    private EventDispatcher dispatcher;
    
    private final Set<Flag> flags;
    
    public AbstractRegistry(Class<V> valueType, RegistryKey id, String name, Map<? extends RegistryKey, V> backingMap, IRegistry<? super V> parentRegistry, boolean frozen, EventDispatcher dispatcher, Set<Flag> flags) {
        this.valueType = valueType;
        this.id = id;
        this.name = name;
        this.backingMap = (Map<RegistryKey, V>) backingMap;
        this.parentRegistry = parentRegistry;
        this.frozen = frozen;
        if (dispatcher != null) {
            this.dispatcher = dispatcher;
        } else {
            this.dispatcher = new Dispatcher<>();
        }
        if (flags != null && !flags.isEmpty()) {
            this.flags = EnumSet.copyOf(flags);
        } else {
            this.flags = EnumSet.noneOf(Flag.class);
        }
    }
    
    public AbstractRegistry(Class<V> valueType, RegistryKey id, String name, Map<? extends RegistryKey, V> backingMap, Flag[] flags) {
        this(valueType, id, name, backingMap, null, false, null, ofFlagSet(flags));
    }
    
    public AbstractRegistry(Class<V> valueType, RegistryKey id, String name, Map<? extends RegistryKey, V> backingMap, IRegistry<? super V> parentRegistry, Flag[] flags) {
        this(valueType, id, name, backingMap, parentRegistry, false, null, ofFlagSet(flags));
    }
    
    private static Set<Flag> ofFlagSet(Flag... flags) {
        if (flags == null) {
            return Set.of();
        } else {
            return Set.of(flags);
        }
    }
    
    public AbstractRegistry(Class<V> valueType, Map<? extends RegistryKey, V> backingMap) {
        this(valueType, null, null, backingMap, null);
    }
    
    public AbstractRegistry(Class<V> valueType, RegistryKey id, Map<? extends RegistryKey, V> backingMap) {
        this(valueType, id, id.toString(), backingMap, null);
    }
    
    public AbstractRegistry(Class<V> valueType, String name, Map<? extends RegistryKey, V> backingMap) {
        this(valueType, RegistryKey.of(name), name, backingMap, null);
    }
    
    @Override
    public IRegistry<? super V> getParentRegistry() {
        return parentRegistry;
    }
    
    @Override
    public final Class<V> getValueType() {
        return valueType;
    }
    
    @Override
    public final @NotNull RegistryKey getId() {
        if (this.id == null) {
            return IRegistry.super.getId();
        }
        return this.id;
    }
    
    @Override
    public final @NotNull String getName() {
        if (this.name == null) {
            return IRegistry.super.getName();
        }
        return this.name;
    }
    
    @Override
    public final void setDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    
    @Override
    public final @NotNull EventDispatcher getDispatcher() {
        return dispatcher != null ? dispatcher : EventDispatcher.NOOP;
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
        
        FreezeEvent<V> e = getDispatcher().dispatch(new FreezeEvent<>(this));
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
        
        UnfreezeEvent<V> e = getDispatcher().dispatch(new UnfreezeEvent<>(this));
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
    public V register(RegistryKey key, V value) {
        if (this.frozen) {
            return null;
        }
        
        V oldValue = get(key);
        
        if (oldValue != null) {
            if (!hasFlag(Flag.REPLACING)) {
                return null;
            }
        }
        
        RegisterEvent<V> e = getDispatcher().dispatch(new RegisterEvent<>(this, key, value, oldValue));
        if (e.isCancelled()) {
            return null;
        }
        
        V ov = this.backingMap.put(key, value);
        callAdditionalRegisterActions(key, value, ov);
        
        if (this.parentRegistry != null) {
            this.parentRegistry.register(key, value);
        }
        
        return ov;
    }
    
    /**
     * This method allows you to perform additional actions when a value is registered. <br>
     * This is called after all checks and after the value is put into the backing map
     *
     * @param key      The key
     * @param value    The new value
     * @param oldValue The old value
     */
    protected void callAdditionalRegisterActions(RegistryKey key, V value, V oldValue) {
    }
    
    @Override
    public V remove(RegistryKey key) {
        if (this.frozen) {
            return null;
        }
        
        V value = get(key);
        
        RemoveEvent<V> e = getDispatcher().dispatch(new RemoveEvent<>(this, key, value));
        if (e.isCancelled()) {
            return null;
        }
        
        V v = this.backingMap.remove(key);
        callAdditionalRemoveActions(key, v);
        if (this.parentRegistry != null) {
            this.parentRegistry.remove(key);
        }
        return v;
    }
    
    /**
     * This is called after the value is removed from the internal map
     *
     * @param key   The key
     * @param value The value that was previously associated with the key
     */
    protected void callAdditionalRemoveActions(RegistryKey key, V value) {
    }
    
    @Override
    public final void clear() {
        if (this.frozen) {
            return;
        }
        
        if (!this.hasFlag(Flag.CLEARING)) {
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
        return Collections.unmodifiableSet(this.backingMap.keySet());
    }
    
    @Override
    public final Collection<V> values() {
        return Collections.unmodifiableCollection(this.backingMap.values());
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
    
    private static class Dispatcher<V> implements EventDispatcher {
        
        private final List<Listener<V, Event<V>>> listeners = new ArrayList<>();
        
        @Override
        public @NotNull <E> E dispatch(E event) {
            this.listeners.forEach(l -> l.onEvent((Event<V>) event));
            return event;
        }
        
        @Override
        public void addListener(Object listener) {
            if (listener instanceof IRegistry.Listener<?, ?> l) {
                listeners.add((Listener<V, Event<V>>) l);
            }
        }
    }
}
