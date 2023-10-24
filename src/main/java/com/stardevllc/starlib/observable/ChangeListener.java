package com.stardevllc.starlib.observable;

@FunctionalInterface
public interface ChangeListener<T> {
    void onChange(ObservableValue<T> observable, T oldObject, T newObject);
}
