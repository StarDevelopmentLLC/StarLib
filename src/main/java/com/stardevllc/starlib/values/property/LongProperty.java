package com.stardevllc.starlib.values.property;

public class LongProperty extends AbstractProperty<Long> {
    
    private long value;
    
    public LongProperty() {
        super(long.class);
    }
    
    public LongProperty(long value) {
        super(long.class);
        this.value = value;
    }
    
    public LongProperty(Object bean, String name) {
        super(bean, long.class, name);
    }
    
    public LongProperty(Object bean, String name, long value) {
        super(bean, long.class, name);
        this.value = value;
    }
    
    public void set(long value) {
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
    public void setValue(Long value) {
        set(value);
    }
    
    public long get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public Long getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof LongProperty that)) {
            return false;
        }
        
        return value == that.value;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Long.hashCode(this.value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "LongProperty{" +
                "value=" + value +
                '}';
    }
}
