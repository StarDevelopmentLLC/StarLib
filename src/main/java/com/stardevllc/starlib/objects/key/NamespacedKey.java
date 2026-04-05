package com.stardevllc.starlib.objects.key;

import org.jetbrains.annotations.NotNull;

/**
 * A namespaced key is a {@link Key} that has a namespace. This allows for multiple keys of the same value to exist in a single collection <br>
 * This structure is used in Minecraft to separate items/blocks/entities from mods. This is typically delimited with the ":" character
 */
public interface NamespacedKey extends Key {
    /**
     * This is the namespace of this key
     *
     * @return The namespace
     */
    String getNamespace();
    
    /**
     * This is the value of the key, this is the actual identifier of whatever thing is being identified by this key
     *
     * @return The value
     */
    String getValue();
    
    /**
     * This is the delimiter of the key. This is what is used between the namespace and the value
     *
     * @return The delimiter
     */
    default char delimiter() {
        return ':';
    }
    
    static NamespacedKey of(String namespace, String value) {
        return of(namespace, value, ':');
    }
    
    static NamespacedKey of(String namespace, String value, char delimiter) {
        return new NamespacedKey() {
            @Override
            public String getNamespace() {
                return namespace;
            }
            
            @Override
            public String getValue() {
                return value;
            }
            
            @Override
            public char delimiter() {
                return delimiter;
            }
            
            @Override
            public int hashCode() {
                return toString().hashCode();
            }
            
            @Override
            public boolean equals(Object obj) {
                return toString().equalsIgnoreCase(obj.toString());
            }
            
            @Override
            public String toString() {
                return getNamespace() + delimiter() + getValue();
            }
            
            @Override
            public int compareTo(@NotNull Key o) {
                return toString().compareTo(o.toString());
            }
        };
    }
}