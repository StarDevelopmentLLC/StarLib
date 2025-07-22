package com.stardevllc.observable.writable;

import com.stardevllc.observable.WritableValue;
import com.stardevllc.observable.value.ObservableCharacterValue;

public interface WritableCharacterValue extends ObservableCharacterValue, WritableValue<Character> {
    void set(char newValue);
    @Override
    default void setValue(Character character) {
        set(character);
    }
}