package com.stardevllc.starlib.values.property;

public class ShortProperty extends AbstractProperty<Short> {
    
    private short value;
    
    public ShortProperty() {
        super(short.class);
    }
    
    public ShortProperty(short value) {
        super(short.class);
        this.value = value;
    }
    
    public ShortProperty(Object bean, String name) {
        super(bean, short.class, name);
    }
    
    public ShortProperty(Object bean, String name, short value) {
        super(bean, short.class, name);
        this.value = value;
    }
    
    public void set(short value) {
        checkValid();
        if (isBound()) {
            return;
        }
        
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Short value) {
        set(value);
    }
    
    public short get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public Short getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ShortProperty that)) {
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
        return "ShortProperty{" +
                "value=" + value +
                '}';
    }
}
