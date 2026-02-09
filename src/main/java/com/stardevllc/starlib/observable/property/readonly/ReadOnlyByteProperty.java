package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableByte;

/**
 * Represents a Read-Only Byte value with an identity
 */
public class ReadOnlyByteProperty extends AbstractReadOnlyProperty<Byte> implements ObservableByte {
    
    /**
     * The value of the property
     */
    protected byte value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyByteProperty(Object bean, String name, byte value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyByteProperty(Object bean, String name) {
        super(bean, name, Byte.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyByteProperty(byte value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyByteProperty() {
        this(null, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public byte get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
