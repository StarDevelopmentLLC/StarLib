package com.stardevllc.observable;

public interface ObservableValue<T> extends Observable {
    default void addListener(ChangeListener<? super T> listener) {}
    default void removeListener(ChangeListener<? super T> listener) {}
    T getValue();
}
