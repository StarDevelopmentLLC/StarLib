package com.stardevllc.observable;

public interface Property<T> extends WritableValue<T> {
    Object getBean();
    String getName();
    
    void bind(ObservableValue<T> other);
    
    Class<T> getTypeClass();
}