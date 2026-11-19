package com.stardevllc.starlib.flag;

import java.util.*;

public class Flags<F extends IFlag> {
    private final Map<F, IFlag.State> backingMap = new HashMap<>();
    
    private Flags<F> parent;
    
    public Flags() {}
    
    public Flags(Flags<F> parent) {
        this.parent = parent;
    }
    
    public boolean contains(F flag) {
        boolean localContains = backingMap.containsKey(flag);
        boolean parentContains = false;
        if (parent != null) {
            parentContains = parent.contains(flag);
        }
        return localContains || parentContains;
    }
    
    public IFlag.State get(F flag) {
        IFlag.State localState = backingMap.getOrDefault(flag, IFlag.State.NOT_SET);
        IFlag.State parentState = parent == null ? IFlag.State.NOT_SET : parent.get(flag);
        
        return IFlag.State.check(localState, parentState);
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