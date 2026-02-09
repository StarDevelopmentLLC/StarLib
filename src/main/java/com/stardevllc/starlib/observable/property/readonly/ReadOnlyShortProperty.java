package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableShort;

/**
 * Represents a Read-Only Byte value with an identity
 */
public class ReadOnlyShortProperty extends AbstractReadOnlyProperty<Short> implements ObservableShort {
    
    /**
     * The value of the property
     */
    protected short value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyShortProperty(Object bean, String name, short value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyShortProperty(Object bean, String name) {
        super(bean, name, Short.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyShortProperty(short value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyShortProperty() {
        this(null, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public short get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
