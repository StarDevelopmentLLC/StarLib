package com.stardevllc.starlib.values;

@SuppressWarnings("ClassCanBeRecord")
public final class ImmutableString implements Value<String> {
    
    public static ImmutableString of(String value) {
        return new ImmutableString(value);
    }
    
    private final String value;
    
    public ImmutableString(String value) {
        this.value = value;
    }
    
    public String get() {
        return value;
    }
    
    @Override
    public String getValue() {
        return get();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (value == obj) {
            return true;
        }
        
        if (value != null) {
            return value.equals(obj);
        }
        
        return false;
    }
}