package com.stardevllc.starlib.observable;

public interface Property<T> extends ObservableValue<T> {
    Object DEFAULT_BEAN = null;
    String DEFAULT_NAME = "";

    Object getBean();
    String getName();
}