package com.stardevllc.starlib.eventbus;

/**
 * Represents an event priority
 */
public enum EventPriority {
    /**
     * First to execute
     */
    HIGHEST,
    
    /**
     * Executes after HIGHEST
     */
    HIGH,
    
    /**
     * Executes after HIGH
     */
    NORMAL,
    
    /**
     * Executes after NORMAL
     */
    LOW,
    
    /**
     * Executes after LOW
     */
    LOWEST
}