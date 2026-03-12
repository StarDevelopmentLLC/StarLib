package com.stardevllc.starlib.values.property;

public class CharProperty extends AbstractProperty<Character> {
    
    private char value;
    
    public CharProperty() {
        super(char.class);
    }
    
    public CharProperty(char value) {
        super(char.class);
        this.value = value;
    }
    
    public CharProperty(Object bean, String name) {
        super(bean, char.class, name);
    }
    
    public CharProperty(Object bean, String name, char value) {
        super(bean, char.class, name);
        this.value = value;
    }
    
    public void set(char value) {
        checkValid();
        if (isBound()) {
            return;
        }
        
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Character value) {
        set(value);
    }
    
    public char get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public Character getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof CharProperty that)) {
            return false;
        }
        
        return value == that.value;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return value;
    }
    
    @Override
    public String toString() {
        checkValid();
        return "CharProperty{" +
                "value=" + value +
                '}';
    }
}
