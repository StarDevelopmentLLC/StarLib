package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableFloatValue;

public class ReadOnlyFloatProperty extends AbstractReadOnlyProperty<Float> implements ObservableFloatValue {
    
    protected float value;
    
    public ReadOnlyFloatProperty(Object bean, String name, float value) {
        this(bean, name);
        this.value = value;
    }
    
    public ReadOnlyFloatProperty(Object bean, String name) {
        super(bean, name, Float.class);
    }
    
    public ReadOnlyFloatProperty(float value) {
        this(null, null, value);
    }
    
    public ReadOnlyFloatProperty() {
        this(null, null);
    }
    
    @Override
    public float get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
