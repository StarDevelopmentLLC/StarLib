package com.stardevllc.starlib.values.observable;

public class ObservableShort extends AbstractObservableValue<Short> {
    
    private short value;
    
    public ObservableShort() {}
    
    public ObservableShort(short value) {
        this.value = value;
    }
    
    public void set(short value) {
        checkValid();
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Short value) {
        set(value);
    }
    
    public short get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public Short getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableShort that)) {
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
        return "ObservableShort{" +
                "value=" + value +
                '}';
    }
}
