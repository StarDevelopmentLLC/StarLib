package com.stardevllc.starlib.values.property;

public class BooleanProperty extends AbstractProperty<Boolean> {
    
    private boolean value;
    
    public BooleanProperty() {
        super(boolean.class);
    }
    
    public BooleanProperty(boolean value) {
        super(boolean.class);
        this.value = value;
    }
    
    public BooleanProperty(Object bean, String name) {
        super(bean, boolean.class, name);
    }
    
    public BooleanProperty(Object bean, String name, boolean value) {
        super(bean, boolean.class, name);
        this.value = value;
    }
    
    public void set(boolean value) {
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
    public void setValue(Boolean value) {
        set(value);
    }
    
    public boolean get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public Boolean getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof BooleanProperty that)) {
            return false;
        }
        
        return value == that.value;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Boolean.hashCode(value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "BooleanProperty{" +
                "value=" + value +
                '}';
    }
}
