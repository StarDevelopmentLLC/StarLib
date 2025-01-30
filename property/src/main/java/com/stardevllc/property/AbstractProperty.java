package com.stardevllc.property;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.Property;

public abstract class AbstractProperty<T> implements Property<T> {

    protected final EventBus<ChangeEvent<T>> eventBus = new SimpleEventBus<>();
    
    protected final Object bean;
    protected final String name;

    public AbstractProperty(Object bean, String name) {
        this.bean = bean;
        this.name = name;
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
    public void addListener(ChangeListener<? super T> changeListener) {
        eventBus.subscribe(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> changeListener) {
        eventBus.unsubscribe(changeListener);
    }
}
