package com.stardevllc.property;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.Property;
import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.writable.WritableBooleanValue;

public class BooleanProperty implements Property<Boolean>, WritableBooleanValue {
    
    protected final Object bean;
    protected final String name;
    
    protected boolean value;
    
    protected final EventBus eventBus = new SimpleEventBus();

    public BooleanProperty(Object bean, String name, boolean value) {
        this.bean = bean;
        this.name = name;
        this.value = value;
    }
    
    public BooleanProperty(String name, boolean value) {
        this(null, name, value);
    }
    
    public BooleanProperty(boolean value) {
        this(null, "", value);
    }
    
    public BooleanProperty() {
        this(null, "", false);
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
    public void setValue(Boolean newValue) {
        set(newValue);
    }

    @Override
    public void addListener(ChangeListener<? super Boolean> changeListener) {
        this.eventBus.registerListener(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> changeListener) {
        this.eventBus.removeListener(changeListener);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void set(boolean newValue) {
        boolean oldValue = value;
        value = newValue;
        if (oldValue != value) {
            this.eventBus.post(new ChangeListener.ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public boolean get() {
        return false;
    }

    @Override
    public ObservableBooleanValue and(ObservableBooleanValue observableBooleanValue) {
        return new BooleanProperty(null, "", observableBooleanValue.get() && get());
    }

    @Override
    public ObservableBooleanValue or(ObservableBooleanValue observableBooleanValue) {
        return new BooleanProperty(null, "", observableBooleanValue.get() || get());
    }

    @Override
    public ObservableBooleanValue not() {
        return new BooleanProperty(null, "", !get());
    }
}