package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.writable.WritableNumberValue;

public abstract class NumberProperty<N extends Number> extends AbstractProperty<N> implements WritableNumberValue<N> {
    
    public NumberProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public NumberProperty(String name) {
        this(null, name);
    }
}