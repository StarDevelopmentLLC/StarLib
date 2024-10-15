package com.stardevllc.property;

import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.value.ObservableNumberValue;
import com.stardevllc.observable.writable.WritableIntegerValue;
import com.stardevllc.starlib.numbers.SimpleCalculator;

public class IntegerProperty extends NumberProperty<Integer> implements WritableIntegerValue {
    
    protected int value;
    
    public IntegerProperty(Object bean, String name, int value) {
        super(bean, name);
        this.value = value;
    }
    
    public IntegerProperty(String name, int value) {
        this(null, name, value);
    }
    
    public IntegerProperty(int value) {
        this(null, "", value);
    }
    
    public IntegerProperty() {
        this(null, "", 0);
    }

    @Override
    public void set(int newValue) {
        int oldValue = value;
        value = newValue;
        if (oldValue != newValue) {
            this.eventBus.post(new ChangeListener.ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public void setValue(Integer integer) {
        set(integer);
    }

    @Override
    public int get() {
        return value;
    }

    @Override
    public ObservableNumberValue<Integer> negate() {
        return new IntegerProperty((Integer) SimpleCalculator.negate(value));
    }

    @Override
    public ObservableNumberValue<Integer> add(Number number) {
        return new IntegerProperty((Integer) SimpleCalculator.add(value, number.intValue()));
    }

    @Override
    public ObservableNumberValue<Integer> subtract(Number number) {
        return new IntegerProperty((Integer) SimpleCalculator.subtract(value, number.intValue()));
    }

    @Override
    public ObservableNumberValue<Integer> multiply(Number number) {
        return new IntegerProperty((Integer) SimpleCalculator.multiply(value, number.intValue()));
    }

    @Override
    public ObservableNumberValue<Integer> divide(Number number) {
        return new IntegerProperty((Integer) SimpleCalculator.divide(value, number.intValue()));
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number number) {
        return new BooleanProperty(get() == number.intValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number number) {
        return new BooleanProperty(get() != number.intValue());
    }

    @Override
    public ObservableBooleanValue greaterThan(Number number) {
        return new BooleanProperty(get() > number.intValue());
    }

    @Override
    public ObservableBooleanValue lessThan(Number number) {
        return new BooleanProperty(get() < number.intValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number number) {
        return new BooleanProperty(get() >= number.intValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number number) {
        return new BooleanProperty(get() <= number.intValue());
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
