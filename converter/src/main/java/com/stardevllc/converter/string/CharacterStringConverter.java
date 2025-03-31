package com.stardevllc.converter.string;

public class CharacterStringConverter implements StringConverter<Character> {

    protected CharacterStringConverter() {
        StringConverters.addConverter(char.class, this);
        StringConverters.addConverter(Character.class, this);
    }
    
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "";
        }
        return Character.toString((char) fromObject);
    }

    @Override
    public Character convertTo(String toObject) {
        if (toObject == null || toObject.isEmpty()) {
            return '\u0000';
        }
        
        return toObject.charAt(0);
    }
}
