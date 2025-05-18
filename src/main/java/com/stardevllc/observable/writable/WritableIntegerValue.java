package com.stardevllc.observable.writable;

import com.stardevllc.observable.value.ObservableIntegerValue;

public interface WritableIntegerValue extends ObservableIntegerValue, WritableNumberValue<Integer> {
    void set(int value);
}