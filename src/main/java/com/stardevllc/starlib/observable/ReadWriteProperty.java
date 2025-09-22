package com.stardevllc.starlib.observable;

/**
 * Represents a property that can be written to
 *
 * @param <T> The type
 */
public interface ReadWriteProperty<T> extends ReadOnlyProperty<T>, WritableProperty<T> {
    
    /**
     * Creates a read only version of this property
     * @return The read only copy
     */
    ReadOnlyProperty<T> asReadOnly();
}