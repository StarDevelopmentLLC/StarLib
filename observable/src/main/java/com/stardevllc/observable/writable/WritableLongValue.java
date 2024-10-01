package com.stardevllc.observable.writable;

import com.stardevllc.observable.value.ObservableLongValue;

public interface WritableLongValue extends ObservableLongValue, WritableNumberValue<Long> {
    void set(long value);
}