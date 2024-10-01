package com.stardevllc.converter.string;

public class ShortStringConverter implements StringConverter<Short> {

    protected ShortStringConverter() {
        StringConverters.addConverter(short.class, this);
        StringConverters.addConverter(Short.class, this);
    }
    
    @Override
    public String convertFrom(Short fromObject) {
        if (fromObject == null) {
            return "0";
        }
        
        return Short.toString(fromObject);
    }

    @Override
    public Short convertTo(String toObject) {
        try {
            return Short.parseShort(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return (short) 0;
        }
    }
}
