package com.stardevllc.starlib.value;

import java.util.*;

public final class Values {
    private static final Map<Class<?>, Class<? extends Value<?>>> READONLY_MAPPINGS = new HashMap<>();
    private static final Map<Class<?>, Class<? extends WritableValue<?>>> WRITABLE_MAPPINGS = new HashMap<>();
    
    private static void addPrimitiveMapping(Class<?> primitiveClass, Class<?> wrapperClass, Class<? extends Value<?>> readOnlyClass, Class<? extends WritableValue<?>> writableClass) {
        addMapping(primitiveClass, readOnlyClass, writableClass);
        addMapping(wrapperClass, readOnlyClass, writableClass);
    }
    
    public static void addMapping(Class<?> clazz, Class<? extends Value<?>> readOnlyClass, Class<? extends WritableValue<?>> writableClass) {
        READONLY_MAPPINGS.put(clazz, readOnlyClass);
        WRITABLE_MAPPINGS.put(clazz, writableClass);
    }
    
    static {
        addPrimitiveMapping(boolean.class, Boolean.class, BooleanValue.class, WritableBooleanValue.class);
        addPrimitiveMapping(byte.class, Byte.class, ByteValue.class, WritableByteValue.class);
        addPrimitiveMapping(char.class, Character.class, CharacterValue.class, WritableCharacterValue.class);
        addPrimitiveMapping(double.class, Double.class, DoubleValue.class, WritableDoubleValue.class);
        addPrimitiveMapping(float.class, Float.class, FloatValue.class, WritableFloatValue.class);
        addPrimitiveMapping(int.class, Integer.class, IntegerValue.class, WritableIntegerValue.class);
        addPrimitiveMapping(long.class, Long.class, LongValue.class, WritableLongValue.class);
        addPrimitiveMapping(short.class, Short.class, ShortValue.class, WritableShortValue.class);
        addMapping(String.class, StringValue.class, WritableStringValue.class);
        addMapping(UUID.class, UUIDValue.class, WritableUUIDValue.class);
    }
}