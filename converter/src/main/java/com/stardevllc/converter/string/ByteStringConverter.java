package com.stardevllc.converter.string;

public class ByteStringConverter implements StringConverter<Byte> {

    protected ByteStringConverter() {
        StringConverters.addConverter(byte.class, this);
        StringConverters.addConverter(Byte.class, this);
    }
    
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "0";
        }
        
        return Byte.toString((byte) fromObject);
    }

    @Override
    public Byte convertTo(String toObject) {
        try {
            return Byte.parseByte(toObject);
        } catch (NumberFormatException | NullPointerException e) {
            return (byte) 0;
        }
    }
}
