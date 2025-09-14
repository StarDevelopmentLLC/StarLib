package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyStringProperty;
import com.stardevllc.starlib.observable.writable.WritableStringValue;

public class ReadWriteStringProperty extends ReadOnlyStringProperty implements ReadWriteProperty<String>, WritableStringValue {
    
    public ReadWriteStringProperty(Object bean, String name, String value) {
        super(bean, name, value);
    }
    
    public ReadWriteStringProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public ReadWriteStringProperty(String value) {
        super(value);
    }
    
    public ReadWriteStringProperty() {
    }
    
    @Override
    public void set(String value) {
        String oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<String> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<String> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<String> other) {
        BidirectionalBindListener<String> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyStringProperty asReadOnly() {
        ReadOnlyStringProperty property = new ReadOnlyStringProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}