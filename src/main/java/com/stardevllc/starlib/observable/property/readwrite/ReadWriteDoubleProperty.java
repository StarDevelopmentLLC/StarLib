package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyDoubleProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableDouble;

/**
 * Represents a Read-Write Double value with an identity
 */
public class ReadWriteDoubleProperty extends ReadOnlyDoubleProperty implements ReadWriteProperty<Double>, WritableObservableDouble {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteDoubleProperty(Object bean, String name, double value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteDoubleProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteDoubleProperty(double value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteDoubleProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(double value) {
        double oldValue = this.value;
        this.handler.handleChange(this, oldValue, value);
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<Double> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Double> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<Double> other) {
        BidirectionalBindListener<Double> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyDoubleProperty asReadOnly() {
        ReadOnlyDoubleProperty property = new ReadOnlyDoubleProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
