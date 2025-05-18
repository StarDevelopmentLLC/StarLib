package com.stardevllc.converter.string;

import java.util.HashMap;
import java.util.Map;

public final class StringConverters {
    private static final Map<Class<?>, StringConverter<?>> converters = new HashMap<>();
    
    static {
        new BooleanStringConverter();
        new ByteStringConverter();
        new CharacterStringConverter();
        new DoubleStringConverter();
        new FloatStringConverter();
        new IntegerStringConverter();
        new LongStringConverter();
        new ShortStringConverter();
        new UUIDStringConverter();
    }
    
    public static void addConverter(Class<?> clazz, StringConverter<?> converter) {
        converters.put(clazz, converter);
    }
    
    public static <T> StringConverter<T> getConverter(Class<T> clazz) {
        return (StringConverter<T>) converters.get(clazz);
    }
    
    public static <T> StringConverter<T> getConverter(T object) {
        return (StringConverter<T>) getConverter(object.getClass());
    }
}