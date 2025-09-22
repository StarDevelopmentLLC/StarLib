package com.stardevllc.starlib.converter.string;

/**
 * Converts between shorts and Strings
 */
public class ShortStringConverter implements StringConverter<Short> {
    
    /**
     * Constructs a new ShortStringConverter
     */
    protected ShortStringConverter() {
        StringConverters.addConverter(short.class, this);
        StringConverters.addConverter(Short.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "0";
        }
        
        return Short.toString((short) fromObject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Short convertTo(String toObject) {
        try {
            return Short.parseShort(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return (short) 0;
        }
    }
}
