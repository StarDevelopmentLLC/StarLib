package com.stardevllc.starlib.values.observable;

import java.util.Objects;

public class ObservableString extends AbstractObservableValue<String> {
    
    private String value;
    
    public ObservableString() {}
    
    public ObservableString(String value) {
        this.value = value;
    }
    
    public void set(String value) {
        checkValid();
        if (!Objects.equals(this.value, value)) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(String value) {
        set(value);
    }
    
    public String get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public String getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableString that)) {
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
        return "ObservableString{" +
                "value='" + value + '\'' +
                '}';
    }
}
