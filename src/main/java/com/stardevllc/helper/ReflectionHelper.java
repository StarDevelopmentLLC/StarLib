package com.stardevllc.helper;

import java.lang.reflect.*;
import java.util.*;

public final class ReflectionHelper {
    public static Object getValue(Object object, String path) {
        if (!path.contains(".")) {
            return getFieldValue(object, path);
        }
        
        String[] split = path.split("\\.");
        
        Object newObject = getFieldValue(object, split[0]);
        String newPath = path.substring(split[0].length() + 1);
        
        return getValue(newObject, newPath);
    }
    
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getClassField(object.getClass(), fieldName);
        if (field == null) {
            return null;
        }
        
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static <T> Optional<T> newInstance(Class<T> clazz, Object... params) {
        Class<?>[] paramTypes;
        if (params != null) {
            paramTypes = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = params[i].getClass();
            }
        } else {
            paramTypes = new Class<?>[0];
        }
        
        Constructor<?> constructor = getClassConstructor(clazz, paramTypes).orElse(null);
        if (constructor != null) {
            constructor.setAccessible(true);
            try {
                return Optional.of((T) constructor.newInstance(params));
            } catch (Exception e) {}
        }
        
        return Optional.empty();
    }
    
    public static Optional<Constructor<?>> getClassConstructor(Class<?> clazz, Class<?>... parameters) {
        if (parameters == null || parameters.length == 0) {
            try {
                return Optional.of(clazz.getDeclaredConstructor());
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        
        constructorLoop:
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            Class<?>[] constructorParameterTypes = constructor.getParameterTypes();
            if (constructorParameterTypes.length != parameters.length) {
                continue;
            }
            
            for (int i = 0; i < constructorParameterTypes.length; i++) {
                if (!constructorParameterTypes[i].isAssignableFrom(parameters[i])) {
                    continue constructorLoop;
                }
            }
            
            return Optional.of(constructor);
        }
        
        return Optional.empty();
    }
    
    public static Method getClassMethod(Class<?> clazz, String name, Class<?>... parameters) {
        try {
            return clazz.getDeclaredMethod(name, parameters);
        } catch (NoSuchMethodException e) {
        }
        
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
        } catch (NoSuchFieldException e) {
        }
        
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
}