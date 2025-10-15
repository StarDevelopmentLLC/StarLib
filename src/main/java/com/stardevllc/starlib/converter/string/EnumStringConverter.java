package com.stardevllc.starlib.converter.string;

/**
 * Converts between Enums and Strings
 * @param <T> The enum type
 */
public class EnumStringConverter<T extends Enum<T>> implements StringConverter<T> {
    
    private Class<T> enumClass;
    
    /**
     * Constructs a new EnumStringConverter
     * @param clazz The enum type
     */
    public EnumStringConverter(Class<T> clazz) {
        this.enumClass = clazz;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(T fromObject) {
        if (enumClass == fromObject.getClass()) {
            return ((T) fromObject).name();
        }
        return "";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public T convertTo(String toObject) {
        return Enum.valueOf(enumClass, toObject.toUpperCase());
    }
}
