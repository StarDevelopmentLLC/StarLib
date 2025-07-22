package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableFloatValue;

/**
 * Represents a constant Float value
 */
public class FloatConstant implements ObservableFloatValue {
    
    private final float value;
    
    /**
     * Constructs a new Float constant with the provided value
     *
     * @param value The value to set the constant to
     */
    public FloatConstant(final float value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float get() {
        return value;
    }
}
