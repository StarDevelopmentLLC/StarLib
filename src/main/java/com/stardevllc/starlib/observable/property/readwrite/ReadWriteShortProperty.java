package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.ReadWriteProperty;
import com.stardevllc.starlib.observable.WritableProperty;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyShortProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableShort;

/**
 * Represents a Read-Write Integer value with an identity
 */
public class ReadWriteShortProperty extends ReadOnlyShortProperty implements ReadWriteProperty<Short>, WritableObservableShort {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteShortProperty(Object bean, String name, short value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteShortProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteShortProperty(short value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteShortProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(short value) {
        short oldValue = this.value;
        if (!this.handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<Short> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Short> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<Short> other) {
        BidirectionalBindListener<Short> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyShortProperty asReadOnly() {
        ReadOnlyShortProperty property = new ReadOnlyShortProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
