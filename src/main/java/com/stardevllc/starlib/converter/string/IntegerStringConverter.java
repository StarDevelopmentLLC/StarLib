package com.stardevllc.starlib.converter.string;

/**
 * Converts between ints and Strings
 */
public class IntegerStringConverter implements StringConverter<Integer> {
    
    /**
     * Constructs a new IntegerStringConverter
     */
    protected IntegerStringConverter() {
        StringConverters.addConverter(int.class, this);
        StringConverters.addConverter(Integer.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "0";
        }
        
        return Integer.toString((int) fromObject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Integer convertTo(String toObject) {
        try {
            return Integer.parseInt(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }
}
