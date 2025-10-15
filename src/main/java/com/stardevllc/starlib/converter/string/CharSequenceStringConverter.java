package com.stardevllc.starlib.converter.string;

/**
 * Converts between charsequences and Strings
 */
public class CharSequenceStringConverter implements StringConverter<CharSequence> {
    
    /**
     * Constructs a new CharSequenceStringConverter
     */
    protected CharSequenceStringConverter() {
        StringConverters.addConverter(String.class, this);
        StringConverters.addConverter(CharSequence.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(CharSequence fromObject) {
        return fromObject.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence convertTo(String toObject) {
        return toObject;
    }
}
