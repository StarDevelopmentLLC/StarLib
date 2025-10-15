package com.stardevllc.starlib.converter.string;

/**
 * Converts between Floats and Strings
 */
public class FloatStringConverter implements StringConverter<Float> {
    
    /**
     * Constructs a new FloatStringConverter
     */
    protected FloatStringConverter() {
        StringConverters.addConverter(float.class, this);
        StringConverters.addConverter(Float.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Float fromObject) {
        if (fromObject == null) {
            return "0.0";
        }
        
        return Float.toString((float) fromObject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Float convertTo(String toObject) {
        try {
            return Float.parseFloat(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0F;
        }
    }
}
