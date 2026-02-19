package com.stardevllc.starlib.registry;

/**
 * Represents a Registry Key
 * <p>
 *     A RegistryKey is used to store and retrieve values within a Registry
 *     Sub-classes must implement the toString method as that is used for comparisons
 *     A RegistryKey must be immutable, so when the values are set, they are not able to be changed using methods (Can't really prevent reflection though)
 *     Can't use an interface because the hashCode and equals methods cannot be default in interfaces because they override methods in Object
 *     This will be a Value class when Project Valhalla from Oracle is ready for use as the keys shouldn't have identity, they are the identity
 * </p>
 */
public abstract class RegistryKey implements Comparable<RegistryKey> {
    
    /**
     * This is just an emtpy RegistryKey
     */
    public static final RegistryKey EMPTY = of("");
    
    /**
     * Provides a default implementation for quick Strings
     * @param key The String to use as the key
     * @return The new RegistryKey instance
     */
    public static RegistryKey of(String key) {
        return new RegistryKey() {
            public String toString() {
                return key;
            }
        };
    }
    
    public abstract String toString();
    
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object obj) {
        if (obj instanceof RegistryKey k) {
            return this.toString().equals(k.toString());
        }
        
        return false;
    }
    
    public int compareTo(RegistryKey o) {
        return toString().compareTo(o.toString());
    }
}