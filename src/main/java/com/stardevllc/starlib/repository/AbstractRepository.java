package com.stardevllc.starlib.repository;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.tuple.pair.ImmutablePair;
import com.stardevllc.starlib.values.mutable.MutableLong;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;

public abstract class AbstractRepository<K, V> implements IRepository<K, V> {
    
    private final Class<K> keyType;
    private final Class<V> valueType;
    
    private final Key key;
    private final String name;
    
    private final Map<K, V> backingMap;
    
    private Function<K, V> loader;
    private EventDispatcher dispatcher;
    
    private long timeout;
    
    private final Map<K, MutableLong> accessMap = new HashMap<>();
    
    private TaskSubmitter taskSubmitter;
    
    public AbstractRepository(Class<K> keyType, Class<V> valueType, Map<K, V> backingMap, Key key, String name, Function<K, V> loader, EventDispatcher dispatcher, long timeout, TaskSubmitter taskSubmitter) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.backingMap = backingMap;
        this.key = key;
        this.name = name;
        this.loader = loader;
        if (dispatcher != null) {
            this.dispatcher = dispatcher;
        } else {
            this.dispatcher = new Dispatcher();
        }
        this.timeout = timeout;
        this.taskSubmitter = taskSubmitter;
    }
    
    public AbstractRepository(Class<K> keyType, Class<V> valueType, @NotNull Map<K, V> backingMap, Key key, String name) {
        this(keyType, valueType, backingMap, key, name, null, null, 0, null);
    }
    
    @Override
    public final String getName() {
        return name;
    }
    
    @Override
    public final @Nullable TaskSubmitter getTaskSubmitter() {
        return taskSubmitter;
    }
    
    @Override
    public final Key getKey() {
        return key;
    }
    
    @Override
    public final void setTaskSubmitter(TaskSubmitter taskSubmitter) {
        this.taskSubmitter = taskSubmitter;
    }
    
    @Override
    public final void setValueLoader(@Nullable Function<K, V> fetcher) {
        this.loader = fetcher;
    }
    
    @Override
    public final long getTimeout() {
        return this.timeout;
    }
    
    @Override
    public final void setTimeout(long milliseconds) {
        this.timeout = milliseconds;
    }
    
    @Override
    public final void setDispatcher(@NotNull EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    
    @Override
    public final @NotNull Iterator<Map.Entry<K, V>> iterator() {
        return entrySet().iterator();
    }
    
    @Override
    public final Class<K> getKeyType() {
        return keyType;
    }
    
    @Override
    public final Class<V> getValueType() {
        return valueType;
    }
    
    @Override
    public final int size() {
        return this.backingMap.size();
    }
    
    @Override
    public final @Nullable Function<K, V> getValueLoader() {
        return this.loader;
    }
    
    @Override
    public final boolean containsKey(K key) {
        return this.backingMap.containsKey(key);
    }
    
    @Override
    public final boolean containsValue(V value) {
        return this.backingMap.containsValue(value);
    }
    
    @Override
    public @Nullable V get(K key, Callable<V> callable) throws Exception {
        V v = get(key);
        if (v != null) {
            return v;
        }
        
        v = callable.call();
        if (v != null) {
            put(key, v);
            return v;
        }
        
        return null;
    }
    
    @Override
    public final @Nullable V get(K key) {
        V value = this.backingMap.get(key);
        MutableLong lastAccess = this.accessMap.computeIfAbsent(key, k -> new MutableLong());
        
        if (timeout > 0 && lastAccess.get() + timeout < System.currentTimeMillis()) {
            value = null;
        }
        
        if (value == null) {
            if (this.loader != null) {
                value = this.loader.apply(key);
                if (value != null) {
                    this.backingMap.put(key, value);
                }
            }
        }
        
        if (value == null) {
            this.backingMap.remove(key);
            this.accessMap.remove(key);
            return null;
        }
        
        lastAccess.set(System.currentTimeMillis());
        return value;
    }
    
    @Override
    public final @Nullable V put(K key, V value) {
        V existing = this.backingMap.get(key);
        this.accessMap.computeIfAbsent(key, k -> new MutableLong()).set(System.currentTimeMillis());
        
        PutEvent<K, V> event = getDispatcher().dispatch(new PutEvent<>(this, key, existing, value));
        if (event.isCancelled()) {
            return null;
        }
        
        this.backingMap.put(key, value);
        return existing;
    }
    
    @Override
    public final V remove(K key) {
        RemoveEvent<K, @Nullable V> event = getDispatcher().dispatch(new RemoveEvent<>(this, key, get(key)));
        
        if (event.isCancelled()) {
            this.accessMap.computeIfAbsent(key, k -> new MutableLong()).set(System.currentTimeMillis());
            return null;
        }
        
        this.accessMap.remove(key);
        return this.backingMap.remove(key);
    }
    
    @Override
    public final void clear() {
        ClearEvent<K, V> event = getDispatcher().dispatch(new ClearEvent<>(this));
        if (event.isCancelled()) {
            return;
        }
        
        for (ImmutablePair<K, V> entry : event.getEntries()) {
            this.backingMap.remove(entry.getLeft());
            this.accessMap.remove(entry.getLeft());
        }
    }
    
    @Override
    public final @NotNull Set<K> keySet() {
        return Collections.unmodifiableSet(backingMap.keySet());
    }
    
    @Override
    public final @NotNull Collection<V> values() {
        return Collections.unmodifiableCollection(this.backingMap.values());
    }
    
    @Override
    public final @NotNull Set<Map.Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(this.backingMap.entrySet());
    }
    
    @Override
    public final @NotNull EventDispatcher getDispatcher() {
        return this.dispatcher;
    }
    
    private static class Dispatcher implements EventDispatcher {
        
        private final List<Listener<Event>> listeners = new ArrayList<>();
        
        @Override
        public @NotNull <I> I dispatch(I event) {
            listeners.forEach(listener -> listener.onEvent((Event) event));
            return event;
        }
        
        @Override
        public void addListener(Object listener) {
            if (listener instanceof IRepository.Listener<?> l) {
                this.listeners.add((Listener<Event>) l);
            }
        }
    }
    
    public abstract static class Builder<K, V, R extends IRepository<K, V>, B extends IRepository.Builder<K, V, R, B>> extends IRepository.Builder<K, V, R, B> {
        public Builder(Class<K> keyType, Class<V> valueType) {
            super(keyType, valueType);
        }
        
        public Builder(B builder) {
            super(builder);
        }
    }
}