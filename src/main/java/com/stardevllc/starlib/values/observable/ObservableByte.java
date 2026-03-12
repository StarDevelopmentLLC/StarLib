package com.stardevllc.starlib.values.observable;

public class ObservableByte extends AbstractObservableValue<Byte> {
    
    private byte value;
    
    public ObservableByte() {}
    
    public ObservableByte(byte value) {
        this.value = value;
    }
    
    public void set(byte value) {
        checkValid();
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Byte value) {
        set(value);
    }
    
    public byte get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public Byte getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableByte that)) {
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
        return "ObservableByte{" +
                "value=" + value +
                '}';
    }
}
