package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableInteger;

/**
 * Represents a Read-Only Integer value with an identity
 */
public class ReadOnlyIntegerProperty extends AbstractReadOnlyProperty<Integer> implements ObservableInteger {
    
    /**
     * The value of the property
     */
    protected int value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyIntegerProperty(Object bean, String name, int value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyIntegerProperty(Object bean, String name) {
        super(bean, name, Integer.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyIntegerProperty(int value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyIntegerProperty() {
        this(null, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
