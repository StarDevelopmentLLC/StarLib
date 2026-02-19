package com.stardevllc.starlib.registry;

import java.util.Objects;
import java.util.function.*;
import java.util.stream.Stream;

public class RegistryObject<V> implements Supplier<V> {
    
    private final IRegistry<V> registry;
    private final RegistryKey key;
    
    public RegistryObject(IRegistry<V> registry, RegistryKey key) {
        this.registry = registry;
        this.key = key;
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
    
    public RegistryKey getKey() {
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
    
    public <U> U map(BiFunction<RegistryKey, ? super V, ? extends U> mapper) {
        if (isPresent()) {
            return mapper.apply(key, get());
        }
        
        return null;
    }
    
    public <U> Supplier<U> lazyMap(Function<? super V, ? extends U> mapper) {
        return () -> isPresent() ? mapper.apply(get()) : null;
    }
    
    public <U> Supplier<U> lazyMap(BiFunction<RegistryKey, ? super V, ? extends U> mapper) {
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