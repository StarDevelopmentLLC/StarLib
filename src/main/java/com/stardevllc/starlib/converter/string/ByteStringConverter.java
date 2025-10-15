package com.stardevllc.starlib.converter.string;

/**
 * Converts between bytes and Strings
 */
public class ByteStringConverter implements StringConverter<Byte> {
    
    /**
     * Constructs a new ByteStringConverter
     */
    protected ByteStringConverter() {
        StringConverters.addConverter(byte.class, this);
        StringConverters.addConverter(Byte.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Byte fromObject) {
        if (fromObject == null) {
            return "0";
        }
        
        return Byte.toString((byte) fromObject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Byte convertTo(String toObject) {
        try {
            return Byte.parseByte(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return (byte) 0;
        }
    }
}
