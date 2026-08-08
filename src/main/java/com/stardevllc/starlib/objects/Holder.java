package com.stardevllc.starlib.objects;

import java.util.function.Supplier;

/**
 * This represents an object that is a reference to someting that exists elsewhere
 *
 * @param <K> The key type
 * @param <T> The value type
 */
public interface Holder<K, T> extends Supplier<T> {
    
    /**
     * This is the identifier of the holder. Used to get the value
     *
     * @return The key
     */
    K key();
    
    /**
     * Retrieves the value of the holder itself <br>
     * Implementations may cache this value or fetch it each time, it is up to the implementation
     *
     * @return The value that the key represents
     */
    T value();
    
    @Override
    default T get() {
        return value();
    }
}