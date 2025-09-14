package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableObjectValue;

public class ReadOnlyObjectProperty<T> extends AbstractReadOnlyProperty<T> implements ObservableObjectValue<T> {
    
    protected T value;
    
    public ReadOnlyObjectProperty(Object bean, String name, T value) {
        this(bean, name, (Class<T>) value.getClass());
        this.value = value;
    }
    
    public ReadOnlyObjectProperty(Object bean, String name, Class<T> clazz) {
        super(bean, name, clazz);
    }
    
    public ReadOnlyObjectProperty(T value) {
        this(null, null, value);
    }
    
    public ReadOnlyObjectProperty(Class<T> clazz) {
        this(null, null, clazz);
    }
    
    @Override
    public T get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
