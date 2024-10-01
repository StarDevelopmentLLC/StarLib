package com.stardevllc.converter.string;

public class DoubleStringConverter implements StringConverter<Double> {

    protected DoubleStringConverter() {
        StringConverters.addConverter(double.class, this);
        StringConverters.addConverter(Double.class, this);
    }
    
    @Override
    public String convertFrom(Double fromObject) {
        if (fromObject == null) {
            return "0.0";
        }
        
        return Double.toString(fromObject);
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
