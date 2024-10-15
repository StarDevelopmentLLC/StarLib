package com.stardevllc.observable;

public interface WritableArray<T> extends ObservableArray<T> {
    void set(int index, T value);
}
