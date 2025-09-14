package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyUUIDProperty;
import com.stardevllc.starlib.observable.writable.WritableUUIDValue;

import java.util.UUID;

public class ReadWriteUUIDProperty extends ReadOnlyUUIDProperty implements ReadWriteProperty<UUID>, WritableUUIDValue {
    
    public ReadWriteUUIDProperty(Object bean, String name, UUID value) {
        super(bean, name, value);
    }
    
    public ReadWriteUUIDProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public ReadWriteUUIDProperty(UUID value) {
        super(value);
    }
    
    public ReadWriteUUIDProperty() {
    }
    
    @Override
    public void set(UUID value) {
        UUID oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<UUID> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<UUID> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<UUID> other) {
        BidirectionalBindListener<UUID> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyUUIDProperty asReadOnly() {
        ReadOnlyUUIDProperty property = new ReadOnlyUUIDProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}