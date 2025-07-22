package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableObjectValue;

public class ObjectConstant<T> implements ObservableObjectValue<T> {
    
    private final T object;

    public ObjectConstant(T object) {
        this.object = object;
    }

    @Override
    public T get() {
        return object;
    }
}
