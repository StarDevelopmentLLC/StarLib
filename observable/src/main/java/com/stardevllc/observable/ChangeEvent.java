package com.stardevllc.observable;

public record ChangeEvent<T>(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {}
