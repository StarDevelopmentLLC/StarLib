package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyFloatProperty;
import com.stardevllc.starlib.observable.writable.WritableFloatValue;

/**
 * Represents a Read-Write Float value with an identity
 */
public class ReadWriteFloatProperty extends ReadOnlyFloatProperty implements ReadWriteProperty<Float>, WritableFloatValue {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteFloatProperty(Object bean, String name, float value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteFloatProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteFloatProperty(float value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteFloatProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(float value) {
        float oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<Float> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Float> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<Float> other) {
        BidirectionalBindListener<Float> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyFloatProperty asReadOnly() {
        ReadOnlyFloatProperty property = new ReadOnlyFloatProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
