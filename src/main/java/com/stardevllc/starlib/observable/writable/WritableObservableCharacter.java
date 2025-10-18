package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.WritableObservableValue;
import com.stardevllc.starlib.observable.value.ObservableCharacter;

/**
 * Represents a Writable Character Observable value
 */
public interface WritableObservableCharacter extends ObservableCharacter, WritableObservableValue<Character> {
    /**
     * Sets the value to the provided value
     *
     * @param newValue The new value
     */
    void set(char newValue);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Character character) {
        set(character);
    }
}