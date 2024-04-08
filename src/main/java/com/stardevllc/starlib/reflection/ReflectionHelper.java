package com.stardevllc.starlib.reflection;

import com.stardevllc.starlib.observable.property.ReadOnlyProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A class to make it easier to recursively get fields and methods of a class <br>
 * There is no caching of these though, but this is planned.
 */
public final class ReflectionHelper {
    
    /**
     * Recursively gets a method of a class from itself or it's parent class(es)
     * @param clazz The base class to get the method from
     * @param name The name of the method - Case Sensitive
     * @param parameters The parameters of the method
     * @return The method or null if it does not exist in the class heirarchy
     */
    public static Method getClassMethod(Class<?> clazz, String name, Class<?>... parameters) {
        try {
            return clazz.getDeclaredMethod(name, parameters);
        } catch (NoSuchMethodException e) {}

        return getClassMethod(clazz.getSuperclass(), name, parameters);
    }

    /**
     * Recursively gets all methods of a class and of it's parent class(es)
     * @param clazz The class to get the methods from
     * @return The set of methods. This should never be null, if it is, please report as a bug
     */
    public static Set<Method> getClassMethods(Class<?> clazz) {
        Set<Method> methods = new LinkedHashSet<>(Arrays.asList(clazz.getDeclaredMethods()));
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            getClassMethods(clazz.getSuperclass(), methods);
        }
        return methods;
    }

    /**
     * The is a helper method for the other one and gets all methods from the provided class and the parent class(es)
     * @param clazz The class to get the methods from
     * @param methods The set of existing methods. This set must be mutable to work
     */
    public static void getClassMethods(Class<?> clazz, Set<Method> methods) {
        if (methods == null) {
            methods = new LinkedHashSet<>();
        }
        
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        if (clazz.getSuperclass() != null) {
            getClassMethods(clazz.getSuperclass(), methods);
        }
    }

    /**
     * Recursively gets a field from a class and it's parent class(es)
     * @param clazz The class to get the field from
     * @param name The name of the field - Case Sensitive
     * @return The field or null if it does not exist
     */
    public static Field getClassField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {}

        return getClassField(clazz.getSuperclass(), name);
    }

    /**
     * Recursively gets all fields from a class and it's parent class(es)
     * @param clazz The class to get the fields from
     * @return The set of fields of the class and it's parent class(es). This should never be null, if it is, please report as a bug
     */
    public static Set<Field> getClassFields(Class<?> clazz) {
        Set<Field> fields = new LinkedHashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            getClassFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /**
     * The is a helper method for the other one and gets all fields from the provided class and the parent class(es)
     * @param clazz The class to get the fields from
     * @param fields The set of existing fields. This set must be mutable to work
     */
    public static void getClassFields(Class<?> clazz, Set<Field> fields) {
        if (fields == null) {
            fields = new LinkedHashSet<>();
        }

        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            getClassFields(clazz.getSuperclass(), fields);
        }
    }
    
    public static <E, T extends ReadOnlyProperty<E>> T getProperty(Class<T> propertyClass, Object object, String name) {
        Set<Field> classFields = getClassFields(object.getClass());
        for (Field field : classFields) {
            if (propertyClass.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    Object rawValue = field.get(object);
                    T property = (T) rawValue;
                    if (property.getName().equals(name)) {
                        return property;
                    }
                } catch (IllegalAccessException e) {}
            }
        }

        return null;
    }
}