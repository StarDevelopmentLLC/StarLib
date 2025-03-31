package com.stardevllc.observable.constants;

import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.value.ObservableBooleanValue;

public class BooleanConstant implements ObservableBooleanValue {
    
    private final boolean value;

    public BooleanConstant(boolean value) {
        this.value = value;
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public ObservableBooleanValue and(ObservableBooleanValue other) {
        return new BooleanConstant(value && other.get());
    }

    @Override
    public ObservableBooleanValue or(ObservableBooleanValue other) {
        return new BooleanConstant(value || other.get());
    }

    @Override
    public ObservableBooleanValue not() {
        return new BooleanConstant(!value);
    }

    @Override
    public void addListener(ChangeListener<? super Boolean> listener) {
        //no-op (Cannot be changed)
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {
        //no-op (Cannot be changed)
    }

    @Override
    public Boolean getValue() {
        return get();
    }
}