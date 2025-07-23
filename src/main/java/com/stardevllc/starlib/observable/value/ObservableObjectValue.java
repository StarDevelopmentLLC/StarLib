package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.observable.ObservableValue;

/**
 * Represents an Object that can be observed
 *
 * @param <T> The object type
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableObjectValue<T> extends ObservableValue<T> {
    /**
     * Gets this value. Mainly for consistency with the others
     *
     * @return The value
     */
    T get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default T getValue() {
        return get();
    }
}