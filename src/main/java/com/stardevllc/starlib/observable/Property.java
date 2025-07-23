package com.stardevllc.starlib.observable;

/**
 * Represents a WritableValue that has an identity
 *
 * @param <T> The value type
 */
public interface Property<T> extends WritableValue<T> {
    /**
     * The bean that owns this property
     *
     * @return The bean
     */
    Object getBean();
    
    /**
     * The name of this property
     *
     * @return The name
     */
    String getName();
    
    /**
     * Binds this property to the value of an ObservableValue
     *
     * @param other The value to bind to
     */
    void bind(ObservableValue<T> other);
    
    /**
     * Gets the Class object that represents the value of this property
     *
     * @return The class object of the value
     */
    Class<T> getTypeClass();
}