package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableIntegerValue;

public class IntegerConstant extends NumberConstant<Integer> implements ObservableIntegerValue {
    
    private final int value;
    public IntegerConstant(final int value) {
        this.value = value;
    }
    
    @Override
    public int get() {
        return value;
    }
}
