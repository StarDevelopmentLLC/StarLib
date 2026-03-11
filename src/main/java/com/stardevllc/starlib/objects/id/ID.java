package com.stardevllc.starlib.objects.id;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an ID, a representation of a unique id of an object or group of objects
 */
@FunctionalInterface
public interface ID extends Comparable<ID> {
    
    ID EMPTY = new ID() {
        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public String toString() {
            return "EMPTY";
        }
        
        @SuppressWarnings("ComparatorMethodParameterNotUsed")
        @Override
        public int compareTo(@NotNull ID o) {
            return -1;
        }
    };
    
    default boolean isEmpty() {
        return true;
    }
    
    default boolean isNotEmpty() {
        return !isEmpty();
    }
    
    @Override
    int hashCode();
    
    @Override
    boolean equals(Object obj);
    
    @Override
    String toString();
}