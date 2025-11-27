package com.stardevllc.starlib.serialization;

import com.stardevllc.starlib.reflection.ReflectionHelper;
import com.stardevllc.starlib.temporal.*;
import com.stardevllc.starlib.temporal.Date;

import java.lang.reflect.*;
import java.util.*;

/**
 * Class that handles the logic behind serialization. The results from the methods are to be used by other utilities
 */
public final class StarSerialization {
    
    static {
        registerClass(TimeValue.class);
        registerClass(Date.class);
        registerClass(Duration.class);
        registerClass(DurationDate.class);
        registerClass(Instant.class);
        registerClass(MonthDay.class);
        registerClass(TimeOfDay.class);
        registerClass(TimeOfHour.class);
        
    }
    
    private final Method method;
    private final Constructor<? extends StarSerializable> constructor;
    
    private StarSerialization(Class<? extends StarSerializable> clazz) {
        this.method = getDeserializeMethod(clazz);
        
        if (this.method != null) {
            method.setAccessible(true);
            
            if (!StarSerializable.class.isAssignableFrom(method.getReturnType())) {
                throw new IllegalArgumentException("Class " + clazz.getName() + " defines an invalid deserialize method. It should return an instance of StarSerializable");
            }
            
            if (Modifier.isStatic(method.getModifiers())) {
                throw new IllegalArgumentException("Class " + clazz.getName() + "'s deserialilze method is not static");
            }
        }
        
        this.constructor = getDeserializeConstructor(clazz);
        
        if (this.constructor != null) {
            constructor.setAccessible(true);
        }
    }
    
    private StarSerializable deserialize(Map<String, Object> map) {
        if (method != null) {
            try {
                return (StarSerializable) method.invoke(null, map);
            } catch (IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        } else if (constructor != null) {
            try {
                return constructor.newInstance(map);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        }
        
        return null;
    }
    
    private static final Map<Class<? extends StarSerializable>, StarSerialization> serializableClasses = new HashMap<>();
    
    /**
     * Registers a class for serialization. This method also verifies correct structure
     *
     * @param clazz The class to register
     * @throws IllegalArgumentException If the method is invalid or if no method or constructor is present
     */
    public static void registerClass(Class<? extends StarSerializable> clazz) {
        StarSerialization serialization = new StarSerialization(clazz);
        if (serialization.method == null && serialization.constructor == null) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " does not define a deserialize method or constructor");
        }
        
        serializableClasses.put(clazz, serialization);
    }
    
    /**
     * Deserializes the serialized form into an object. If the class is not registered, then it is registered. <br>
     *
     * @param clazz      The class type
     * @param serialized The serialized form
     * @param <T>        The type of object
     * @return An instance of the class or null
     * @throws ClassCastException       If the value returned by the deserialization is not castable to the provided class
     * @throws IllegalArgumentException If the class is not serializable
     */
    public static <T extends StarSerializable> T deserializeObject(Class<? extends T> clazz, Map<String, Object> serialized) {
        if (!serializableClasses.containsKey(clazz)) {
            registerClass(clazz);
        }
        
        StarSerialization serialization = serializableClasses.get(clazz);
        return (T) serialization.deserialize(serialized);
    }
    
    /**
     * Serializes the object into a serialilzable form. This method ignores if the class has been registered
     *
     * @param object The object
     * @param <T>    The type
     * @return The map representing the object
     */
    public static <T extends StarSerializable> Map<String, Object> serializeObject(T object) {
        return Collections.unmodifiableMap(object.serialize());
    }
    
    private static Method getDeserializeMethod(Class<? extends StarSerializable> clazz) {
        return ReflectionHelper.getClassMethod(clazz, "deserialize", Map.class);
    }
    
    private static Constructor<? extends StarSerializable> getDeserializeConstructor(Class<? extends StarSerializable> clazz) {
        return ReflectionHelper.getClassConstructor(clazz, Map.class).orElse(null);
    }
}