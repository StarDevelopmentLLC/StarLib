package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableDoubleValue;

public class DoubleConstant extends NumberConstant<Double> implements ObservableDoubleValue {
    
    private final double value;
    public DoubleConstant(final double value) {
        this.value = value;
    }
    
    @Override
    public double get() {
        return value;
    }
}
