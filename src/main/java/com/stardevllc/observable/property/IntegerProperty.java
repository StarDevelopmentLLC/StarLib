package com.stardevllc.observable.property;

import com.stardevllc.helper.NumberHelper;
import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.value.ObservableNumberValue;
import com.stardevllc.observable.writable.WritableIntegerValue;

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
    public Class<Integer> getTypeClass() {
        return Integer.class;
    }

    @Override
    public void set(int newValue) {
        if (boundValue != null) {
            return;
        }
        
        int oldValue = value;
        value = newValue;
        if (oldValue != newValue) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public void setValue(Integer integer) {
        set(integer);
    }

    @Override
    public int get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }

    @Override
    public ObservableNumberValue<Integer> negate() {
        return new IntegerProperty((Integer) NumberHelper.negate(value));
    }

    @Override
    public ObservableNumberValue<Integer> add(Number number) {
        return new IntegerProperty((Integer) NumberHelper.add(value, number.intValue()));
    }

    @Override
    public ObservableNumberValue<Integer> subtract(Number number) {
        return new IntegerProperty((Integer) NumberHelper.subtract(value, number.intValue()));
    }

    @Override
    public ObservableNumberValue<Integer> multiply(Number number) {
        return new IntegerProperty((Integer) NumberHelper.multiply(value, number.intValue()));
    }

    @Override
    public ObservableNumberValue<Integer> divide(Number number) {
        return new IntegerProperty((Integer) NumberHelper.divide(value, number.intValue()));
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
        return get();
    }
}
