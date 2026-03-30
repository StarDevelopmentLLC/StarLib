package com.stardevllc.starlib.objects.key;

@FunctionalInterface
public interface Keyable {
    Key getKey();
    
    default boolean hasKey() {
        return getKey() != null && getKey().isNotEmpty();
    }
}