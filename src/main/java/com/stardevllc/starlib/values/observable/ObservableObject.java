package com.stardevllc.starlib.values.observable;

import java.util.Objects;

public class ObservableObject<T> extends AbstractObservableValue<T> {
    
    private T value;
    
    public ObservableObject() {}
    
    public ObservableObject(T value) {
        this.value = value;
    }
    
    public void set(T value) {
        checkValid();
        if (!Objects.equals(this.value, value)) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(T value) {
        set(value);
    }
    
    public T get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public T getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableObject<?> that)) {
            return false;
        }
        
        return Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Objects.hashCode(value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "ObservableObject{" +
                "value=" + value +
                '}';
    }
}
