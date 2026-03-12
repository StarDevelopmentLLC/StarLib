package com.stardevllc.starlib.values.property;

import java.util.Objects;
import java.util.UUID;

public class UUIDProperty extends AbstractProperty<UUID> {
    
    private UUID value;
    
    public UUIDProperty() {
        super(UUID.class);
    }
    
    public UUIDProperty(UUID value) {
        super(UUID.class);
        this.value = value;
    }
    
    public UUIDProperty(Object bean, String name) {
        super(bean, UUID.class, name);
    }
    
    public UUIDProperty(Object bean, String name, UUID value) {
        super(bean, UUID.class, name);
        this.value = value;
    }
    
    public void set(UUID value) {
        checkValid();
        if (isBound()) {
            return;
        }
        
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
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public UUID getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof UUIDProperty that)) {
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
        return "UUIDProperty{" +
                "value=" + value +
                '}';
    }
}
