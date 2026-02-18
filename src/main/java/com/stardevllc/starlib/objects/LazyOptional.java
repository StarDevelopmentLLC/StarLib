package com.stardevllc.starlib.objects;

import com.stardevllc.starlib.values.MutableObject;

import java.util.Optional;
import java.util.function.*;

public class LazyOptional<T> {
    private final Supplier<T> supplier;
    private final Object lock = new Object();
    private volatile MutableObject<T> resolved;
    
    private static final LazyOptional<Void> EMPTY = new LazyOptional<>(null);
    
    public static <T> LazyOptional<T> empty() {
        return EMPTY.cast();
    }
    
    public static <T> LazyOptional<T> of(final Supplier<T> supplier) {
        return supplier == null ? empty() : new LazyOptional<>(supplier);
    }
    
    private LazyOptional(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    public <X> LazyOptional<X> cast() {
        return (LazyOptional<X>) this;
    }
    
    private T getValue() {
        if (supplier == null) {
            return null;
        }
        
        if (resolved == null) {
            synchronized (lock) {
                if (resolved == null) {
                    T temp = supplier.get();
                    if (temp == null) {
                        throw new IllegalStateException("Supplier cannot return a null value");
                    }
                    resolved = new MutableObject<>(temp);
                }
            }
        }
        
        return resolved.getValue();
    }
    
    private T getValueUnsafe() {
        T ret = getValue();
        if (ret == null) {
            throw new IllegalStateException("LazyOptional is empty or returned null unexpectedly");
        }
        return ret;
    }
    
    public boolean isPresent() {
        return supplier != null;
    }
    
    public void ifPresent(Consumer<? super T> consumer) {
        if (consumer == null) {
            return;
        }
        
        consumer.accept(getValue());
    }
    
    public <U> LazyOptional<U> lazyMap(Function<? super T, ? extends U> mapper) {
        if (mapper == null) {
            throw new IllegalArgumentException("Mapper is null");
        }
        
        return isPresent() ? of(() -> mapper.apply(getValueUnsafe())) : empty();
    }
    
    public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        if (mapper == null) {
            throw new IllegalArgumentException("Mapper is null");
        }
        
        return isPresent() ? Optional.of(mapper.apply(getValueUnsafe())) : Optional.empty();
    }
    
    public Optional<T> resolve() {
        return isPresent() ? Optional.of(getValueUnsafe()) : Optional.empty();
    }
    
    public T orElse(T other) {
        T val = getValue();
        return val != null ? val : other;
    }
    
    public T orElseGet(Supplier<? extends T> other) {
        T val = getValue();
        return val != null ? val : other.get();
    }
    
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        T val = getValue();
        if (val != null) {
            return val;
        }
        
        throw exceptionSupplier.get();
    }
}