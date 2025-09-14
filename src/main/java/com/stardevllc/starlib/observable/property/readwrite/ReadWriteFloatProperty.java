package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyFloatProperty;
import com.stardevllc.starlib.observable.writable.WritableFloatValue;

public class ReadWriteFloatProperty extends ReadOnlyFloatProperty implements ReadWriteProperty<Float>, WritableFloatValue {
    
    public ReadWriteFloatProperty(Object bean, String name, float value) {
        super(bean, name, value);
    }
    
    public ReadWriteFloatProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public ReadWriteFloatProperty(float value) {
        super(value);
    }
    
    public ReadWriteFloatProperty() {
    }
    
    @Override
    public void set(float value) {
        float oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<Float> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Float> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<Float> other) {
        BidirectionalBindListener<Float> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyFloatProperty asReadOnly() {
        ReadOnlyFloatProperty property = new ReadOnlyFloatProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
