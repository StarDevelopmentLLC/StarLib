package com.stardevllc.converter;

import java.util.Map;

public interface Converter<F, T> {
    T convertFrom(F fromObject);
    F convertTo(T toObject);
    
    Map<Class<?>, Class<? extends Converter<?, ?>>> getConverters();
    
    static Converter<?, ?> getConverter(Class<?> clazz) {
        return null;    
    }
}