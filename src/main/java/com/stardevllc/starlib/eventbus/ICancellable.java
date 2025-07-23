package com.stardevllc.starlib.eventbus;

@FunctionalInterface
public interface ICancellable {
    boolean isCancelled();
    default void setCancelled(boolean value) {
        
    }
    
    default void cancel() {
        setCancelled(true);
    }
    
    default void uncancel() {
        setCancelled(false);
    }
}