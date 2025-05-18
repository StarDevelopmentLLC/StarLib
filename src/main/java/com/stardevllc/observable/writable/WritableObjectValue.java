package com.stardevllc.observable.writable;

import com.stardevllc.observable.WritableValue;

public interface WritableObjectValue<T> extends WritableValue<T> {
    T get();
    void set(T value);
}