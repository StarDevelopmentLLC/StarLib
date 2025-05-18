package com.stardevllc.converter.string;

import java.util.UUID;

public class UUIDStringConverter implements StringConverter<UUID> {
    protected UUIDStringConverter() {
        StringConverters.addConverter(UUID.class, this);
    }

    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject == null) {
            return "";
        }
        
        return fromObject.toString();
    }

    @Override
    public UUID convertTo(String toObject) {
        try {
            return UUID.fromString(toObject);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
