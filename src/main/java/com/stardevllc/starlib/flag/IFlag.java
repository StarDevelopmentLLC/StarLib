package com.stardevllc.starlib.flag;

import org.jetbrains.annotations.NotNull;

public interface IFlag extends Comparable<IFlag> {
    String name();
    
    int ordinal();
    
    boolean equals(IFlag other);
    
    int hashCode();
    
    String toString();
    
    @Override
    default int compareTo(@NotNull IFlag o) {
        return ordinal() - o.ordinal();
    }
    
    enum State {
        NOT_SET, ALLOW, DENY;
        
        public static State check(State s1, State s2) {
            if (s1 == IFlag.State.DENY || s2 == IFlag.State.DENY) {
                return IFlag.State.DENY;
            } else if (s1 == IFlag.State.ALLOW || s2 == IFlag.State.ALLOW) {
                return IFlag.State.ALLOW;
            } else {
                return IFlag.State.NOT_SET;
            }
        }
    }
}