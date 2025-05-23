package com.stardevllc.converter.string;

public class EnumStringConverter<T extends Enum<T>> implements StringConverter<T> {
    
    private Class<T> enumClass;
    
    public EnumStringConverter(Class<T> clazz) {
        this.enumClass = clazz;
    }
    
    @Override
    public String convertFrom(Object fromObject) {
        if (enumClass == fromObject.getClass()) {
            return ((T) fromObject).name();
        }
        return "";
    }
    
    @Override
    public T convertTo(String toObject) {
        return Enum.valueOf(enumClass, toObject.toUpperCase());
    }
}
