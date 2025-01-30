package com.stardevllc.property;

import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.writable.WritableObjectValue;

public class ObjectProperty<T> extends AbstractProperty<T> implements WritableObjectValue<T> {
    
    protected T value;
    
    public ObjectProperty(Object bean, String name, T value) {
        super(bean, name);
        this.value = value;
    }
    
    public ObjectProperty(String name, T value) {
        this(null, name, value);
    } 
    
    public ObjectProperty(T value) {
        this(null, null, value);
    }
    
    public ObjectProperty() {
        this(null);
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T newValue) {
        T oldValue = value;
        value = newValue;
        ChangeEvent<T> event = new ChangeEvent<>(this, oldValue, newValue);
        
        if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null)) {
            eventBus.post(event);
        }
        
        if (oldValue != null && !oldValue.equals(newValue)) {
            eventBus.post(event);
        }
    }

    @Override
    public void setValue(T newValue) {
        set(newValue);
    }

    @Override
    public T getValue() {
        return value;
    }
}