package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableCharacterValue;

public class ReadOnlyCharacterProperty extends AbstractReadOnlyProperty<Character> implements ObservableCharacterValue {
    
    protected char value;
    
    public ReadOnlyCharacterProperty(Object bean, String name, char value) {
        this(bean, name);
        this.value = value;
    }
    
    public ReadOnlyCharacterProperty(Object bean, String name) {
        super(bean, name, Character.class);
    }
    
    public ReadOnlyCharacterProperty(char value) {
        this(null, null, value);
    }
    
    public ReadOnlyCharacterProperty() {
        this(null, null);
    }
    
    @Override
    public char get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}