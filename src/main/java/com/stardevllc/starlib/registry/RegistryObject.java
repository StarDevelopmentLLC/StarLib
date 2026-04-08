package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.objects.key.Key;

import java.util.Objects;
import java.util.function.*;
import java.util.stream.Stream;

public class RegistryObject<V> implements Supplier<V> {
    
    private final IRegistry<V> registry;
    private final Key key;
    private final Registerer<V> registerer;
    private final DeferredRegisterer<V> deferredRegisterer;
    
    public RegistryObject(IRegistry<V> registry, Key key, Registerer<V> registerer, DeferredRegisterer<V> deferredRegisterer) {
        this.registry = registry;
        this.key = key;
        this.registerer = registerer;
        this.deferredRegisterer = deferredRegisterer;
    }
    
    public RegistryObject(IRegistry<V> registry, Key key) {
        this(registry, key, null, null);
    }
    
    public RegistryObject(IRegistry<V> registry, Key key, Registerer<V> registerer) {
        this(registry, key, registerer, null);
    }
    
    public RegistryObject(IRegistry<V> registry, Key key, DeferredRegisterer<V> deferredRegisterer) {
        this(registry, key, null, deferredRegisterer);
    }
    
    public boolean isPresent() {
        return registry.containsKey(key);
    }
    
    public void ifPresent(Consumer<V> consumer) {
        V v = get();
        if (v != null) {
            consumer.accept(v);
        }
    }
    
    @Override
    public V get() {
        return registry.get(key);
    }
    
    public IRegistry.RegisterResult<V> result() {
        if (this.registerer != null) {
            return this.registerer.getResult(key);
        } else if (this.deferredRegisterer != null) {
            return this.deferredRegisterer.getResult(key);
        }
        
        return null;
    }
    
    public Key getKey() {
        return key;
    }
    
    public Stream<V> stream() {
        return isPresent() ? Stream.of(get()) : Stream.of();
    }
    
    public <U> U map(Function<? super V, ? extends U> mapper) {
        if (isPresent()) {
            return mapper.apply(get());
        }
        
        return null;
    }
    
    public <U> U map(BiFunction<Key, ? super V, ? extends U> mapper) {
        if (isPresent()) {
            return mapper.apply(key, get());
        }
        
        return null;
    }
    
    public <U> Supplier<U> lazyMap(Function<? super V, ? extends U> mapper) {
        return () -> isPresent() ? mapper.apply(get()) : null;
    }
    
    public <U> Supplier<U> lazyMap(BiFunction<Key, ? super V, ? extends U> mapper) {
        return () -> isPresent() ? mapper.apply(key, get()) : null;
    }
    
    public V orElse(V other) {
        return isPresent() ? get() : other;
    }
    
    public V orElseGet(Supplier<? extends V> other) {
        return isPresent() ? get() : other.get();
    }
    
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof RegistryObject<?> that)) {
            return false;
        }
        
        return Objects.equals(key, that.key);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}