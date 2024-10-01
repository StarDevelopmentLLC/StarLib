package com.stardevllc.converter.string;

public class LongStringConverter implements StringConverter<Long> {

    protected LongStringConverter() {
        StringConverters.addConverter(long.class, this);
        StringConverters.addConverter(Long.class, this);
    }
    
    @Override
    public String convertFrom(Long fromObject) {
        if (fromObject == null) {
            return "0";
        }
        
        return Long.toString(fromObject);
    }

    @Override
    public Long convertTo(String toObject) {
        try {
            return Long.parseLong(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return 0L;
        }
    }
}
