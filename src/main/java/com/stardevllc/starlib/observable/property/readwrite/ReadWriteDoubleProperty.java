package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyDoubleProperty;
import com.stardevllc.starlib.observable.writable.WritableDoubleValue;

public class ReadWriteDoubleProperty extends ReadOnlyDoubleProperty implements ReadWriteProperty<Double>, WritableDoubleValue {
    
    public ReadWriteDoubleProperty(Object bean, String name, double value) {
        super(bean, name, value);
    }
    
    public ReadWriteDoubleProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public ReadWriteDoubleProperty(double value) {
        super(value);
    }
    
    public ReadWriteDoubleProperty() {
    }
    
    @Override
    public void set(double value) {
        double oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<Double> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Double> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<Double> other) {
        BidirectionalBindListener<Double> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyDoubleProperty asReadOnly() {
        ReadOnlyDoubleProperty property = new ReadOnlyDoubleProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
