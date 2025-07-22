package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableLongValue;

public class LongConstant extends NumberConstant<Long> implements ObservableLongValue {
    
    private final long value;
    public LongConstant(final long value) {
        this.value = value;
    }
    
    @Override
    public long get() {
        return value;
    }
}
