package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableBooleanValue;
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

    @Override
    public ObservableFloatValue negate() {
        return new FloatConstant(-value);
    }

    @Override
    public ObservableFloatValue add(Number other) {
        return new FloatConstant(value + other.floatValue());
    }

    @Override
    public ObservableFloatValue subtract(Number other) {
        return new FloatConstant(value - other.floatValue());
    }

    @Override
    public ObservableFloatValue multiply(Number other) {
        return new FloatConstant(value * other.floatValue());
    }

    @Override
    public ObservableFloatValue divide(Number other) {
        return new FloatConstant(value / other.floatValue());
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number other) {
        return new BooleanConstant(value == other.floatValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number other) {
        return new BooleanConstant(value != other.floatValue());
    }

    @Override
    public ObservableBooleanValue greaterThan(Number other) {
        return new BooleanConstant(value > other.floatValue());
    }

    @Override
    public ObservableBooleanValue lessThan(Number other) {
        return new BooleanConstant(value < other.floatValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return new BooleanConstant(value >= other.floatValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return new BooleanConstant(value <= other.floatValue());
    }

    @Override
    public Float getValue() {
        return get();
    }
}
