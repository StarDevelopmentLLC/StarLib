package com.stardevllc.property;

import com.stardevllc.observable.writable.WritableNumberValue;

public abstract class NumberProperty<N extends Number> extends AbstractProperty<N> implements WritableNumberValue<N> {
    
    public NumberProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public NumberProperty(String name) {
        this(null, name);
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }
}