package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableFloatValue;

public class FloatConstant extends NumberConstant<Float> implements ObservableFloatValue {
    
    private final float value;
    public FloatConstant(final float value) {
        this.value = value;
    }
    
    @Override
    public float get() {
        return value;
    }
}
