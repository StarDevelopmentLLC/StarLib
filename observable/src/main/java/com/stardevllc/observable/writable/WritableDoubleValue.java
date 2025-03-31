package com.stardevllc.observable.writable;

import com.stardevllc.observable.value.ObservableDoubleValue;

public interface WritableDoubleValue extends ObservableDoubleValue, WritableNumberValue<Double> {
    void set(double value);
}