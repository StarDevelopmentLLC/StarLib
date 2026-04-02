package com.stardevllc.starlib.objects.key;

import com.stardevllc.starlib.registry.IRegistry;
import org.jetbrains.annotations.NotNull;

/**
 * This represents a fully qualified (fq) key for something within a Registry. <br>
 * The format it is in is "{registrykey}/{key}" <br>
 * Example: Registry: starlib:test, Item: starlib:example, FQN: starlib:test/starlib:example, or without namespacing, test/example <br>
 * Text to the left of the "/" is the registry id and text to the right of the "/" is the actual key within that registry
 */
public class FQKey implements Key {
    
    public static FQKey of(IRegistry<?> registry, Key key) {
        return new FQKey(registry, key);
    }
    
    public static FQKey of(IRegistry<?> registry, Keyable keyable) {
        return new FQKey(registry, keyable.getKey());
    }
    
    public static FQKey of(IRegistry<?> registry, String key) {
        return new FQKey(registry, Keys.of(key));
    }
    
    private final IRegistry<?> registry;
    private final Key key;
    
    private final String value;
    
    private final int hashCode;
    
    public FQKey(IRegistry<?> registry, Key key) {
        this.registry = registry;
        this.key = key;
        
        this.value = registry.getKey() + "/" + key;
        this.hashCode = this.value.hashCode();
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        return value.equals(obj.toString());
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public int compareTo(@NotNull Key o) {
        return value.compareTo(o.toString());
    }
}
