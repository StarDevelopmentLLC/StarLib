package com.stardevllc.starlib.flag;

import java.util.*;

public class Flags<F extends IFlag> {
    private final Map<F, IFlag.State> backingMap = new HashMap<>();
    
    public boolean contains(F flag) {
        return backingMap.containsKey(flag);
    }
    
    public IFlag.State get(F flag) {
        if (backingMap.containsKey(flag)) {
            return backingMap.get(flag);
        }
        
        return IFlag.State.NOT_SET;
    }
    
    public void set(F flag, IFlag.State state) {
        this.backingMap.put(flag, state);
    }
    
    public Set<Map.Entry<F, IFlag.State>> entrySet() {
        return backingMap.entrySet();
    }
    
    @SafeVarargs
    public static <F extends IFlag> Flags<F> combine(Flags<F> f1, Flags<F>... others) {
        if (others == null || others.length == 0) {
            throw new IllegalArgumentException("Must provide two or more Flags instances");
        }
        
        Flags<F> flags = new Flags<>();
        for (Map.Entry<F, IFlag.State> entry : f1.entrySet()) {
            flags.set(entry.getKey(), entry.getValue());
        }
        
        for (Flags<F> other : others) {
            for (Map.Entry<F, IFlag.State> entry : other.entrySet()) {
                if (!flags.contains(entry.getKey())) {
                    flags.set(entry.getKey(), entry.getValue());
                } else {
                    IFlag.State existingState = flags.get(entry.getKey());
                    IFlag.State newState = entry.getValue();
                    
                    flags.set(entry.getKey(), IFlag.State.check(newState, existingState));
                }
            }
        }
        
        return flags;
    }
}