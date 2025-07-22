package com.stardevllc.observable.value;

import com.stardevllc.observable.ObservableValue;

/**
 * Represents a Boolean Value that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableBooleanValue extends ObservableValue<Boolean> {
    
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    boolean get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Boolean getValue() {
        return get();
    }
    
    /**
     * Performs a logical AND operation and returns the new value as an Observable Value
     *
     * @param other The other value
     * @return The result of the operation
     */
    default ObservableBooleanValue and(ObservableBooleanValue other) {
        return () -> get() && other.get();
    }
    
    /**
     * Permforms a logical OR operation and returns the new value as an Observable Value
     *
     * @param other The other value
     * @return The result of the operation
     */
    default ObservableBooleanValue or(ObservableBooleanValue other) {
        return () -> get() || other.get();
    }
    
    /**
     * Performs a logical NOT operation and returns the new value as an Observable Value
     *
     * @return The result of the operation
     */
    default ObservableBooleanValue not() {
        return () -> !get();
    }
}