package com.stardevllc.starlib.values.observable;

public class ObservableLong extends AbstractObservableValue<Long> {
    
    private long value;
    
    public ObservableLong() {}
    
    public ObservableLong(long value) {
        this.value = value;
    }
    
    public void set(long value) {
        checkValid();
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Long value) {
        set(value);
    }
    
    public long get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public Long getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableLong that)) {
            return false;
        }
        
        return value == that.value;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Long.hashCode(value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "ObservableLong{" +
                "value=" + value +
                '}';
    }
}