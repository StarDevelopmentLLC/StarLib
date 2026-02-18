package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableStringValue;

@Deprecated(since = "0.24.0")
public class SimpleStringValue extends SimpleObjectValue<String> implements WritableStringValue {
    public SimpleStringValue() {
    }
    
    public SimpleStringValue(String value) {
        super(value);
    }
}
