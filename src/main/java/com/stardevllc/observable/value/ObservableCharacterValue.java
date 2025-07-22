package com.stardevllc.observable.value;

import com.stardevllc.observable.ObservableValue;

/**
 * Represents a character that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableCharacterValue extends ObservableValue<Character> {
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    char get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Character getValue() {
        return get();
    }
}