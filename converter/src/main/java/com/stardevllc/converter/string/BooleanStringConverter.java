package com.stardevllc.converter.string;

public class BooleanStringConverter implements StringConverter<Boolean> {
    protected BooleanStringConverter() {
        StringConverters.addConverter(boolean.class, this);
        StringConverters.addConverter(Boolean.class, this);
    }

    @Override
    public String convertFrom(Boolean fromObject) {
        if (fromObject == null) {
            return "false";
        }
        
        return Boolean.toString(fromObject);
    }

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
