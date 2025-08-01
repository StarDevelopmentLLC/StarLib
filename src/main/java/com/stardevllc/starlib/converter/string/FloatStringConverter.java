package com.stardevllc.starlib.converter.string;

public class FloatStringConverter implements StringConverter<Float> {

    protected FloatStringConverter() {
        StringConverters.addConverter(float.class, this);
        StringConverters.addConverter(Float.class, this);
    }
    
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "0.0";
        }
        
        return Float.toString((float) fromObject);
    }

    @Override
    public Float convertTo(String toObject) {
        try {
            return Float.parseFloat(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0F;
        }
    }
}
