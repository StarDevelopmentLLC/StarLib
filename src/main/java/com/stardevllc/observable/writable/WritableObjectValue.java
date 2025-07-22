package com.stardevllc.observable.writable;

import com.stardevllc.observable.WritableValue;
import com.stardevllc.observable.value.ObservableObjectValue;

public interface WritableObjectValue<T> extends ObservableObjectValue<T>, WritableValue<T> {
    void set(T value);
    
    @Override
    default void setValue(T value) {
        set(value);
    }
}