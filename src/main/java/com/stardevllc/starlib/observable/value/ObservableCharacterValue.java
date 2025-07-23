package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.observable.ObservableValue;

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