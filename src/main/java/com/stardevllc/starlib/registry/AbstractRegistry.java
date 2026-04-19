package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.objects.key.Keys;
import com.stardevllc.starlib.tuple.pair.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public abstract class AbstractRegistry<V> implements IRegistry<V> {
    
    private final Class<V> valueType;
    private final Key key;
    private final String name;
    private final Map<Key, V> backingMap;
    private final SequencedMap<Key, Key> parentRegistryKeys = new LinkedHashMap<>();
    private final SequencedMap<Key, Key> reversedParentRegistryKeys = parentRegistryKeys.reversed();
    private final IRegistry<? super V> parentRegistry;
    
    private boolean frozen;
    private EventDispatcher dispatcher;
    
    private final Set<Flag> flags;
    
    public AbstractRegistry(Class<V> valueType, Key key, String name, Map<? extends Key, V> backingMap, IRegistry<? super V> parentRegistry, boolean frozen, EventDispatcher dispatcher, Set<Flag> flags) {
        this.valueType = valueType;
        this.key = key;
        this.name = name;
        this.backingMap = (Map<Key, V>) backingMap;
        this.parentRegistry = parentRegistry;
        if (this.parentRegistry != null) {
            this.parentRegistry.addRemoveListener(e -> {
                Key k = reversedParentRegistryKeys.get(e.key());
                if (k != null) {
                    remove(k);
                    parentRegistryKeys.remove(k);
                }
            });
        }
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
    
    public AbstractRegistry(Class<V> valueType, Key key, String name, Map<? extends Key, V> backingMap, Flag[] flags) {
        this(valueType, key, name, backingMap, null, false, null, ofFlagSet(flags));
    }
    
    public AbstractRegistry(Class<V> valueType, Key key, String name, Map<? extends Key, V> backingMap, IRegistry<? super V> parentRegistry, Flag[] flags) {
        this(valueType, key, name, backingMap, parentRegistry, false, null, ofFlagSet(flags));
    }
    
    private static Set<Flag> ofFlagSet(Flag... flags) {
        if (flags == null) {
            return Set.of();
        } else {
            return Set.of(flags);
        }
    }
    
    public AbstractRegistry(Class<V> valueType, Map<? extends Key, V> backingMap) {
        this(valueType, null, null, backingMap, null);
    }
    
    public AbstractRegistry(Class<V> valueType, Key key, Map<? extends Key, V> backingMap) {
        this(valueType, key, key.toString(), backingMap, null);
    }
    
    public AbstractRegistry(Class<V> valueType, String name, Map<? extends Key, V> backingMap) {
        this(valueType, Keys.of(name), name, backingMap, null);
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
    public final @NotNull Key getKey() {
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
    public final boolean containsKey(Object key) {
        if (key instanceof Key k) {
            return this.backingMap.containsKey(k);
        }
        
        return this.backingMap.containsKey(Keys.of(key));
    }
    
    @Override
    public final boolean containsValue(V value) {
        return this.backingMap.containsValue(value);
    }
    
    @Override
    public V get(Key key) {
        V v = this.backingMap.get(key);
        if (v != null || !hasFlag(Flag.CHECK_PARTIAL_IN_GET)) {
            return v;
        }
        
        List<Key> matches = getPartial(key);
        
        if (matches.size() == 1) {
            return get(matches.getFirst());
        }
        
        return null;
    }
    
    @Override
    public List<Key> getPartial(Key key) {
        List<Key> matches = new ArrayList<>();
        for (Key k : this.backingMap.keySet()) {
            if (k.contains(key)) {
                matches.add(k);
            }
        }
        return matches;
    }
    
    @Override
    public final Collection<Key> get(V value) {
        List<Key> keys = new ArrayList<>();
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
    
    protected final RegisterResult<V> registerBacking(Key key, V value) {
        if (this.frozen) {
            return RegisterResult.ofFrozenFailure(key, value);
        }
        
        V oldValue = get(key);
        
        if (oldValue != null) {
            if (!hasFlag(Flag.REPLACING)) {
                return RegisterResult.ofReplacementFailure(key, value);
            }
        }
        
        RegisterEvent<V> e = getDispatcher().dispatch(new RegisterEvent<>(this, key, value, oldValue));
        if (e.isCancelled()) {
            return RegisterResult.ofCancelledFailure(key, value);
        }
        
        V ov = this.backingMap.put(key, value);
        callAdditionalRegisterActions(key, value, ov);
        
        RegisterResult<? super V> parentResult;
        if (this.parentRegistry != null) {
            Key fqKey;
            if (this.hasKey() && hasFlag(Flag.APPEND_KEY_TO_OBJECT_TO_PARENT)) {
                fqKey = Keys.of(this.key, "/", key);
                this.parentRegistryKeys.put(key, fqKey);
            } else {
                fqKey = key;
            }
            parentResult = this.parentRegistry.register(fqKey, value);
            if (!parentResult.success() && hasFlag(Flag.FAIL_ON_PARENT_REGISTER_FAILURE)) {
                return RegisterResult.ofParentFailure(key, value, parentResult);
            }
        } else {
            parentResult = null;
        }
        
        return RegisterResult.ofSuccess(key, value, ov, parentResult);
    }
    
    @Override
    public RegisterResult<V> register(Key key, V value) {
        return registerBacking(key, value);
    }
    
    /**
     * This method allows you to perform additional actions when a value is registered. <br>
     * This is called after all checks and after the value is put into the backing map
     *
     * @param key      The key
     * @param value    The new value
     * @param oldValue The old value
     */
    protected void callAdditionalRegisterActions(Key key, V value, V oldValue) {
    }
    
    protected final V removeBacking(Key key) {
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
            Key k = this.parentRegistryKeys.get(key);
            if (k != null) {
                this.parentRegistry.remove(k);
            } else {
                this.parentRegistry.remove(key);
            }
        }
        return v;
    }
    
    @Override
    public V remove(Key key) {
        return removeBacking(key);
    }
    
    /**
     * This is called after the value is removed from the internal map
     *
     * @param key   The key
     * @param value The value that was previously associated with the key
     */
    protected void callAdditionalRemoveActions(Key key, V value) {
    }
    
    protected final void clearBacking() {
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
        
        for (ImmutablePair<Key, V> pair : e.getValues()) {
            this.backingMap.remove(pair.getLeft());
        }
    }
    
    @Override
    public void clear() {
        clearBacking();
    }
    
    @Override
    public final Set<Key> keySet() {
        return new KeySet();
    }
    
    @Override
    public Set<Map.Entry<Key, V>> entrySet() {
        return Set.of();
    }
    
    protected class EntryItr implements Iterator<Map.Entry<Key, V>> {
        
        private final Iterator<Map.Entry<Key, V>> backingIterator;
        
        private Map.Entry<Key, V> current;
        
        public EntryItr() {
            this.backingIterator = backingMap.entrySet().iterator();
        }
        
        @Override
        public boolean hasNext() {
            return this.backingIterator.hasNext();
        }
        
        @Override
        public Map.Entry<Key, V> next() {
            return current = this.backingIterator.next();
        }
        
        @Override
        public void remove() {
            if (frozen) {
                throw new UnsupportedOperationException("Cannot remove a key from a frozen registry");
            }
            
            RemoveEvent<V> e = getDispatcher().dispatch(new RemoveEvent<>(AbstractRegistry.this, current.getKey(), current.getValue()));
            if (e.isCancelled()) {
                return;
            }
            
            this.backingIterator.remove();
            callAdditionalRemoveActions(current.getKey(), current.getValue());
            IRegistry<? super V> pr = AbstractRegistry.this.parentRegistry;
            if (pr != null) {
                pr.remove(current.getKey());
            }
        }
    }
    
    protected class EntrySet extends AbstractSet<Map.Entry<Key, V>> {
        
        @Override
        public Iterator<Map.Entry<Key, V>> iterator() {
            return null;
        }
        
        @Override
        public int size() {
            return AbstractRegistry.this.size();
        }
    }
    
    @SuppressWarnings("DuplicatedCode")
    protected class KeyItr implements Iterator<Key> {
        
        protected final Iterator<Key> backingIterator;
        
        protected Key current;
        
        public KeyItr() {
            backingIterator = backingMap.keySet().iterator();
        }
        
        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }
        
        @Override
        public Key next() {
            return current = backingIterator.next();
        }
        
        @Override
        public void remove() {
            if (frozen) {
                throw new UnsupportedOperationException("Cannot remove a key from a frozen registry");
            }
            
            V value = get(current);
            
            RemoveEvent<V> e = getDispatcher().dispatch(new RemoveEvent<>(AbstractRegistry.this, key, value));
            if (e.isCancelled()) {
                return;
            }
            
            this.backingIterator.remove();
            callAdditionalRemoveActions(key, value);
            IRegistry<? super V> pr = AbstractRegistry.this.parentRegistry;
            if (pr != null) {
                pr.remove(current);
            }
        }
    }
    
    protected class KeySet extends AbstractSet<Key> {
        @Override
        public Iterator<Key> iterator() {
            return new KeyItr();
        }
        
        @Override
        public int size() {
            return AbstractRegistry.this.size();
        }
    }
    
    @Override
    public final Collection<V> values() {
        return new Values();
    }
    
    @SuppressWarnings("DuplicatedCode")
    protected class ValuesItr implements Iterator<V> {
        
        protected final Iterator<V> backingIterator;
        
        protected V current;
        
        public ValuesItr() {
            this.backingIterator = backingMap.values().iterator();
        }
        
        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }
        
        @Override
        public V next() {
            return current = backingIterator.next();
        }
        
        @Override
        public void remove() {
            if (frozen) {
                throw new UnsupportedOperationException("Cannot remove a value from a frozen registry");
            }
            
            Collection<Key> keys = get(current);
            
            if (keys.size() != 1) {
                return;
            }
            
            Key key = keys.iterator().next();
            
            RemoveEvent<V> e = getDispatcher().dispatch(new RemoveEvent<>(AbstractRegistry.this, key, current));
            if (e.isCancelled()) {
                return;
            }
            
            this.backingIterator.remove();
            callAdditionalRemoveActions(key, current);
            IRegistry<? super V> pr = AbstractRegistry.this.parentRegistry;
            if (pr != null) {
                pr.remove(key);
            }
        }
    }
    
    protected class Values extends AbstractCollection<V> {
        
        @Override
        public Iterator<V> iterator() {
            return new ValuesItr();
        }
        
        @Override
        public int size() {
            return AbstractRegistry.this.size();
        }
    }
    
    @Override
    public final void forEach(BiConsumer<Key, ? super V> action) {
        if (action != null) {
            this.backingMap.forEach(action);
        }
    }
    
    @Override
    public final Iterator<V> iterator() {
        return new ValuesItr();
    }
    
    private static class Dispatcher<V> implements EventDispatcher {
        
        private final List<Listener<V, Event<V>>> listeners = new ArrayList<>();
        
        @Override
        public @NotNull <E> E dispatch(E event) {
            this.listeners.forEach(l -> {
                try {
                    l.onEvent((Event<V>) event);
                } catch (ClassCastException e) {}
            });
            return event;
        }
        
        @Override
        public void addListener(Object listener) {
            if (listener instanceof IRegistry.Listener<?, ?> l) {
                listeners.add((Listener<V, Event<V>>) l);
            }
        }
    }
    
    public abstract static class Builder<V, R extends AbstractRegistry<V>, B extends Builder<V, R, B>> extends IRegistry.Builder<V, R, B> {
        protected Builder(Class<V> valueType) {
            super(valueType);
        }
        
        public Builder(B builder) {
            super(builder);
        }
    }
}
