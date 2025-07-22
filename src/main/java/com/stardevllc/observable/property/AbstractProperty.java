package com.stardevllc.observable.property;

import com.stardevllc.eventbus.IEventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.*;

/**
 * An Abstract Property that contains common components of a Property
 *
 * @param <T> The value type
 */
public abstract class AbstractProperty<T> implements Property<T> {
    
    /**
     * The EventBus that is used for processing changes
     */
    protected final IEventBus<ChangeEvent<T>> eventBus = new SimpleEventBus<>();
    
    /**
     * The object that "owns" this property
     */
    protected final Object bean;
    
    /**
     * The name of this property
     */
    protected final String name;
    
    /**
     * The value that this property is bound to
     */
    protected ObservableValue<T> boundValue;
    
    /**
     * Constructs a new Property
     *
     * @param bean The owner of the property
     * @param name The name of the property
     */
    public AbstractProperty(Object bean, String name) {
        this.bean = bean;
        this.name = name;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(ObservableValue<T> other) {
        this.boundValue = other;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return bean;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(ChangeListener<? super T> changeListener) {
        eventBus.subscribe(changeListener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(ChangeListener<? super T> changeListener) {
        eventBus.unsubscribe(changeListener);
    }
}
