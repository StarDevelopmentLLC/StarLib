package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyIntegerProperty;
import com.stardevllc.starlib.observable.writable.WritableIntegerValue;

public class ReadWriteIntegerProperty extends ReadOnlyIntegerProperty implements ReadWriteProperty<Integer>, WritableIntegerValue {
    
    public ReadWriteIntegerProperty(Object bean, String name, int value) {
        super(bean, name, value);
    }
    
    public ReadWriteIntegerProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public ReadWriteIntegerProperty(int value) {
        super(value);
    }
    
    public ReadWriteIntegerProperty() {
    }
    
    @Override
    public void set(int value) {
        int oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<Integer> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Integer> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<Integer> other) {
        BidirectionalBindListener<Integer> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyIntegerProperty asReadOnly() {
        ReadOnlyIntegerProperty property = new ReadOnlyIntegerProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
