package com.stardevllc.property;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.ChangeListener.ChangeEvent;
import com.stardevllc.observable.Property;
import com.stardevllc.observable.writable.WritableCharacterValue;

public class CharacterProperty implements Property<Character>, WritableCharacterValue {
    
    protected final Object bean;
    protected final String name;
    
    protected char value;
    
    protected final EventBus eventBus = new SimpleEventBus();

    public CharacterProperty(Object bean, String name, char value) {
        this.bean = bean;
        this.name = name;
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
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
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
    public void addListener(ChangeListener<? super Character> changeListener) {
        this.eventBus.registerListener(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super Character> changeListener) {
        this.eventBus.removeListener(changeListener);
    }

    @Override
    public Character getValue() {
        return get();
    }
}
