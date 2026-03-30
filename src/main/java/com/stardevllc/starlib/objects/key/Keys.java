package com.stardevllc.starlib.objects.key;

import com.stardevllc.starlib.objects.key.impl.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Keys {
    public static Key of(Object object) {
        if (object instanceof String str) {
            return new StringKey(str);
        } else if (object instanceof Integer i) {
            return new IntKey(i);
        } else if (object instanceof Long l) {
            return new LongKey(l);
        }
        
        return new Key() {
            @Override
            public int compareTo(@NotNull Key o) {
                return toString().compareTo(o.toString());
            }
            
            @Override
            public int hashCode() {
                return object.hashCode();
            }
            
            @Override
            public boolean equals(Object obj) {
                return Objects.equals(object, obj);
            }
            
            @Override
            public String toString() {
                return object.toString();
            }
        };
    }
}