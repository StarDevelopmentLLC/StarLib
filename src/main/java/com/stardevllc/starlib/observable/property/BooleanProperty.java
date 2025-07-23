package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.ChangeEvent;
import com.stardevllc.starlib.observable.writable.WritableBooleanValue;

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
    public Class<Boolean> getTypeClass() {
        return Boolean.class;
    }
    
    @Override
    public void set(boolean newValue) {
        if (boundValue != null) {
            return;
        }
        boolean oldValue = value;
        value = newValue;
        if (oldValue != value) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
        
    }
    
    @Override
    public boolean get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}