package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableLongValue;

/**
 * Represents a Read-Only Long value with an identity
 */
public class ReadOnlyLongProperty extends AbstractReadOnlyProperty<Long> implements ObservableLongValue {
    
    /**
     * The value of the property
     */
    protected long value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyLongProperty(Object bean, String name, long value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyLongProperty(Object bean, String name) {
        super(bean, name, Long.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyLongProperty(long value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyLongProperty() {
        this(null, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
