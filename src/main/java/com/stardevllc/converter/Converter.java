package com.stardevllc.converter;

//All converters must have a no-args constructor in order to allow creation
public interface Converter<F, T> {
    T convertFrom(Object fromObject);
    F convertTo(T toObject);
}