package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.ChangeEvent;
import com.stardevllc.starlib.observable.writable.WritableLongValue;

public class LongProperty extends NumberProperty<Long> implements WritableLongValue {
    
    protected long value;
    
    public LongProperty(Object bean, String name, long value) {
        super(bean, name);
        this.value = value;
    }
    
    public LongProperty(String name, long value) {
        this(null, name, value);
    }
    
    public LongProperty(long value) {
        this(null, "", value);
    }
    
    public LongProperty() {
        this(null, "", 0L);
    }

    @Override
    public Class<Long> getTypeClass() {
        return Long.class;
    }

    @Override
    public void set(long newValue) {
        if (boundValue != null) {
            return;
        }
        
        long oldValue = value;
        value = newValue;
        if (oldValue != newValue) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public long get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        return value;
    }
}
