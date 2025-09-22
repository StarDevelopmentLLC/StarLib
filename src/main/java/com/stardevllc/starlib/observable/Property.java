package com.stardevllc.starlib.observable;

/**
 * Represents an Object that has an Identity
 *
 * @param <T> The value type
 */
public interface Property<T> extends Observable {
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
     * Gets the Class object that represents the value of this property
     *
     * @return The class object of the value
     */
    Class<T> getTypeClass();
}