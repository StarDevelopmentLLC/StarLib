package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyBooleanProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableBoolean;

/**
 * Represents a Read-Write Boolean value with an identity
 */
public class ReadWriteBooleanProperty extends ReadOnlyBooleanProperty implements ReadWriteProperty<Boolean>, WritableObservableBoolean {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteBooleanProperty(Object bean, String name, boolean value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadWriteBooleanProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteBooleanProperty(boolean value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteBooleanProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(boolean value) {
        boolean oldValue = this.value;
        this.handler.handleChange(this, oldValue, value);
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<Boolean> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Boolean> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<Boolean> other) {
        BidirectionalBindListener<Boolean> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyBooleanProperty asReadOnly() {
        ReadOnlyBooleanProperty property = new ReadOnlyBooleanProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
