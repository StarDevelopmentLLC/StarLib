package com.stardevllc.converter.string;

import java.util.UUID;

public class UUIDStringConverter extends StringConverter<UUID> {
    @Override
    public String toString(UUID object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    @Override
    public UUID fromString(String string) {
        if (string == null) {
            return null;
        }

        if (string.isEmpty()) {
            return null;
        }

        return UUID.fromString(string);
    }
}
