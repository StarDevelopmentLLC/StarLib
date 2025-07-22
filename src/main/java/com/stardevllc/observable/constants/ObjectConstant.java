package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableObjectValue;

/**
 * Represents a constant Object value
 * 
 * @param <T> The Object type
 */
public class ObjectConstant<T> implements ObservableObjectValue<T> {
    
    private final T object;
    
    /**
     * Constructs a new Object constant with the provided value
     *
     * @param object The value to set the constant to
     */
    public ObjectConstant(T object) {
        this.object = object;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public T get() {
        return object;
    }
}
