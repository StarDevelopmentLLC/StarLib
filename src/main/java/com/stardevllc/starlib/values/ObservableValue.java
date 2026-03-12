package com.stardevllc.starlib.values;

import com.stardevllc.starlib.values.observable.listener.ChangeListener;

public interface ObservableValue<T> extends MutableValue<T>, Observable {
    void addChangeListener(ChangeListener<T> listener);
    void removeChangeListener(ChangeListener<T> listener);
    
    default void setValue(Value<T> value) {
        if (value != null) {
            this.setValue(value.getValue());
        }
    }
}