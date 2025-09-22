package com.stardevllc.starlib.converter.string;

import java.util.UUID;

/**
 * Converts between uuids and Strings
 */
public class UUIDStringConverter implements StringConverter<UUID> {
    
    /**
     * Constructs a new UUIDStringConverter
     */
    protected UUIDStringConverter() {
        StringConverters.addConverter(UUID.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "";
        }
        
        return fromObject.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public UUID convertTo(String toObject) {
        try {
            return UUID.fromString(toObject);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
