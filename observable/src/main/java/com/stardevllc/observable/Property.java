package com.stardevllc.observable;

public interface Property<T> extends WritableValue<T> {
    Object getBean();
    String getName();
    
    Class<T> getTypeClass();
}