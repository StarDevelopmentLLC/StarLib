package com.stardevllc.starlib.eventbus;

/**
 * Marks an interface cancellable
 */
@FunctionalInterface
public interface ICancellable {
    
    /**
     * Checks the status of the cancellation flag
     * @return The cancellation status
     */
    boolean isCancelled();
    
    /**
     * Sets the cancellation status. Default no-op
     * @param value The value to set
     */
    default void setCancelled(boolean value) {
        
    }
    
    /**
     * Calls setCancelled(true)
     */
    default void cancel() {
        setCancelled(true);
    }
    
    /**
     * Calls setCancelled(false)
     */
    default void uncancel() {
        setCancelled(false);
    }
}