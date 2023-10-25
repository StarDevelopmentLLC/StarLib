package com.stardevllc.starlib;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class allows storing values that can have different types in a single field. <br>
 * This is mainly a compatibility utility for StarSQL to allow storing in a single field. <br>
 * StarSQL has default integration for this class built into it. <br>
 * All you need to do to use this is to store it as a field and use the methods to interact with the value and type.
 */
public final class Value implements Cloneable {

    private Type type;
    private Object object;

    /**
     * Constructs a new value
     * @param type The Type of the value
     * @param object The actual object of the value
     */
    public Value(Type type, Object object) {
        this.type = type;
        this.object = object;
    }

    /**
     * @return This value's type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The actual object of the value
     */
    public Object get() {
        return object;
    }

    /**
     * Sets the object of the value. <br>
     * There is a check for the correct class type. See the Type Enum to see what classes go with which type.
     * @param object The object to store, can be null
     */
    public void set(Object object) {
        if (object == null) {
            this.object = null;
            return;
        }
        
        if (!this.type.isValidClass(object.getClass())) {
            return;
        }
        
        this.object = object;
    }

    /**
     * Gets this object as an integer. This will parse from other types safely
     * @return The object as an integer, or 0 if it is not
     */
    public int getAsInt() {
        if (object instanceof Number n) {
            return n.intValue();
        } else if (object instanceof String str) {
            return Integer.parseInt(str);
        } else if (object instanceof Boolean bool) {
            return bool ? 1 : 0;
        }
        return 0;
    }

    /**
     * Gets this object as a double. This will parse from other types safely
     * @return The object as a double, or 0.0 if it is not
     */
    public double getAsDouble() {
        if (object instanceof Number n) {
            return n.doubleValue();
        } else if (object instanceof String str) {
            return Double.parseDouble(str);
        } else if (object instanceof Boolean bool) {
            return bool ? 1.0 : 0.0;
        }
        return 0.0;
    }

    /**
     * Gets this object as a long. This will parse from other types safely
     * @return The object as a long, or 0 if it is not
     */
    public long getAsLong() {
        if (object instanceof Number n) {
            return n.longValue();
        } else if (object instanceof String str) {
            return Long.parseLong(str);
        } else if (object instanceof Boolean bool) {
            return bool ? 1 : 0;
        }
        return 0;
    }

    /**
     * Gets this object as a String. This will call the toString method on the object and check for null
     * @return The object as a String, or an empty string if the object is null
     */
    public String getAsString() {
        if (object != null) {
            return object.toString();
        }
        return "";
    }

    /**
     * Gets this object as a boolean. Please see below for converions<br>
     * If this object is a boolean, it will return it directly<br>
     * If this object is a String, it will parse using the {@link Boolean#parseBoolean(String)} method<br>
     * If this object is a number, it will return true if it is equal to 1 and false for other values.<br>
     * If it is none of those things, it will return false by default
     * @return The object as a boolean
     */
    public boolean getAsBoolean() {
        if (object instanceof Boolean bool) {
            return bool;
        } else if (object instanceof String str) {
            return Boolean.parseBoolean(str);
        } else if (object instanceof Integer integer) {
            return integer == 1;
        } else if (object instanceof Long longValue) {
            return longValue == 1;
        } else if (object instanceof Double doubleValue) {
            return doubleValue == 1.0;
        }
        return false;
    }

    /**
     * Gets the object as an enum based on the parameter. <br>
     * This method will return null if the object is null, or if the class provided does not match the actual class of the object.<br>
     * Otherwise it will return a casted version of the value.
     * @param clazz The enum's class
     * @return The value
     * @param <T> The type of the enum.
     */
    public <T extends Enum<?>> T getAsEnum(Class<T> clazz) {
        if (object == null) {
            return null;
        }

        if (clazz.equals(this.object.getClass())) {
            return (T) object;
        }

        return null;
    }

    /**
     * All supported types of this Value class
     */
    public enum Type {
        INTEGER(Integer.class, int.class, Byte.class, byte.class, short.class, Short.class), 
        DOUBLE(Double.class, double.class, Float.class, float.class), 
        STRING(String.class), 
        LONG(Long.class, long.class),
        BOOLEAN(Boolean.class, boolean.class), 
        ENUM(Enum.class);
        
        private final Set<Class<?>> validClasses = new HashSet<>();
        Type(Class<?>... classes) {
            if (classes != null) {
                validClasses.addAll(List.of(classes));
            }
        }
        
        public boolean isValidClass(Class<?> clazz) {
            if (this.validClasses.contains(clazz)) {
                return true;
            }

            for (Class<?> validClass : this.validClasses) {
                if (validClass.isAssignableFrom(clazz)) {
                    return true;
                }
            }
            
            return false;
        }

        public Set<Class<?>> getValidClasses() {
            return validClasses;
        }
    }

    /**
     * Clones this value
     * @return The cloned value
     */
    @Override
    public Value clone() {
        try {
            return (Value) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}