package com.stardevllc.starlib.objects;

@FunctionalInterface
public interface Nameable {
    String getName();
    
    default boolean hasName() {
        return getName() != null && !getName().isEmpty();
    }
}