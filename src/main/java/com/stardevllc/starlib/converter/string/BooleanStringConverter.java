package com.stardevllc.starlib.converter.string;

/**
 * Converts between booleans and Strings
 */
public class BooleanStringConverter implements StringConverter<Boolean> {
    
    /**
     * Constructs a new BooleanStringConverter
     */
    protected BooleanStringConverter() {
        StringConverters.addConverter(boolean.class, this);
        StringConverters.addConverter(Boolean.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Boolean fromObject) {
        if (fromObject == null) {
            return "false";
        }
        
        return Boolean.toString(fromObject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean convertTo(String toObject) {
        if (toObject == null) {
            return false;
        }
        
        String input = toObject.toLowerCase();
        if (input.equals("true") || input.equals("1")) {
            return true;            
        } else if (input.equals("false") || input.equals("0")) {
            return false;
        }
           
        return false;
    }
}
