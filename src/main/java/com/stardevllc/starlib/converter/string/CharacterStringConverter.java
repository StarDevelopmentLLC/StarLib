package com.stardevllc.starlib.converter.string;

/**
 * Converts between chars and Strings
 */
public class CharacterStringConverter implements StringConverter<Character> {
    
    /**
     * Constructs a new CharacterStringConverter
     */
    protected CharacterStringConverter() {
        StringConverters.addConverter(char.class, this);
        StringConverters.addConverter(Character.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "";
        }
        return Character.toString((char) fromObject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Character convertTo(String toObject) {
        if (toObject == null || toObject.isEmpty()) {
            return '\u0000';
        }
        
        return toObject.charAt(0);
    }
}
