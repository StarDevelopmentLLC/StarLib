package com.stardevllc.property;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.Property;
import com.stardevllc.observable.writable.WritableNumberValue;

public abstract class NumberProperty<N extends Number> implements Property<N>, WritableNumberValue<N> {
    
    protected final Object bean;
    protected final String name;
    protected final EventBus<ChangeEvent<N>> eventBus = new SimpleEventBus<>();

    public NumberProperty(Object bean, String name) {
        this.bean = bean;
        this.name = name;
    }
    
    public NumberProperty(String name) {
        this(null, name);
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
    public void addListener(ChangeListener<? super N> changeListener) {
        this.eventBus.subscribe(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super N> changeListener) {
        this.eventBus.unsubscribe(changeListener);
    }
}
