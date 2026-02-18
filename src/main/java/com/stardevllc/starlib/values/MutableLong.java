package com.stardevllc.starlib.values;

public class MutableLong implements MutableValue<Long> {
    
    public static MutableLong of() {
        return new MutableLong();
    }
    
    public static MutableLong of(long value) {
        return new MutableLong(value);
    }
    
    protected long value;
    
    public MutableLong() {}
    
    public MutableLong(long value) {
        this.value = value;
    }
    
    public void set(long value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Long value) {
        set(value);
    }
    
    public long get() {
        return value;
    }
    
    @Override
    public Long getValue() {
        return get();
    }
}
