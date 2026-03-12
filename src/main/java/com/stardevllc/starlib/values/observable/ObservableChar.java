package com.stardevllc.starlib.values.observable;

public class ObservableChar extends AbstractObservableValue<Character> {
    
    private char value;
    
    public ObservableChar() {}
    
    public ObservableChar(char value) {
        this.value = value;
    }
    
    public void set(char value) {
        checkValid();
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
        return this.value;
    }
    
    @Override
    public Character getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableChar that)) {
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
        return "ObservableChar{" +
                "value=" + value +
                '}';
    }
}