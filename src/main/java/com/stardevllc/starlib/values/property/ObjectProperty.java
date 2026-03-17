package com.stardevllc.starlib.values.property;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ObjectProperty<T> extends AbstractProperty<T> {
    
    private T value;
    
    public ObjectProperty(Class<T> typeClass) {
        super(typeClass);
    }
    
    public ObjectProperty(@NotNull T value) {
        super((Class<T>) value.getClass());
        this.value = value;
    }
    
    public ObjectProperty(Object bean, String name, Class<T> typeClass) {
        super(bean, typeClass, name);
    }
    
    public ObjectProperty(Object bean, String name, @NotNull T value) {
        super(bean, (Class<T>) value.getClass(), name);
        this.value = value;
    }
    
    public void set(T value) {
        checkValid();
        if (isBound()) {
            return;
        }
        
        if (!Objects.equals(this.value, value)) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(T value) {
        set(value);
    }
    
    public T get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public T getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof ObjectProperty<?> that)) {
            return false;
        }
        
        return Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Objects.hashCode(value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "ObjectProperty{" +
                "value=" + value +
                '}';
    }
}
