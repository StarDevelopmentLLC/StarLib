package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableStringValue;

public class SimpleStringValue extends SimpleObjectValue<String> implements WritableStringValue {
    public SimpleStringValue() {
    }
    
    public SimpleStringValue(String value) {
        super(value);
    }
}
