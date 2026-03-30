package com.stardevllc.starlib.repository;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.tuple.pair.ImmutablePair;
import com.stardevllc.starlib.values.mutable.MutableLong;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public abstract class AbstractRepository<K, V> implements IRepository<K, V> {
    
    private final Class<K> keyType;
    private final Class<V> valueType;
    
    private final Key id;
    private final String name;
    
    private final Map<K, V> backingMap;
    
    private Function<K, V> fetcher;
    private EventDispatcher dispatcher;
    
    private long timeout;
    
    private final Map<K, MutableLong> accessMap = new HashMap<>();
    
    private TaskSubmitter taskSubmitter;
    
    public AbstractRepository(Class<K> keyType, Class<V> valueType, Map<K, V> backingMap, Key id, String name, Function<K, V> fetcher, EventDispatcher dispatcher, long timeout, TaskSubmitter taskSubmitter) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.backingMap = backingMap;
        this.id = id;
        this.name = name;
        this.fetcher = fetcher;
        if (dispatcher != null) {
            this.dispatcher = dispatcher;
        } else {
            this.dispatcher = new Dispatcher();
        }
        this.timeout = timeout;
        this.taskSubmitter = taskSubmitter;
    }
    
    protected AbstractRepository(Class<K> keyType, Class<V> valueType, @NotNull Map<K, V> backingMap, Key id, String name) {
        this(keyType, valueType, backingMap, id, name, null, null, 0, null);
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
        return id;
    }
    
    @Override
    public final void setTaskSubmitter(TaskSubmitter taskSubmitter) {
        this.taskSubmitter = taskSubmitter;
    }
    
    @Override
    public final void setValueFetcher(@Nullable Function<K, V> fetcher) {
        this.fetcher = fetcher;
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
    public final @Nullable Function<K, V> getValueFetcher() {
        return this.fetcher;
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
    public final @Nullable V get(K key) {
        V value = this.backingMap.get(key);
        MutableLong lastAccess = this.accessMap.computeIfAbsent(key, k -> new MutableLong());
        
        if (timeout > 0 && lastAccess.get() + timeout < System.currentTimeMillis()) {
            value = null;
        }
        
        if (value == null) {
            if (this.fetcher != null) {
                value = this.fetcher.apply(key);
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
}