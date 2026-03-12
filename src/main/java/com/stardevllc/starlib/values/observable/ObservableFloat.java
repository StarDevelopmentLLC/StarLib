package com.stardevllc.starlib.values.observable;

public class ObservableFloat extends AbstractObservableValue<Float> {
    
    private float value;
    
    public ObservableFloat() {}
    
    public ObservableFloat(float value) {
        this.value = value;
    }
    
    public void set(float value) {
        checkValid();
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Float value) {
        set(value);
    }
    
    public float get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public Float getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableFloat that)) {
            return false;
        }
        
        return Float.compare(value, that.value) == 0;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Float.hashCode(value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "ObservableFloat{" +
                "value=" + value +
                '}';
    }
}