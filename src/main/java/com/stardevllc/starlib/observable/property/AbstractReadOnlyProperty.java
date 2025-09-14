package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starlib.eventbus.impl.SimpleEventBus;
import com.stardevllc.starlib.observable.*;

import java.util.Objects;

public abstract class AbstractReadOnlyProperty<T> implements ReadOnlyProperty<T> {
    
    protected final Object bean;
    protected final String name;
    protected final Class<T> typeClass;
    protected final IEventBus<ChangeEvent<T>> eventBus;
    
    protected ObservableValue<T> boundValue;
    protected BindListener<T> bindListener;
    
    public AbstractReadOnlyProperty(Object bean, String name, Class<T> typeClass) {
        this.bean = bean;
        this.name = name;
        this.typeClass = typeClass;
        this.eventBus = new SimpleEventBus<>();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(ObservableValue<T> other) {
        T oldValue = getValue();
        if (other != null && bindListener != null) {
            other.removeListener(bindListener);
            this.bindListener = null;
        }
        
        this.boundValue = other;
        if (other != null) {
            this.bindListener = new BindListener<>(this);
            other.addListener(this.bindListener);
        }
        
        T newValue = getValue();
        if (!Objects.equals(oldValue, newValue)) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        T value = getValue();
        if (this.boundValue != null) {
            this.boundValue.removeListener(this.bindListener);
            this.boundValue = null;
            this.bindListener = null;
        }
        
        if (!Objects.equals(value, this.getValue())) {
            this.eventBus.post(new ChangeEvent<>(this, value, this.getValue()));
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(ChangeListener<? super T> listener) {
        boolean status = eventBus.subscribe(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        eventBus.unsubscribe(listener);
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
    public Class<T> getTypeClass() {
        return typeClass;
    }
    
    /**
     * A change listener that reacts to changes in a parent value to pass down to a read only property
     *
     * @param <T> The value type
     */
    protected static class BindListener<T> implements ChangeListener<T> {
        
        private AbstractReadOnlyProperty<T> property;
        
        public BindListener(AbstractReadOnlyProperty<T> property) {
            this.property = property;
        }
        
        @Override
        public void changed(ChangeEvent<T> event) {
            property.eventBus.post(event);
        }
    }
}