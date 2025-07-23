package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.ChangeEvent;
import com.stardevllc.starlib.observable.writable.WritableFloatValue;

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
    public float get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}