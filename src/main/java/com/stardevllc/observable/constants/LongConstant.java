package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.value.ObservableLongValue;

public class LongConstant extends NumberConstant<Long> implements ObservableLongValue {
    
    private final long value;
    public LongConstant(final long value) {
        this.value = value;
    }
    
    @Override
    public long get() {
        return value;
    }

    @Override
    public ObservableLongValue negate() {
        return new LongConstant(-value);
    }

    @Override
    public ObservableLongValue add(Number other) {
        return new LongConstant(value + other.longValue());
    }

    @Override
    public ObservableLongValue subtract(Number other) {
        return new LongConstant(value - other.longValue());
    }

    @Override
    public ObservableLongValue multiply(Number other) {
        return new LongConstant(value * other.longValue());
    }

    @Override
    public ObservableLongValue divide(Number other) {
        return new LongConstant(value / other.longValue());
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number other) {
        return new BooleanConstant(value == other.longValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number other) {
        return new BooleanConstant(value != other.longValue());
    }

    @Override
    public ObservableBooleanValue greaterThan(Number other) {
        return new BooleanConstant(value > other.longValue());
    }

    @Override
    public ObservableBooleanValue lessThan(Number other) {
        return new BooleanConstant(value < other.longValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return new BooleanConstant(value >= other.longValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return new BooleanConstant(value <= other.longValue());
    }

    @Override
    public Long getValue() {
        return get();
    }
}
