package com.stardevllc.starlib.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * Some reflection based utilities that also provide some caching
 */
public final class ReflectionHelper {
    private ReflectionHelper() {
    }
    
    private static final Map<Method, Annotation> methodAnnotationCache = new HashMap<>();
    
    /**
     * Recursively retrieves an annotation from a method
     *
     * @param method     The method
     * @param annotation The annotation class
     * @param <A>        The annotation type
     * @return The annotation details
     */
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
        } catch (Exception e) {
        }
        
        //Return null if nothing is found
        return null;
    }
    
    /**
     * Recursively gets a method with a specific annotation
     *
     * @param annotation     The annotation type
     * @param c              The class to get the method from
     * @param methodName     The name of the method
     * @param parameterTypes The parameters of the method
     * @return The method that was found or null if not
     */
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
    
    /**
     * Recursively gets an annotation from a class
     *
     * @param clazz      The class to get the annotation from
     * @param annotation The annotation class
     * @param <A>        The annotationi type
     * @return The annotation information
     */
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
    
    /**
     * Gets a value from an object using dot (.) notation to denote the path
     *
     * @param object The object that holds the value
     * @param path   The path of the value
     * @return The value
     */
    public static Object getValue(Object object, String path) {
        if (!path.contains(".")) {
            return getFieldValue(object, path);
        }
        
        String[] split = path.split("\\.");
        
        Object newObject = getFieldValue(object, split[0]);
        String newPath = path.substring(split[0].length() + 1);
        
        return getValue(newObject, newPath);
    }
    
    /**
     * Recursively gets a fiel value from an object
     *
     * @param object    The object that holds the field
     * @param fieldName The name of the field
     * @return The field value or null if not found or not accessible
     */
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
    
    /**
     * Creates a new instnace of a class using reflelction
     *
     * @param clazz  The class to create the instance of
     * @param params The parameters for the constructor
     * @param <T>    The object type
     * @return The new instance as an optional
     */
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
            } catch (Exception e) {
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Gets a constructor from a class based on the parameters
     *
     * @param clazz      The class that holds the constructor
     * @param parameters The parameters of the constructor
     * @return An optional of the constructor instance
     */
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
    
    /**
     * Recursively gets a class method based on parameters
     *
     * @param clazz      The class that holds the method
     * @param name       The name of the method
     * @param parameters The parameters of the method
     * @return The method instance
     */
    public static Method getClassMethod(Class<?> clazz, String name, Class<?>... parameters) {
        try {
            return clazz.getDeclaredMethod(name, parameters);
        } catch (NoSuchMethodException e) {
        }
        
        return getClassMethod(clazz.getSuperclass(), name, parameters);
    }
    
    private static final Map<Class<?>, Set<Method>> classMethods = new HashMap<>();
    
    /**
     * Recursively fetches the methods of a class
     *
     * @param clazz The class to search
     * @return The set of methods
     */
    public static Set<Method> getClassMethods(Class<?> clazz) {
        if (classMethods.containsKey(clazz)) {
            return new HashSet<>(classMethods.get(clazz));
        }
        
        Set<Method> methods = new LinkedHashSet<>(Arrays.asList(clazz.getDeclaredMethods()));
        classMethods.put(clazz, methods);
        
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            methods.addAll(getClassMethods(clazz.getSuperclass()));
        }
        
        return methods;
    }
    
    private static final Map<Class<?>, Map<String, Field>> classFields = new HashMap<>();
    
    /**
     * Recursively gets the field of a class
     *
     * @param clazz The class that holds the field
     * @param name  The field name
     * @return The found field or null if it is invalid
     */
    public static Field getClassField(Class<?> clazz, String name) {
        Map<String, Field> fields = getClassFields(clazz);
        return fields.get(name.toLowerCase());
    }
    
    /**
     * Recursively fetches all fields of a class and super-classes
     *
     * @param clazz The class to search
     * @return The found fields
     */
    public static Map<String, Field> getClassFields(Class<?> clazz) {
        if (classFields.containsKey(clazz)) {
            return new HashMap<>(classFields.get(clazz));
        }
        
        Map<String, Field> fields = new HashMap<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            fields.put(declaredField.getName().toLowerCase(), declaredField);
        }
        
        classFields.put(clazz, fields);
        
        if (clazz.getSuperclass() != null) {
            fields.putAll(getClassFields(clazz.getSuperclass()));
        }
        
        return fields;
    }
}