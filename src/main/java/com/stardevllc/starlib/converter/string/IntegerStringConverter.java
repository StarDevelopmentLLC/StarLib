package com.stardevllc.starlib.converter.string;

public class IntegerStringConverter implements StringConverter<Integer> {

    protected IntegerStringConverter() {
        StringConverters.addConverter(int.class, this);
        StringConverters.addConverter(Integer.class, this);
    }
    
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "0";
        }
        
        return Integer.toString((int) fromObject);
    }

    @Override
    public Integer convertTo(String toObject) {
        try {
            return Integer.parseInt(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }
}
