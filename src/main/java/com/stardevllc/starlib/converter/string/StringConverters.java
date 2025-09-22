package com.stardevllc.starlib.converter.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to get a StringConverter without having to worry about cyclic class loading
 */
public final class StringConverters {
    private StringConverters() {}
    
    private static final Map<Class<?>, StringConverter<?>> converters = new HashMap<>();
    
    static {
        new BooleanStringConverter();
        new ByteStringConverter();
        new CharacterStringConverter();
        new CharSequenceStringConverter();
        new DoubleStringConverter();
        new DurationStringConverter();
        new FloatStringConverter();
        new IntegerStringConverter();
        new LongStringConverter();
        new ShortStringConverter();
        new UUIDStringConverter();
    }
    
    /**
     * Adds a converter
     *
     * @param clazz     the class type
     * @param converter The instance of the converter
     */
    public static void addConverter(Class<?> clazz, StringConverter<?> converter) {
        converters.put(clazz, converter);
    }
    
    /**
     * Gets a converter instance based on a class type
     *
     * @param clazz The class type
     * @param <T>   The object type
     * @return The converter instance
     */
    public static <T> StringConverter<T> getConverter(Class<T> clazz) {
        return (StringConverter<T>) converters.get(clazz);
    }
    
    /**
     * Gets a converter from an object instance
     *
     * @param object The object
     * @param <T>    The type
     * @return The converter instance
     */
    public static <T> StringConverter<T> getConverter(T object) {
        return (StringConverter<T>) getConverter(object.getClass());
    }
}