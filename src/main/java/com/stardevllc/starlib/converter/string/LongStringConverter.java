package com.stardevllc.starlib.converter.string;

/**
 * Converts between longs and Strings
 */
public class LongStringConverter implements StringConverter<Long> {
    
    /**
     * Constructs a new LongStringConverter
     */
    protected LongStringConverter() {
        StringConverters.addConverter(long.class, this);
        StringConverters.addConverter(Long.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Long fromObject) {
        if (fromObject == null) {
            return "0";
        }
        
        return Long.toString((long) fromObject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Long convertTo(String toObject) {
        try {
            return Long.parseLong(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return 0L;
        }
    }
}
