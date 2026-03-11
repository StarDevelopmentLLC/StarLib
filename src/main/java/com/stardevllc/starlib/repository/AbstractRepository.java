package com.stardevllc.starlib.repository;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.id.ID;
import com.stardevllc.starlib.tuple.ImmutablePair;
import com.stardevllc.starlib.values.MutableLong;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public abstract class AbstractRepository<K, V> implements IRepository<K, V> {
    
    protected final Class<K> keyType;
    protected final Class<V> valueType;
    
    protected final ID id;
    protected final String name;
    
    protected final Map<K, V> backingMap;
    
    protected Function<K, V> fetcher;
    protected EventDispatcher<Event> dispatcher;
    
    protected long timeout;
    
    protected final Map<K, MutableLong> accessMap = new HashMap<>();
    
    protected TaskSubmitter taskSubmitter;
    
    public AbstractRepository(Class<K> keyType, Class<V> valueType, Map<K, V> backingMap, ID id, String name, Function<K, V> fetcher, EventDispatcher<Event> dispatcher, long timeout, TaskSubmitter taskSubmitter) {
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
    
    protected AbstractRepository(Class<K> keyType, Class<V> valueType, @NotNull Map<K, V> backingMap, ID id, String name) {
        this(keyType, valueType, backingMap, id, name, null, null, 0, null);
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public @Nullable TaskSubmitter getTaskSubmitter() {
        return taskSubmitter;
    }
    
    @Override
    public ID getId() {
        return id;
    }
    
    @Override
    public void setTaskSubmitter(TaskSubmitter taskSubmitter) {
        this.taskSubmitter = taskSubmitter;
    }
    
    @Override
    public void setValueFetcher(@Nullable Function<K, V> fetcher) {
        this.fetcher = fetcher;
    }
    
    @Override
    public long getTimeout() {
        return this.timeout;
    }
    
    @Override
    public void setTimeout(long milliseconds) {
        this.timeout = milliseconds;
    }
    
    @Override
    public <E extends Event> void setDispatcher(@NotNull EventDispatcher<E> dispatcher) {
        this.dispatcher = (EventDispatcher<Event>) dispatcher;
    }
    
    @Override
    public @NotNull Iterator<Map.Entry<K, V>> iterator() {
        return entrySet().iterator();
    }
    
    @Override
    public Class<K> getKeyType() {
        return keyType;
    }
    
    @Override
    public Class<V> getValueType() {
        return valueType;
    }
    
    @Override
    public int size() {
        return this.backingMap.size();
    }
    
    @Override
    public @Nullable Function<K, V> getValueFetcher() {
        return this.fetcher;
    }
    
    @Override
    public boolean containsKey(K key) {
        return this.backingMap.containsKey(key);
    }
    
    @Override
    public boolean containsValue(V value) {
        return this.backingMap.containsValue(value);
    }
    
    @Override
    public final @Nullable V get(K key) {
        V value = this.backingMap.get(key);
        MutableLong lastAccess = this.accessMap.computeIfAbsent(key, k -> new MutableLong());
        
        if (value == null || lastAccess.get() > 0 && lastAccess.get() + timeout < System.currentTimeMillis()) {
            value = null;
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
        }
    }
    
    @Override
    public @NotNull Set<K> keySet() {
        return Collections.unmodifiableSet(backingMap.keySet());
    }
    
    @Override
    public @NotNull Collection<V> values() {
        return Collections.unmodifiableCollection(this.backingMap.values());
    }
    
    @Override
    public @NotNull Set<Map.Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(this.backingMap.entrySet());
    }
    
    @Override
    public @NotNull <E extends Event> EventDispatcher<E> getDispatcher() {
        return (EventDispatcher<E>) this.dispatcher;
    }
    
    protected static class Dispatcher implements EventDispatcher<IRepository.Event> {
        
        private final List<Listener<Event>> listeners = new ArrayList<>();
        
        @Override
        public @NotNull <I extends Event> I dispatch(I event) {
            listeners.forEach(listener -> listener.onEvent(event));
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