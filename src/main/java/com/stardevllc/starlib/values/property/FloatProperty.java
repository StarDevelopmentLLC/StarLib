package com.stardevllc.starlib.values.property;

public class FloatProperty extends AbstractProperty<Float> {
    
    private float value;
    
    public FloatProperty() {
        super(float.class);
    }
    
    public FloatProperty(float value) {
        super(float.class);
        this.value = value;
    }
    
    public FloatProperty(Object bean, String name) {
        super(bean, float.class, name);
    }
    
    public FloatProperty(Object bean, String name, float value) {
        super(bean, float.class, name);
        this.value = value;
    }
    
    public void set(float value) {
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
    public void setValue(Float value) {
        set(value);
    }
    
    public float get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public Float getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof FloatProperty that)) {
            return false;
        }
        
        return value == that.value;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Float.hashCode(this.value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "FloatProperty{" +
                "value=" + value +
                '}';
    }
}
