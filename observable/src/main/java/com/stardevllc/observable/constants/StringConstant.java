package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableStringValue;

public class StringConstant extends ObjectConstant<String> implements ObservableStringValue {
    public StringConstant(String object) {
        super(object);
    }
}
