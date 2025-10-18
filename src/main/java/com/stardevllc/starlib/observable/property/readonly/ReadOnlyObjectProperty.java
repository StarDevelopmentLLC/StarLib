package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableObject;

/**
 * Represents a Read-Only Object value with an identity
 *
 * @param <T> The object type
 */
public class ReadOnlyObjectProperty<T> extends AbstractReadOnlyProperty<T> implements ObservableObject<T> {
    
    /**
     * The value of the property
     */
    protected T value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyObjectProperty(Object bean, String name, T value) {
        this(bean, name, (Class<T>) value.getClass());
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param clazz The class of the value
     */
    public ReadOnlyObjectProperty(Object bean, String name, Class<T> clazz) {
        super(bean, name, clazz);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyObjectProperty(T value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param clazz The class of the value
     */
    public ReadOnlyObjectProperty(Class<T> clazz) {
        this(null, null, clazz);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public T get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
