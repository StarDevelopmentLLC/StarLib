package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableCharacter;

/**
 * Represents a Read-Only Character value with an identity
 */
public class ReadOnlyCharacterProperty extends AbstractReadOnlyProperty<Character> implements ObservableCharacter {
    
    /**
     * The value of the property
     */
    protected char value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyCharacterProperty(Object bean, String name, char value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyCharacterProperty(Object bean, String name) {
        super(bean, name, Character.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyCharacterProperty(char value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyCharacterProperty() {
        this(null, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public char get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}