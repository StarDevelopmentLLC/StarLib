package com.stardevllc.converter.string;

public class CharSequenceStringConverter implements StringConverter<CharSequence> {
    
    protected CharSequenceStringConverter() {
        StringConverters.addConverter(String.class, this);
        StringConverters.addConverter(CharSequence.class, this);
    }
    
    @Override
    public String convertFrom(Object fromObject) {
        return fromObject.toString();
    }
    
    @Override
    public CharSequence convertTo(String toObject) {
        return toObject;
    }
}
