package com.stardevllc.starlib.converter.string;

/**
 * Converts between doubles and Strings
 */
public class DoubleStringConverter implements StringConverter<Double> {
    
    /**
     * Constructs a new DoubleStringConverter
     */
    protected DoubleStringConverter() {
        StringConverters.addConverter(double.class, this);
        StringConverters.addConverter(Double.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "0.0";
        }
        
        return Double.toString((double) fromObject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Double convertTo(String toObject) {
        try {
            return Double.parseDouble(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0;
        }
    }
}
