package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.ChangeEvent;
import com.stardevllc.starlib.observable.writable.WritableIntegerValue;

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
    public int get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}