package com.stardevllc.observable;

public interface WritableValue<T> extends ObservableValue<T> {
    void setValue(T value);
}