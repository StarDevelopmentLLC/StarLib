package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyLongProperty;
import com.stardevllc.starlib.observable.writable.WritableLongValue;

public class ReadWriteLongProperty extends ReadOnlyLongProperty implements ReadWriteProperty<Long>, WritableLongValue {
    
    public ReadWriteLongProperty(Object bean, String name, long value) {
        super(bean, name, value);
    }
    
    public ReadWriteLongProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public ReadWriteLongProperty(long value) {
        super(value);
    }
    
    public ReadWriteLongProperty() {
    }
    
    @Override
    public void set(long value) {
        long oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<Long> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Long> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<Long> other) {
        BidirectionalBindListener<Long> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyLongProperty asReadOnly() {
        ReadOnlyLongProperty property = new ReadOnlyLongProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
