package com.stardevllc.starlib.repository;

import com.stardevllc.starlib.collections.RemoveOnlyArrayList;
import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.Nameable;
import com.stardevllc.starlib.objects.builder.IBuilder;
import com.stardevllc.starlib.objects.key.*;
import com.stardevllc.starlib.time.TimeUnit;
import com.stardevllc.starlib.tuple.pair.ImmutablePair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.*;

/**
 * <p>
 * The goal behind a repository will be like the Guava Cache
 * A way to get cached values of a type based on a key, will probably have a timeout feature like the guava cache
 * But also just a way to store objects using similar design pattern to a {@link com.stardevllc.starlib.registry.IRegistry}
 * </p>
 */
public interface IRepository<K, V> extends Iterable<Map.Entry<K, V>>, Keyable, Nameable {
    
    Class<K> getKeyType();
    
    Class<V> getValueType();
    
    int size();
    
    default boolean isEmpty() {
        return size() == 0;
    }
    
    default boolean isNotEmpty() {
        return size() > 0;
    }
    
    /**
     * The valueLoader allows loading of values automatically either with custom logic or from an external source
     *
     * @return The value loader
     */
    @Nullable Function<K, V> getValueLoader();
    
    default void setValueLoader(@Nullable Function<K, V> fetcher) {
        
    }
    
    default long getTimeout() {
        return 0;
    }
    
    default void setTimeout(long milliseconds) {
        
    }
    
    default void setTimeout(long time, TimeUnit unit) {
        setTimeout(unit.toMillis(time));
    }
    
    default void setTimeout(long time, java.util.concurrent.TimeUnit unit) {
        setTimeout(unit.toMillis(time));
    }
    
    boolean containsKey(K key);
    
    boolean containsValue(V value);
    
    @Nullable V get(K key);
    
    @Nullable V get(K key, Callable<V> callable) throws Exception;
    
    default CompletableFuture<V> getAsync(K key) {
        if (getTaskSubmitter() == null) {
            throw new IllegalStateException("Cannot submit an async task to a null task submitter");
        }
        
        CompletableFuture<V> future = new CompletableFuture<>();
        getTaskSubmitter().submit(() -> future.complete(get(key)));
        return future;
    }
    
    final class PutEvent<K, V> extends KeyedEvent<K, V> {
        
        private final V currentValue, newValue;
        
        public PutEvent(IRepository<?, ?> repository, K key, V currentValue, V newValue) {
            super(repository, key);
            this.currentValue = currentValue;
            this.newValue = newValue;
        }
        
        public V getCurrentValue() {
            return currentValue;
        }
        
        public V getNewValue() {
            return newValue;
        }
    }
    
    @FunctionalInterface
    interface PutListener<K, V> extends Listener<PutEvent<K, V>> {
    }
    
    default void addPutListener(PutListener<K, V> listener) {
        addListener(listener);
    }
    
    @Nullable V put(K key, V value);
    
    final class RemoveEvent<K, V> extends KeyedEvent<K, V> {
        private final V currentValue;
        
        public RemoveEvent(IRepository<?, ?> repository, K key, V currentValue) {
            super(repository, key);
            this.currentValue = currentValue;
        }
        
        public V getCurrentValue() {
            return currentValue;
        }
    }
    
    @FunctionalInterface
    interface RemoveListener<K, V> extends Listener<RemoveEvent<K, V>> {
    }
    
    default void addRemoveListener(RemoveListener<K, V> listener) {
        addListener(listener);
    }
    
    V remove(K key);
    
    final class PutAllEvent<K, V> extends AbstractEvent {
        private final List<ImmutablePair<K, V>> entries;
        
        public PutAllEvent(IRepository<K, V> repository, Map<? extends K, ? extends V> m) {
            super(repository);
            List<ImmutablePair<K, V>> list = new ArrayList<>();
            m.forEach((k, v) -> list.add(ImmutablePair.of(k, v)));
            entries = new RemoveOnlyArrayList<>(list);
        }
        
        public List<ImmutablePair<K, V>> getEntries() {
            return entries;
        }
    }
    
    @FunctionalInterface
    interface PutAllListener<K, V> extends Listener<PutAllEvent<K, V>> {}
    
    default void addPutAllListener(PutAllListener<K, V> listener) {
        addListener(listener);
    }
    
    default void putAll(@NotNull Map<? extends K, ? extends V> m) {
        PutAllEvent<K, V> event = getDispatcher().dispatch(new PutAllEvent<>(this, m));
        event.getEntries().forEach(e -> put(e.getLeft(), e.getRight()));
    }
    
    final class ClearEvent<K, V> extends AbstractEvent {
        private final List<ImmutablePair<K, V>> entries;
        
        public ClearEvent(IRepository<K, V> repository) {
            super(repository);
            List<ImmutablePair<K, V>> list = new ArrayList<>();
            repository.forEach((k, v) -> list.add(ImmutablePair.of(k, v)));
            entries = new RemoveOnlyArrayList<>(list);
        }
        
        public List<ImmutablePair<K, V>> getEntries() {
            return entries;
        }
    }
    
    @FunctionalInterface
    interface ClearListener<K, V> extends Listener<ClearEvent<K, V>> {
    }
    
    default void addClearListener(ClearListener<K, V> listener) {
        addListener(listener);
    }
    
    void clear();
    
    @NotNull Set<K> keySet();
    
    @NotNull Collection<V> values();
    
    @NotNull Set<Map.Entry<K, V>> entrySet();
    
    default V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        if (value == null) {
            return defaultValue;
        }
        
