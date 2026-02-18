package com.stardevllc.starlib.values;

public interface MutableValue<T> extends Value<T> {
    void setValue(T value);
}