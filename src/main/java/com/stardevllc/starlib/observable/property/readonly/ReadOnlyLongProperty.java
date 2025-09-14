package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableLongValue;

public class ReadOnlyLongProperty extends AbstractReadOnlyProperty<Long> implements ObservableLongValue {
    
    protected long value;
    
    public ReadOnlyLongProperty(Object bean, String name, long value) {
        this(bean, name);
        this.value = value;
    }
    
    public ReadOnlyLongProperty(Object bean, String name) {
        super(bean, name, Long.class);
    }
    
    public ReadOnlyLongProperty(long value) {
        this(null, null, value);
    }
    
    public ReadOnlyLongProperty() {
        this(null, null);
    }
    
    @Override
    public long get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
