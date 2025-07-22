package com.stardevllc.observable.value;

import com.stardevllc.observable.ObservableValue;

public interface ObservableCharacterValue extends ObservableValue<Character> {
    char get();
    @Override
    default Character getValue() {
        return get();
    }
}