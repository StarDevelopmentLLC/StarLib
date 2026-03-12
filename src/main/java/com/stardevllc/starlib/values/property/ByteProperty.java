package com.stardevllc.starlib.values.property;

public class ByteProperty extends AbstractProperty<Byte> {
    
    private byte value;
    
    public ByteProperty() {
        super(byte.class);
    }
    
    public ByteProperty(byte value) {
        super(byte.class);
        this.value = value;
    }
    
    public ByteProperty(Object bean, String name) {
        super(bean, byte.class, name);
    }
    
    public ByteProperty(Object bean, String name, byte value) {
        super(bean, byte.class, name);
        this.value = value;
    }
    
    public void set(byte value) {
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
    public void setValue(Byte value) {
        set(value);
    }
    
    public byte get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public Byte getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ByteProperty that)) {
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
        return "ByteProperty{" +
                "value=" + value +
                '}';
    }
}
