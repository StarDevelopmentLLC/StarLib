package com.stardevllc.property;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.Property;
import com.stardevllc.observable.writable.WritableObjectValue;

public class ObjectProperty<T> implements Property<T>, WritableObjectValue<T> {
    
    protected final Object bean;
    protected final String name;
    
    protected T value;
    
    protected final EventBus eventBus = new SimpleEventBus();

    public ObjectProperty(Object bean, String name, T value) {
        this.bean = bean;
        this.name = name;
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
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T newValue) {
        T oldValue = value;
        value = newValue;
        ChangeListener.ChangeEvent<T> event = new ChangeListener.ChangeEvent<>(this, oldValue, newValue);
        
        if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null)) {
            eventBus.post(event);
        }
        
        if (!oldValue.equals(newValue)) {
            eventBus.post(event);
        }
    }

    @Override
    public void setValue(T newValue) {
        set(newValue);
    }

    @Override
    public void addListener(ChangeListener<? super T> changeListener) {
        this.eventBus.subscribe(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> changeListener) {
        this.eventBus.unsubscribe(changeListener);
    }

    @Override
    public T getValue() {
        return value;
    }
}
