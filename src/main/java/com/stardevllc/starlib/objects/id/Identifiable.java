package com.stardevllc.starlib.objects.id;

@FunctionalInterface
public interface Identifiable {
    ID getId();
    
    default boolean hasId() {
        return getId() != null && getId().isNotEmpty();
    }
}