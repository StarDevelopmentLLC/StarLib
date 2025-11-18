package com.stardevllc.starlib.observable.property.readwrite;

import com.stardevllc.starlib.observable.*;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyObjectProperty;
import com.stardevllc.starlib.observable.writable.WritableObservableObject;

/**
 * Represents a Read-Write Object value with an identity
 *
 * @param <T> The value type
 */
public class ReadWriteObjectProperty<T> extends ReadOnlyObjectProperty<T> implements ReadWriteProperty<T>, WritableObservableObject<T> {
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadWriteObjectProperty(Object bean, String name, T value) {
        super(bean, name, value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param clazz The class of the value
     */
    public ReadWriteObjectProperty(Object bean, String name, Class<T> clazz) {
        super(bean, name, clazz);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param value The value
     */
    public ReadWriteObjectProperty(T value) {
        super(value);
    }
    
    /**
     * Constructs a ReadWrite Property
     *
     * @param clazz The class of the value
     */
    public ReadWriteObjectProperty(Class<T> clazz) {
        super(clazz);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(T value) {
        T oldValue = this.value;
        if (!this.handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectionally(WritableProperty<T> other) {
        if (other == null) {
            return;
        }
        
        BidirectionalBindListener<T> listener = new BidirectionalBindListener<>(this, other);
        other.addListener(listener);
        addListener(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectionally(WritableProperty<T> other) {
        BidirectionalBindListener<T> cl = new BidirectionalBindListener<>(this, other);
        other.addListener(cl);
        addListener(cl);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyObjectProperty<T> asReadOnly() {
        ReadOnlyObjectProperty<T> property = new ReadOnlyObjectProperty<>(this.bean, this.name, this.typeClass);
        property.bind(this);
        return property;
    }
}