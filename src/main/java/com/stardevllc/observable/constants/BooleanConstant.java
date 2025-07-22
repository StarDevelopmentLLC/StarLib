package com.stardevllc.observable.constants;

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
}