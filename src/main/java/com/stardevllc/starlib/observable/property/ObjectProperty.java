package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.ChangeEvent;
import com.stardevllc.starlib.observable.writable.WritableObjectValue;

public class ObjectProperty<T> extends AbstractProperty<T> implements WritableObjectValue<T> {
    
    protected T value;
    protected Class<T> typeClass;
    
    public ObjectProperty(Class<T> typeClass, Object bean, String name, T value) {
        super(bean, name);
        this.value = value;
    }
    
    public ObjectProperty(Class<T> typeClass, String name, T value) {
        this(typeClass, null, name, value);
    } 
    
    public ObjectProperty(Class<T> typeClass, T value) {
        this(typeClass, null, null, value);
    }
    
    public ObjectProperty(Class<T> typeClass) {
        this(typeClass, null, null, null);
    }

    @Override
    public Class<T> getTypeClass() {
        return typeClass;
    }

    @Override
    public T get() {
        if (this.boundValue != null) {
            return this.boundValue.getValue();
        }
        
        return value;
    }

    @Override
    public void set(T newValue) {
        if (this.boundValue != null) {
            return;
        }
        
        T oldValue = value;
        value = newValue;
        ChangeEvent<T> event = new ChangeEvent<>(this, oldValue, newValue);
        
        if (oldValue == null && newValue != null || oldValue != null && newValue == null) {
            eventBus.post(event);
        }
        
        if (oldValue != null && !oldValue.equals(newValue)) {
            eventBus.post(event);
        }
    }
}