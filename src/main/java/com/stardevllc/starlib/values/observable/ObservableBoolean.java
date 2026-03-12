package com.stardevllc.starlib.values.observable;

public class ObservableBoolean extends AbstractObservableValue<Boolean> {
    
    private boolean value;
    
    public ObservableBoolean() {}
    
    public ObservableBoolean(boolean value) {
        this.value = value;
    }
    
    public void set(boolean value) {
        checkValid();
        
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Boolean value) {
        set(value);
    }
    
    public boolean get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public Boolean getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableBoolean that)) {
            return false;
        }
        
        return value == that.value;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Boolean.hashCode(value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "ObservableBoolean{" +
                "value=" + value +
                '}';
    }
}