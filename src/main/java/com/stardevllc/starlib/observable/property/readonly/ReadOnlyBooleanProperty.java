package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;

public class ReadOnlyBooleanProperty extends AbstractReadOnlyProperty<Boolean> implements ObservableBooleanValue {
    
    protected boolean value;
    
    public ReadOnlyBooleanProperty(Object bean, String name, boolean value) {
        this(bean, name);
        this.value = value;
    }
    
    public ReadOnlyBooleanProperty(Object bean, String name) {
        super(bean, name, Boolean.class);
    }
    
    public ReadOnlyBooleanProperty(boolean value) {
        this(null, null, value);
    }
    
    public ReadOnlyBooleanProperty() {
        this(false);
    }
    
    @Override
    public boolean get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
