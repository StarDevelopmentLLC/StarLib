package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableBooleanValue;
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

    @Override
    public ObservableDoubleValue negate() {
        return new DoubleConstant(-value);
    }

    @Override
    public ObservableDoubleValue add(Number other) {
        return new DoubleConstant(value + other.doubleValue());
    }

    @Override
    public ObservableDoubleValue subtract(Number other) {
        return new DoubleConstant(value - other.doubleValue());
    }

    @Override
    public ObservableDoubleValue multiply(Number other) {
        return new DoubleConstant(value * other.doubleValue());
    }

    @Override
    public ObservableDoubleValue divide(Number other) {
        return new DoubleConstant(value / other.doubleValue());
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number other) {
        return new BooleanConstant(value == other.doubleValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number other) {
        return new BooleanConstant(value != other.doubleValue());
    }

    @Override
    public ObservableBooleanValue greaterThan(Number other) {
        return new BooleanConstant(value > other.doubleValue());
    }

    @Override
    public ObservableBooleanValue lessThan(Number other) {
        return new BooleanConstant(value < other.doubleValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return new BooleanConstant(value >= other.doubleValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return new BooleanConstant(value <= other.doubleValue());
    }

    @Override
    public Double getValue() {
        return get();
    }
}
