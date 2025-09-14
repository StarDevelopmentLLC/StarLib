package com.stardevllc.starlib.observable;

public interface ReadWriteProperty<T> extends ReadOnlyProperty<T>, WritableProperty<T> {
    ReadOnlyProperty<T> asReadOnly();
}