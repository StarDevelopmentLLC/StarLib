package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyCharacterProperty;
import com.stardevllc.starlib.observable.writable.WritableCharacterValue;

public class ReadWriteCharacterProperty extends ReadOnlyCharacterProperty implements ReadWriteProperty<Character>, WritableCharacterValue {
    
    public ReadWriteCharacterProperty(Object bean, String name, char value) {
        super(bean, name, value);
    }
    
    public ReadWriteCharacterProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public ReadWriteCharacterProperty(char value) {
        super(value);
    }
    
    public ReadWriteCharacterProperty() {
    }
    
    @Override
    public void set(char value) {
        char oldValue = this.value;
        this.eventBus.post(new ChangeEvent<>(this, oldValue, value));
        this.value = value;
    }
    
    @Override
    public void bindBidirectionally(WritableProperty<Character> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Character> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    @Override
    public void unbindBidirectionally(WritableProperty<Character> other) {
        BidirectionalBindListener<Character> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    @Override
    public ReadOnlyCharacterProperty asReadOnly() {
        ReadOnlyCharacterProperty property = new ReadOnlyCharacterProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
