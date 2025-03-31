package com.stardevllc.observable.writable;

import com.stardevllc.observable.value.ObservableFloatValue;

public interface WritableFloatValue extends ObservableFloatValue, WritableNumberValue<Float> {
    void set(float value);
}