package com.stardevllc.observable;

@FunctionalInterface
public interface ChangeListener<T> {
    void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue);
}
