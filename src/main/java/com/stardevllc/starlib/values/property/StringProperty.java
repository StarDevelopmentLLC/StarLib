package com.stardevllc.starlib.values.property;

import java.util.Objects;

public class StringProperty extends AbstractProperty<String> {
    
    private String value;
    
    public StringProperty() {
        super(String.class);
    }
    
    public StringProperty(String value) {
        super(String.class);
        this.value = value;
    }
    
    public StringProperty(Object bean, String name) {
        super(bean, String.class, name);
    }
    
    public StringProperty(Object bean, String name, String value) {
        super(bean, String.class, name);
        this.value = value;
    }
    
    public void set(String value) {
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
    public void setValue(String value) {
        set(value);
    }
    
    public String get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public String getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof StringProperty that)) {
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
        return "StringProperty{" +
                "value=" + value +
                '}';
    }
}
