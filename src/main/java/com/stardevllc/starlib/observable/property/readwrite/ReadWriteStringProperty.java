package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyStringProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableString;

/**
 * Represents a Read-Write String value with an identity
 */
public class ReadWriteStringProperty extends ReadOnlyStringProperty implements ReadWriteProperty<String>, WritableObservableString {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteStringProperty(Object bean, String name, String value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteStringProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteStringProperty(String value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteStringProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(String value) {
        String oldValue = this.value;
        this.handler.handleChange(this, oldValue, value);
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<String> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<String> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<String> other) {
        BidirectionalBindListener<String> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyStringProperty asReadOnly() {
        ReadOnlyStringProperty property = new ReadOnlyStringProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}