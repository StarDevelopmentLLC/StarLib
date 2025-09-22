package com.stardevllc.starlib.converter;

/**
 * An interface that defines the contract to convert between object types
 *
 * @param <F> The first object type
 * @param <T> The second object type
 */
public interface Converter<F, T> {
    /**
     * Converts from the passed object of the {@link F} type to the {@link T} type
     *
     * @param fromObject The object to convert
     * @return The converted value
     */
    T convertFrom(Object fromObject);
    
    /**
     * Reverse of {@link #convertFrom(Object)}
     *
     * @param toObject The object to convert
     * @return The converted value
     */
    F convertTo(T toObject);
}