package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableIntegerValue;

public class ReadOnlyIntegerProperty extends AbstractReadOnlyProperty<Integer> implements ObservableIntegerValue {
    
    protected int value;
    
    public ReadOnlyIntegerProperty(Object bean, String name, int value) {
        this(bean, name);
        this.value = value;
    }
    
    public ReadOnlyIntegerProperty(Object bean, String name) {
        super(bean, name, Integer.class);
    }
    
    public ReadOnlyIntegerProperty(int value) {
        this(null, null, value);
    }
    
    public ReadOnlyIntegerProperty() {
        this(null, null);
    }
    
    @Override
    public int get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
