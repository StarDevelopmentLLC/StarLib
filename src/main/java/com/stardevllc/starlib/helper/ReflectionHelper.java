package com.stardevllc.starlib.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public final class ReflectionHelper {
    
    private static final Map<Method, Annotation> methodAnnotationCache = new HashMap<>();
    public static <A extends Annotation> A getMethodAnnotation(Method method, Class<A> annotation) {
        //Check the cache to see if it exists already
        if (methodAnnotationCache.containsKey(method)) {
            return (A) methodAnnotationCache.get(method);
        }
        
        //Check to see if the method has the annotation directly
        if (method.isAnnotationPresent(annotation)) {
            A a = method.getAnnotation(annotation);
            methodAnnotationCache.put(method, a);
            return a;
        }
        
        //Get the declaring class of the method
        Class<?> declaringClass = method.getDeclaringClass();
        try {
            //Search using the recursive way to get the method from the declaring class and it's parents
            Method annotatedMethod = getAnnotatedMethod(annotation, declaringClass, method.getName(), method.getParameterTypes());
            //Check to see if the method is null, if not, return the annotation
            if (annotatedMethod != null) {
                A a = annotatedMethod.getAnnotation(annotation);
                methodAnnotationCache.put(method, a);
                return a;
            }
        } catch (Exception e) {}
        
        //Return null if nothing is found
        return null;
    }
    
    public static Method getAnnotatedMethod(Class<? extends Annotation> annotation, Class<?> c, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = c.getDeclaredMethod(methodName, parameterTypes);
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
            
            for (Class<?> classInterface : c.getInterfaces()) {
                Method interfaceMethod = getAnnotatedMethod(annotation, classInterface, methodName, parameterTypes);
                if (interfaceMethod != null) {
                    return interfaceMethod;
                }
            }
            
            return getAnnotatedMethod(annotation, c.getSuperclass(), methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            if (c.getSuperclass() == Object.class) {
                return null;
            }
            
            return getAnnotatedMethod(annotation, c.getSuperclass(), methodName, parameterTypes);
        } catch (Exception e) {
            return null;
        }
    }
    
    private static final Map<Class<?>, Annotation> classAnnotations = new HashMap<>();
    public static <A extends Annotation> A getClassAnnotation(Class<?> clazz, Class<A> annotation) {
        if (classAnnotations.containsKey(clazz)) {
            return (A) classAnnotations.get(clazz);
        }
        
        //Check to see if the class has the annotation directly
        if (clazz.isAnnotationPresent(annotation)) {
            A a = clazz.getAnnotation(annotation);
            classAnnotations.put(clazz, a);
            return a;
        }
        
        //If the class doesn't have the annotation, look at any interfaces
        for (Class<?> clazzInterface : clazz.getInterfaces()) {
            //Check to see if the interface has the annotation directly
            if (clazzInterface.isAnnotationPresent(annotation)) {
                A a = clazzInterface.getAnnotation(annotation);
                classAnnotations.put(clazz, a);
                return a;
            }
        }
        
        //If the interfaces don't have the annotation, then look recursively through the hierarchy for it
        if (clazz.getSuperclass() != Object.class) {
            return getClassAnnotation(clazz.getSuperclass(), annotation);
        }
        
        //If nothing is found, return null
        return null;
    }
    
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