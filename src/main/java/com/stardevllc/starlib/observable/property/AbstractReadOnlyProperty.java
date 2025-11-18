package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.*;

import java.util.Objects;

/**
 * A common abstract class for read only properties to make things easier and smaller
 *
 * @param <T> The object type
 */
public abstract class AbstractReadOnlyProperty<T> implements ReadOnlyProperty<T> {
    
    /**
     * The owner of the property
     */
    protected final Object bean;
    
    /**
     * The name of the property
     */
    protected final String name;
    
    /**
     * The class type for the value of the property
     */
    protected final Class<T> typeClass;
    
    /**
     * The EventBus for change listener handling
     */
    protected final ListenerHandler<T> handler = new ListenerHandler<>();
    
    /**
     * The value that this property is bound to
     */
    protected ObservableValue<T> boundValue;
    
    /**
     * The listener attached to the bound value
     */
    protected BindListener<T> bindListener;
    
    /**
     * Constructs a new AbstractReadOnlyProperty
     *
     * @param bean      The owner bean
     * @param name      The name of the property
     * @param typeClass The type class
     */
    public AbstractReadOnlyProperty(Object bean, String name, Class<T> typeClass) {
        this.bean = bean;
        this.name = name;
        this.typeClass = typeClass;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ListenerHandler<T> getHandler() {
        return handler;
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
            this.handler.handleChange(this, oldValue, newValue);
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
            this.handler.handleChange(this, value, this.getValue());
        }
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
        
        /**
         * Constructs a new bind listeners
         *
         * @param property The property that owns the listener
         */
        public BindListener(AbstractReadOnlyProperty<T> property) {
            this.property = property;
        }
        
        @Override
        public void changed(Change<T> change) {
            property.getHandler().handleChange(change);
        }
    }
}