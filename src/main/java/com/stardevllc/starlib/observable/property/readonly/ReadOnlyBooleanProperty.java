package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableBoolean;

/**
 * Represents a Read-Only Boolean value with an identity
 */
public class ReadOnlyBooleanProperty extends AbstractReadOnlyProperty<Boolean> implements ObservableBoolean {
    
    /**
     * The value of the property
     */
    protected boolean value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyBooleanProperty(Object bean, String name, boolean value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyBooleanProperty(Object bean, String name) {
        super(bean, name, Boolean.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyBooleanProperty(boolean value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyBooleanProperty() {
        this(false);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
