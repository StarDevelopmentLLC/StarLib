package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.ChangeEvent;
import com.stardevllc.starlib.observable.writable.WritableCharacterValue;

public class CharacterProperty extends AbstractProperty<Character> implements WritableCharacterValue {
    
    protected char value;
    
    public CharacterProperty(Object bean, String name, char value) {
        super(bean, name);
        this.value = value;
    }
    
    public CharacterProperty(String name, char value) {
        this(null, name, value);
    }
    
    public CharacterProperty(char value) {
        this(null, null, value);
    }
    
    public CharacterProperty() {
        this(null, null, '\u0000');
    }

    @Override
    public Class<Character> getTypeClass() {
        return Character.class;
    }

    @Override
    public void set(char newValue) {
        if (boundValue != null) {
            return;
        }
        
        char oldValue = this.value;
        this.value = newValue;
        if (oldValue != newValue) {
            eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public char get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
