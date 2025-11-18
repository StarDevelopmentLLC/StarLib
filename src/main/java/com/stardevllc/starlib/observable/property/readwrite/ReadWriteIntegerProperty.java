package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyIntegerProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableInteger;

/**
 * Represents a Read-Write Integer value with an identity
 */
public class ReadWriteIntegerProperty extends ReadOnlyIntegerProperty implements ReadWriteProperty<Integer>, WritableObservableInteger {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteIntegerProperty(Object bean, String name, int value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteIntegerProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteIntegerProperty(int value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteIntegerProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(int value) {
        int oldValue = this.value;
        if (!this.handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<Integer> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Integer> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<Integer> other) {
        BidirectionalBindListener<Integer> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyIntegerProperty asReadOnly() {
        ReadOnlyIntegerProperty property = new ReadOnlyIntegerProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
