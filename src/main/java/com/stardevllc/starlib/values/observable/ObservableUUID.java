package com.stardevllc.starlib.values.observable;

import java.util.Objects;
import java.util.UUID;

public class ObservableUUID extends AbstractObservableValue<UUID> {
    
    private UUID value;
    
    public ObservableUUID() {}
    
    public ObservableUUID(UUID value) {
        this.value = value;
    }
    
    public void set(UUID value) {
        checkValid();
        if (!Objects.equals(this.value, value)) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(UUID value) {
        set(value);
    }
    
    public UUID get() {
        checkValid();
        return this.value;
    }
    
    @Override
    public UUID getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObservableUUID that)) {
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
        return "ObservableUUID{" +
                "value=" + value +
                '}';
    }
}
