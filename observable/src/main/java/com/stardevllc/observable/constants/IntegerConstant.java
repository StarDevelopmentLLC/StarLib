package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableBooleanValue;
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

    @Override
    public ObservableIntegerValue negate() {
        return new IntegerConstant(-value);
    }

    @Override
    public ObservableIntegerValue add(Number other) {
        return new IntegerConstant(value + other.intValue());
    }

    @Override
    public ObservableIntegerValue subtract(Number other) {
        return new IntegerConstant(value - other.intValue());
    }

    @Override
    public ObservableIntegerValue multiply(Number other) {
        return new IntegerConstant(value * other.intValue());
    }

    @Override
    public ObservableIntegerValue divide(Number other) {
        return new IntegerConstant(value / other.intValue());
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number other) {
        return new BooleanConstant(value == other.intValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number other) {
        return new BooleanConstant(value != other.intValue());
    }

    @Override
    public ObservableBooleanValue greaterThan(Number other) {
        return new BooleanConstant(value > other.intValue());
    }

    @Override
    public ObservableBooleanValue lessThan(Number other) {
        return new BooleanConstant(value < other.intValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return new BooleanConstant(value >= other.intValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return new BooleanConstant(value <= other.intValue());
    }

    @Override
    public Integer getValue() {
        return get();
    }
}
