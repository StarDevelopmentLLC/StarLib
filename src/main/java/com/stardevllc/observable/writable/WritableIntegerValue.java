package com.stardevllc.observable.writable;

import com.stardevllc.observable.value.ObservableIntegerValue;

public interface WritableIntegerValue extends ObservableIntegerValue, WritableNumberValue<Integer> {
    void set(int value);
    @Override
    default void setValue(Integer value) {
        set(value);
    }
}