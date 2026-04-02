package com.stardevllc.starlib.objects.key;

@FunctionalInterface
public interface Keyable {
    Key getKey();
    
    default void setKey(Key key) {
        throw new UnsupportedOperationException(getClass().getName() + " does not support setting the key");
    }
    
    default boolean supportsSettingKey() {
        return false;
    }
    
    default boolean hasKey() {
        return getKey() != null && getKey().isNotEmpty();
    }
}