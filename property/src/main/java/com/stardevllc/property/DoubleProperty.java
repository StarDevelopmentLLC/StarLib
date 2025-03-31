package com.stardevllc.property;

import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.value.ObservableNumberValue;
import com.stardevllc.observable.writable.WritableDoubleValue;
import com.stardevllc.starlib.numbers.SimpleCalculator;

public class DoubleProperty extends NumberProperty<Double> implements WritableDoubleValue {
    
    protected double value;
    
    public DoubleProperty(Object bean, String name, double value) {
        super(bean, name);
        this.value = value;
    }
    
    public DoubleProperty(String name, double value) {
        this(null, name, value);
    }
    
    public DoubleProperty(double value) {
        this(null, "", value);
    }
    
    public DoubleProperty() {
        this(null, "", 0.0);
    }

    @Override
    public Class<Double> getTypeClass() {
        return Double.class;
    }

    @Override
    public void set(double newValue) {
        double oldValue = value;
        value = newValue;
        if (oldValue != newValue) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public void setValue(Double newValue) {
        set(newValue);
    }

    @Override
    public double get() {
        return value;
    }

    @Override
    public ObservableNumberValue<Double> negate() {
        return new DoubleProperty((Double) SimpleCalculator.negate(value));
    }

    @Override
    public ObservableNumberValue<Double> add(Number number) {
        return new DoubleProperty((Double) SimpleCalculator.add(value, number.doubleValue()));
    }

    @Override
    public ObservableNumberValue<Double> subtract(Number number) {
        return new DoubleProperty((Double) SimpleCalculator.subtract(value, number.doubleValue()));
    }

    @Override
    public ObservableNumberValue<Double> multiply(Number number) {
        return new DoubleProperty((Double) SimpleCalculator.multiply(value, number.doubleValue()));
    }

    @Override
    public ObservableNumberValue<Double> divide(Number number) {
        return new DoubleProperty((Double) SimpleCalculator.divide(value, number.doubleValue()));
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number number) {
        return new BooleanProperty(get() == number.doubleValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number number) {
        return new BooleanProperty(get() != number.doubleValue());
    }

    @Override
    public ObservableBooleanValue greaterThan(Number number) {
        return new BooleanProperty(get() > number.doubleValue());
    }

    @Override
    public ObservableBooleanValue lessThan(Number number) {
        return new BooleanProperty(get() < number.doubleValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number number) {
        return new BooleanProperty(get() >= number.doubleValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number number) {
        return new BooleanProperty(get() <= number.doubleValue());
    }

    @Override
    public Double getValue() {
        return value;
    }
}
