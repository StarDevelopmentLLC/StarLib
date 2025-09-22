package com.stardevllc.starlib.fieldwatcher;

import java.lang.reflect.Field;

/**
 * Listener for field changes
 */
@FunctionalInterface
public interface FieldChangeListener {
    /**
     * When a field changes
     * @param object The object that owns the field
     * @param field The field itself
     * @param oldValue The old value
     * @param newValue The new value
     */
    void changed(Object object, Field field, Object oldValue, Object newValue);
}
