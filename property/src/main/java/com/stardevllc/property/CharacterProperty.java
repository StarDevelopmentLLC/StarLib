package com.stardevllc.property;

import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.writable.WritableCharacterValue;

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
    public void set(char newValue) {
        char oldValue = this.value;
        this.value = newValue;
        if (oldValue != newValue) {
            eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public void setValue(Character character) {
        set(character);
    }

    @Override
    public char get() {
        return value;
    }

    @Override
    public Character getValue() {
        return get();
    }
}
