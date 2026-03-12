package com.stardevllc.starlib.values.observable;

public class ObservableDouble extends AbstractObservableValue<Double> {
    
    private double value;
    
    public ObservableDouble() {}
    
    public ObservableDouble(double value) {
        this.value = value;
    }
    
    public void set(double value) {
        checkValid();
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Double value) {
        set(value);
    }
    
    public double get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public Double getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableDouble that)) {
            return false;
        }
        
        return Double.compare(value, that.value) == 0;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Double.hashCode(value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "ObservableDouble{" +
                "value=" + value +
                '}';
    }
}
