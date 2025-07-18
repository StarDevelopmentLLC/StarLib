package com.stardevllc.observable.property;

import com.stardevllc.helper.NumberHelper;
import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.value.ObservableNumberValue;
import com.stardevllc.observable.writable.WritableFloatValue;

public class FloatProperty extends NumberProperty<Float> implements WritableFloatValue {
    
    protected float value;
    
    public FloatProperty(Object bean, String name, float value) {
        super(bean, name);
        this.value = value;
    }
    
    public FloatProperty(String name, float value) {
        this(null, name, value);
    }
    
    public FloatProperty(float value) {
        this(null, "", value);
    }
    
    public FloatProperty() {
        this(null, "", 0F);
    }

    @Override
    public Class<Float> getTypeClass() {
        return Float.class;
    }

    @Override
    public void set(float newValue) {
        if (boundValue != null) {
            return;
        }
        
        float oldValue = value;
        value = newValue;
        if (oldValue != newValue) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public void setValue(Float newValue) {
        set(newValue);
    }

    @Override
    public float get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }

    @Override
    public ObservableNumberValue<Float> negate() {
        return new FloatProperty((Float) NumberHelper.negate(value));
    }

    @Override
    public ObservableNumberValue<Float> add(Number number) {
        return new FloatProperty((Float) NumberHelper.add(value, number.floatValue()));
    }

    @Override
    public ObservableNumberValue<Float> subtract(Number number) {
        return new FloatProperty((Float) NumberHelper.subtract(value, number.floatValue()));
    }

    @Override
    public ObservableNumberValue<Float> multiply(Number number) {
        return new FloatProperty((Float) NumberHelper.multiply(value, number.floatValue()));
    }

    @Override
    public ObservableNumberValue<Float> divide(Number number) {
        return new FloatProperty((Float) NumberHelper.divide(value, number.floatValue()));
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number number) {
        return new BooleanProperty(get() == number.floatValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number number) {
        return new BooleanProperty(get() != number.floatValue());
    }

    @Override
    public ObservableBooleanValue greaterThan(Number number) {
        return new BooleanProperty(get() > number.floatValue());
    }

    @Override
    public ObservableBooleanValue lessThan(Number number) {
        return new BooleanProperty(get() < number.floatValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number number) {
        return new BooleanProperty(get() >= number.floatValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number number) {
        return new BooleanProperty(get() <= number.floatValue());
    }

    @Override
    public Float getValue() {
        return get();
    }
}
