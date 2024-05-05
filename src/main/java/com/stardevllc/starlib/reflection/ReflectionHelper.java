package com.stardevllc.starlib.reflection;

import com.stardevllc.starlib.observable.property.readonly.ReadOnlyProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public final class ReflectionHelper {
    
    public static Method getClassMethod(Class<?> clazz, String name, Class<?>... parameters) {
        try {
            return clazz.getDeclaredMethod(name, parameters);
        } catch (NoSuchMethodException e) {}

        return getClassMethod(clazz.getSuperclass(), name, parameters);
    }
    
    public static Set<Method> getClassMethods(Class<?> clazz) {
        Set<Method> methods = new LinkedHashSet<>(Arrays.asList(clazz.getDeclaredMethods()));
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            getClassMethods(clazz.getSuperclass(), methods);
        }
        return methods;
    }
    
    public static void getClassMethods(Class<?> clazz, Set<Method> methods) {
        if (methods == null) {
            methods = new LinkedHashSet<>();
        }
        
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        if (clazz.getSuperclass() != null) {
            getClassMethods(clazz.getSuperclass(), methods);
        }
    }
    
    public static Field getClassField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {}

        return getClassField(clazz.getSuperclass(), name);
    }
    
    public static Set<Field> getClassFields(Class<?> clazz) {
        Set<Field> fields = new LinkedHashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            getClassFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
    
    public static void getClassFields(Class<?> clazz, Set<Field> fields) {
        if (fields == null) {
            fields = new LinkedHashSet<>();
        }

        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            getClassFields(clazz.getSuperclass(), fields);
        }
    }
    
    public static <E, T extends ReadOnlyProperty<E>> List<T> getProperties(Class<T> propertyClass, Object object) {
        Set<Field> classFields = getClassFields(object.getClass());
        List<T> properties = new ArrayList<>();
        for (Field field : classFields) {
            if (propertyClass.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    properties.add((T) field.get(object));
                } catch (IllegalAccessException e) {}
            }
        }

        return properties;
    }
    
    public static <E, T extends ReadOnlyProperty<E>> T getProperty(Class<T> propertyClass, Object object, String name) {
        Set<Field> classFields = getClassFields(object.getClass());
        for (Field field : classFields) {
            if (propertyClass.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    Object rawValue = field.get(object);
                    T property = (T) rawValue;
                    if (property.getName().equalsIgnoreCase(name)) {
                        return property;
                    }
                } catch (IllegalAccessException e) {}
            }
        }

        return null;
    }
}