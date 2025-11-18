package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyLongProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableLong;

/**
 * Represents a Read-Write Long value with an identity
 */
public class ReadWriteLongProperty extends ReadOnlyLongProperty implements ReadWriteProperty<Long>, WritableObservableLong {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteLongProperty(Object bean, String name, long value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteLongProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteLongProperty(long value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteLongProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(long value) {
        long oldValue = this.value;
        if (this.handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<Long> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Long> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<Long> other) {
        BidirectionalBindListener<Long> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyLongProperty asReadOnly() {
        ReadOnlyLongProperty property = new ReadOnlyLongProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
