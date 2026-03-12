package com.stardevllc.starlib.values.property;

public class IntProperty extends AbstractProperty<Integer> {
    
    private int value;
    
    public IntProperty() {
        super(int.class);
    }
    
    public IntProperty(int value) {
        super(int.class);
        this.value = value;
    }
    
    public IntProperty(Object bean, String name) {
        super(bean, int.class, name);
    }
    
    public IntProperty(Object bean, String name, int value) {
        super(bean, int.class, name);
        this.value = value;
    }
    
    public void set(int value) {
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
    public void setValue(Integer value) {
        set(value);
    }
    
    public int get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public Integer getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof IntProperty that)) {
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
        return "IntProperty{" +
                "value=" + value +
                '}';
    }
}
