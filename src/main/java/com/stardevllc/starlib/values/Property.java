package com.stardevllc.starlib.values;

import com.stardevllc.starlib.objects.Nameable;

public interface Property<T> extends ObservableValue<T>, Nameable {
    Object getBean();
    
    Class<T> getTypeClass();
    
    void bind(ObservableValue<T> other);
    
    void unbind();
    
    boolean isBound();
}