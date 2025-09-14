package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyBooleanProperty;
import com.stardevllc.starlib.observable.writable.WritableBooleanValue;

public class ReadWriteBooleanProperty extends ReadOnlyBooleanProperty implements ReadWriteProperty<Boolean>, WritableBooleanValue {
    
    public ReadWriteBooleanProperty(Object bean, String name, boolean value) {
        super(bean, name, value);
    }
    
    public ReadWriteBooleanProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public ReadWriteBooleanProperty(boolean value) {
        super(value);
    }
    
    public ReadWriteBooleanProperty() {
    }
    
    @Override
    public void set(boolean value) {
        boolean oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<Boolean> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Boolean> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<Boolean> other) {
        BidirectionalBindListener<Boolean> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyBooleanProperty asReadOnly() {
        ReadOnlyBooleanProperty property = new ReadOnlyBooleanProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
