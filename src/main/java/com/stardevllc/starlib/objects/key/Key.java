package com.stardevllc.starlib.objects.key;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a Key, a representation of a unique key of an object or group of objects
 */
@FunctionalInterface
public interface Key extends Comparable<Key> {
    
    /**
     * Just an empty key for non-null uses
     */
    Key EMPTY = new Key() {
        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public String toString() {
            return "EMPTY";
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @SuppressWarnings("ComparatorMethodParameterNotUsed")
        @Override
        public int compareTo(@NotNull Key o) {
            return -1;
        }
    };
    
    /**
     * Checks to see if this key is empty
     *
     * @return If the key is empty
     */
    default boolean isEmpty() {
        return toString() == null || toString().isBlank();
    }
    
    /**
     * Checks to see if this key is not empty
     *
     * @return If the key is not empty
     */
    default boolean isNotEmpty() {
        return !isEmpty();
    }
    
    /**
     * Checks to see if this key contains the string
     *
     * @param str The string
     * @return If this key contains the string
     */
    default boolean contains(String str) {
        return toString().contains(str);
    }
    
    /**
     * Checks to see if this key contains the key
     *
     * @param key The key
     * @return If this key contains the key
     */
    default boolean contains(Key key) {
        return toString().contains(key.toString());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    int hashCode();
    
    /**
     * {@inheritDoc}
     */
    @Override
    boolean equals(Object obj);
    
    /**
     * {@inheritDoc}
     */
    @Override
    String toString();
}