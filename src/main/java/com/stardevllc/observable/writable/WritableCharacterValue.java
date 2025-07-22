package com.stardevllc.observable.writable;

import com.stardevllc.observable.WritableValue;
import com.stardevllc.observable.value.ObservableCharacterValue;

/**
 * Represents a Writable Character Observable value
 */
public interface WritableCharacterValue extends ObservableCharacterValue, WritableValue<Character> {
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