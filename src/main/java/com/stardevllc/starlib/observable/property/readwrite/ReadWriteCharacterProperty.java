package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyCharacterProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableCharacter;

/**
 * Represents a Read-Write Character value with an identity
 */
public class ReadWriteCharacterProperty extends ReadOnlyCharacterProperty implements ReadWriteProperty<Character>, WritableObservableCharacter {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteCharacterProperty(Object bean, String name, char value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     */
    public ReadWriteCharacterProperty(Object bean, String name) {
        super(bean, name);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteCharacterProperty(char value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     */
    public ReadWriteCharacterProperty() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(char value) {
        char oldValue = this.value;
        if (!this.handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<Character> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<Character> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<Character> other) {
        BidirectionalBindListener<Character> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyCharacterProperty asReadOnly() {
        ReadOnlyCharacterProperty property = new ReadOnlyCharacterProperty(this.bean, this.name);
        property.bind(this);
        return property;
    }
}
