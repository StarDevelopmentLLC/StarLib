package com.stardevllc.starlib.misc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated
public final class Value implements Cloneable {

    private Type type;
    private Object object;
    
    public Value(Type type, Object object) {
        this.type = type;
        this.object = object;
    }
    
    public Type getType() {
        return type;
    }

    public Object get() {
        return object;
    }

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

    public String getAsString() {
        if (object != null) {
            return object.toString();
        }
        return "";
    }

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

    public <T extends Enum<?>> T getAsEnum(Class<T> clazz) {
        if (object == null) {
            return null;
        }

        if (clazz.equals(this.object.getClass())) {
            return (T) object;
        }

        return null;
    }

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

    @Override
    public Value clone() {
        try {
            return (Value) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}