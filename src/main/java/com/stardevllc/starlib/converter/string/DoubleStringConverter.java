package com.stardevllc.starlib.converter.string;

public class DoubleStringConverter implements StringConverter<Double> {

    protected DoubleStringConverter() {
        StringConverters.addConverter(double.class, this);
        StringConverters.addConverter(Double.class, this);
    }
    
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "0.0";
        }
        
        return Double.toString((double) fromObject);
    }

    @Override
    public Double convertTo(String toObject) {
        try {
            return Double.parseDouble(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0;
        }
    }
}
