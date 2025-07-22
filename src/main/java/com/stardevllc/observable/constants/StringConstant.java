package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableStringValue;

/**
 * Represents a constant Boolean value
 */
public class StringConstant extends ObjectConstant<String> implements ObservableStringValue {
    /**
     * Constructs a new Boolean constant with the provided value
     *
     * @param object The value to set the constant to
     */
    public StringConstant(String object) {
        super(object);
    }
}
