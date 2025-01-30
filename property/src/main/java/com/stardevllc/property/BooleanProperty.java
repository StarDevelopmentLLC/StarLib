package com.stardevllc.property;

import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.writable.WritableBooleanValue;

public class BooleanProperty extends AbstractProperty<Boolean> implements WritableBooleanValue {
    
    protected boolean value;
    
    public BooleanProperty(Object bean, String name, boolean value) {
        super(bean, name);
        this.value = value;
    }
    
    public BooleanProperty(String name, boolean value) {
        this(null, name, value);
    }
    
    public BooleanProperty(boolean value) {
        this(null, "", value);
    }
    
    public BooleanProperty() {
        this(null, "", false);
    }

    @Override
    public void setValue(Boolean newValue) {
        set(newValue);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void set(boolean newValue) {
        boolean oldValue = value;
        value = newValue;
        if (oldValue != value) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public ObservableBooleanValue and(ObservableBooleanValue observableBooleanValue) {
        return new BooleanProperty(null, "", observableBooleanValue.get() && get());
    }

    @Override
    public ObservableBooleanValue or(ObservableBooleanValue observableBooleanValue) {
        return new BooleanProperty(null, "", observableBooleanValue.get() || get());
    }

    @Override
    public ObservableBooleanValue not() {
        return new BooleanProperty(null, "", !get());
    }
}