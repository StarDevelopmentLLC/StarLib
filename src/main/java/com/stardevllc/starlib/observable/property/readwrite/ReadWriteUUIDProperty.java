package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyUUIDProperty;
import com.stardevllc.starlib.observable.writable.WritableUUIDValue;

import java.util.UUID;

/**
 * Represents a Read-Write UUID value with an identity
 */
public class ReadWriteUUIDProperty extends ReadOnlyUUIDProperty implements ReadWriteProperty<UUID>, WritableUUIDValue {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteUUIDProperty(Object bean, String name, UUID value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteUUIDProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteUUIDProperty(UUID value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteUUIDProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(UUID value) {
        UUID oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<UUID> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<UUID> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<UUID> other) {
        BidirectionalBindListener<UUID> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyUUIDProperty asReadOnly() {
        ReadOnlyUUIDProperty property = new ReadOnlyUUIDProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}