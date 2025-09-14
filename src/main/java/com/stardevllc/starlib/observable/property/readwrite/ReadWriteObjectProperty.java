package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyObjectProperty;
import com.stardevllc.starlib.observable.writable.WritableObjectValue;

public class ReadWriteObjectProperty<T> extends ReadOnlyObjectProperty<T> implements ReadWriteProperty<T>, WritableObjectValue<T> {
    
    public ReadWriteObjectProperty(Object bean, String name, T value) {
        super(bean, name, value);
    }
    
    public ReadWriteObjectProperty(Object bean, String name, Class<T> clazz) {
        super(bean, name, clazz);
    }
    
    public ReadWriteObjectProperty(T value) {
        super(value);
    }
    
    public ReadWriteObjectProperty(Class<T> clazz) {
        super(clazz);
    }
    
    @Override
    public void set(T value) {
        T oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<T> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<T> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<T> other) {
        BidirectionalBindListener<T> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyObjectProperty<T> asReadOnly() {
        ReadOnlyObjectProperty<T> property = new ReadOnlyObjectProperty<>(this.bean, this.name, this.typeClass);
        property.bind(this);
        return property;
    }
}