package com.stardevllc.observable.constants;

import com.stardevllc.observable.ChangeListener;
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

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        //no-op (It cannot be changed)
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        //no-op (It cannot be changed)
    }

    @Override
    public T getValue() {
        return object;
    }
}