        return value;
    }
    
    default void forEach(BiConsumer<? super K, ? super V> action) {
        for (Map.Entry<K, V> entry : entrySet()) {
            action.accept(entry.getKey(), entry.getValue());
        }
    }
    
    @Nullable
    default V putIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            return put(key, value);
        }
        
        return null;
    }
    
    default V computeIfAbsent(K key, Function<K, ? extends V> mappingFunction) {
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }
        
        return v;
    }
    
    default boolean replace(K key, V oldValue, V newValue) {
        if (get(key).equals(oldValue)) {
            return put(key, newValue) != null;
        }
        
        return false;
    }
    
    @Nullable
    default V replace(K key, V value) {
        if (containsKey(key)) {
            return put(key, value);
        }
        return null;
    }
    
    /**
     * This is the parent interfaces for all events fired by this repository
     */
    @FunctionalInterface
    interface Event {
        @NotNull IRepository<?, ?> getRepository();
        
        default boolean isCancelled() {
            return false;
        }
        
        default void setCancelled(boolean cancelled) {
            throw new UnsupportedOperationException("Event does not support cancellation");
        }
    }
    
    abstract class AbstractEvent implements Event {
        protected final IRepository<?, ?> repository;
        protected boolean cancelled;
        
        public AbstractEvent(IRepository<?, ?> repository) {
            this.repository = repository;
        }
        
        @Override
        public IRepository<?, ?> getRepository() {
            return repository;
        }
        
        @Override
        public boolean isCancelled() {
            return cancelled;
        }
        
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
    
    abstract class KeyedEvent<K, V> extends AbstractEvent {
        private final K key;
        
        public KeyedEvent(IRepository<?, ?> repository, K key) {
            super(repository);
            this.key = key;
        }
        
        public K getKey() {
            return key;
        }
    }
    
    @FunctionalInterface
    interface Listener<E extends Event> {
        void onEvent(E event);
    }
    
    default <E extends Event> void addListener(@NotNull IRepository.Listener<E> listener) {
        getDispatcher().addListener(listener);
    }
    
    @NotNull EventDispatcher getDispatcher();
    
    default void setDispatcher(@NotNull EventDispatcher dispatcher) {
        
    }
    
    default @Nullable TaskSubmitter getTaskSubmitter() {
        return null;
    }
    
    default void setTaskSubmitter(@Nullable TaskSubmitter taskSubmitter) {
        
    }
    
    @FunctionalInterface
    interface TaskSubmitter {
        void submit(Runnable runnable);
    }
    
    abstract class Builder<K, V, R extends IRepository<K, V>, B extends Builder<K, V, R, B>> implements IBuilder<R, B> {
        
        protected final Class<K> keyType;
        protected final Class<V> valueType;
        protected Key key;
        protected String name;
        protected Supplier<Map<K, V>> mapSupplier;
        protected Function<K, V> loader;
        protected EventDispatcher dispatcher;
        protected long timeout;
        protected boolean global;
        protected TaskSubmitter taskSubmitter;
        protected final Map<K, V> initialValues = new HashMap<>();
        protected final List<Listener<? extends Event>> listeners = new ArrayList<>();
        
        public Builder(Class<K> keyType, Class<V> valueType) {
            this.keyType = keyType;
            this.valueType = valueType;
        }
        
        public Builder(B builder) {
            this.keyType = builder.keyType;
            this.valueType = builder.valueType;
            this.key = builder.key;
            this.name = builder.name;
            this.mapSupplier = builder.mapSupplier;
            this.loader = builder.loader;
            this.timeout = builder.timeout;
            this.global = builder.global;
            this.taskSubmitter = builder.taskSubmitter;
            this.initialValues.putAll(builder.initialValues);
            this.listeners.addAll(builder.listeners);
        }
        
        public B withKey(Key key) {
            this.key = key;
            return self();
        }
        
        public B withName(String name) {
            this.name = name;
            return self();
        }
        
        public B withSupplier(Supplier<Map<K, V>> supplier) {
            this.mapSupplier = supplier;
            return self();
        }
        
        public B withLoader(Function<K, V> loader) {
            this.loader = loader;
            return self();
        }
        
        public B withTimeout(long timeout) {
            this.timeout = timeout;
            return self();
        }
        
        public B withSubmitter(TaskSubmitter submitter) {
            this.taskSubmitter = submitter;
            return self();
        }
        
        public B put(K key, V value) {
            this.initialValues.put(key, value);
            return self();
        }
        
        public B addListener(Listener<? extends Event> listener) {
            this.listeners.add(listener);
            return self();
        }
        
        public B asGlobal() {
            this.global = true;
            return self();
        }
        
        public B asNotGlobal() {
            this.global = false;
            return self();
        }
        
        @SuppressWarnings("DuplicatedCode")
        protected final B preBuild() {
            if (key == null && name != null) {
                this.key = Keys.of(name);
            } else if (key != null && name == null) {
                this.name = this.key.toString();
            }
            
            if (mapSupplier == null) {
                throw new IllegalStateException("Map Supplier cannot be null");
            }
            
            Map<K, V> backingMap = mapSupplier.get();
            if (backingMap == null) {
                throw new IllegalStateException("Map Supplier cannot return a null map");
            }
            return self();
        }
        
        protected final R postBuild(R repository) {
            this.initialValues.forEach(repository::put);
            this.listeners.forEach(repository::addListener);
            if (this.global) {
                if (key != null && key.isNotEmpty()) {
                    Repositories.addRepository(repository);
                }
            }
            return repository;
        }
        
        @Override
        public abstract B clone();
    }
}