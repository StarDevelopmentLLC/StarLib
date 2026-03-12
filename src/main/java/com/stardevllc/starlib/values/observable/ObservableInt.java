package com.stardevllc.starlib.values.observable;

public class ObservableInt extends AbstractObservableValue<Integer> {
    
    private int value;
    
    public ObservableInt() {}
    
    public ObservableInt(int value) {
        this.value = value;
    }
    
    public void set(int value) {
        checkValid();
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Integer value) {
        set(value);
    }
    
    public int get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public Integer getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableInt that)) {
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
        return "ObservableInt{" +
                "value=" + value +
                '}';
    }
}
