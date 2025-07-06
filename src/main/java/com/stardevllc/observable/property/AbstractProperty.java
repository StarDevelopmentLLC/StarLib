package com.stardevllc.observable.property;

import com.stardevllc.eventbus.IEventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.*;

public abstract class AbstractProperty<T> implements Property<T> {

    protected final IEventBus<ChangeEvent<T>> eventBus = new SimpleEventBus<>();
    
    protected final Object bean;
    protected final String name;
    
    protected ObservableValue<T> boundValue;

    public AbstractProperty(Object bean, String name) {
        this.bean = bean;
        this.name = name;
    }
    
    @Override
    public void bind(ObservableValue<T> other) {
        this.boundValue = other;
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
