package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableFloat;

/**
 * Represents a Read-Only Float value with an identity
 */
public class ReadOnlyFloatProperty extends AbstractReadOnlyProperty<Float> implements ObservableFloat {
    
    /**
     * The value of the property
     */
    protected float value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyFloatProperty(Object bean, String name, float value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyFloatProperty(Object bean, String name) {
        super(bean, name, Float.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyFloatProperty(float value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyFloatProperty() {
        this(null, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
