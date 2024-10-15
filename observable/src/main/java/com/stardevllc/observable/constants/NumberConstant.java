package com.stardevllc.observable.constants;

import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.value.ObservableNumberValue;

public abstract class NumberConstant<T extends Number> implements ObservableNumberValue<T> {
    @Override
    public void addListener(ChangeListener<? super T> listener) {
        //no-op (Cannot be changed)
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        //no-op (Cannot be changed)
    }
}