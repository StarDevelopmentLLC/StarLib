package com.stardevllc.starlib.objects.key;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a Key, a representation of a unique key of an object or group of objects
 */
@FunctionalInterface
public interface Key extends Comparable<Key> {
    
    Key EMPTY = new Key() {
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
        public int compareTo(@NotNull Key o) {
            return -1;
        }
    };
    
    default boolean isEmpty() {
        return toString() == null || toString().isBlank();
    }
    
    default boolean isNotEmpty() {
        return !isEmpty();
    }
    
    default boolean contains(String str) {
        return toString().contains(str);
    }
    
    @Override
    int hashCode();
    
    @Override
    boolean equals(Object obj);
    
    @Override
    String toString();
}