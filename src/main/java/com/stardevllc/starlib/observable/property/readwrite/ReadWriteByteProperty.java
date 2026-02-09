package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.ReadWriteProperty;
import com.stardevllc.starlib.observable.WritableProperty;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyByteProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableByte;

/**
 * Represents a Read-Write Integer value with an identity
 */
public class ReadWriteByteProperty extends ReadOnlyByteProperty implements ReadWriteProperty<Byte>, WritableObservableByte {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteByteProperty(Object bean, String name, byte value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteByteProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteByteProperty(byte value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteByteProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(byte value) {
        byte oldValue = this.value;
        if (!this.handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<Byte> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Byte> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<Byte> other) {
        BidirectionalBindListener<Byte> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyByteProperty asReadOnly() {
        ReadOnlyByteProperty property = new ReadOnlyByteProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
