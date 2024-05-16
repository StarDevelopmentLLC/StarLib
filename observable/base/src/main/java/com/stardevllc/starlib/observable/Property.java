package com.stardevllc.starlib.observable;

import com.stardevllc.starlib.reflection.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Property<T> extends ObservableValue<T> {
    Object DEFAULT_BEAN = null;
    String DEFAULT_NAME = "";

    Object getBean();
    String getName();

    void bind(ObservableValue<? extends T> observable);

    void unbind();

    boolean isBound();

    static <E, T extends ReadOnlyProperty<E>> List<T> getProperties(Class<T> propertyClass, Object object) {
        Set<Field> classFields = ReflectionHelper.getClassFields(object.getClass());
        List<T> properties = new ArrayList<>();
        for (Field field : classFields) {
            if (propertyClass.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    properties.add((T) field.get(object));
                } catch (IllegalAccessException e) {
                }
            }
        }

        return properties;
    }

    static <E, T extends ReadOnlyProperty<E>> T getProperty(Class<T> propertyClass, Object object, String name) {
        Set<Field> classFields = ReflectionHelper.getClassFields(object.getClass());
        for (Field field : classFields) {
            if (propertyClass.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    Object rawValue = field.get(object);
                    T property = (T) rawValue;
                    if (property.getName().equalsIgnoreCase(name)) {
                        return property;
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }

        return null;
    }
}