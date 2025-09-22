package com.stardevllc.starlib.fieldwatcher;

import java.util.HashSet;
import java.util.Set;

/**
 * A manager for field watches. This is not required to be used, but is a default implementation
 */
public class WatcherManager {
    private final Set<FieldWatcher> fieldWatchers = new HashSet<>();
    
    /**
     * Constructs a new watcher manager
     */
    public WatcherManager() {
    }
    
    /**
     * Creates a field watcher
     *
     * @param object    The object that holds the field
     * @param fieldName the name of the field
     * @return The field watcher instance
     */
    public FieldWatcher watchField(Object object, String fieldName) {
        FieldWatcher watcher = new FieldWatcher(object, fieldName);
        if (!this.fieldWatchers.contains(watcher)) {
            this.fieldWatchers.add(watcher);
            return watcher;
        } else {
            for (FieldWatcher fieldWatcher : this.fieldWatchers) {
                if (fieldWatcher.equals(watcher)) {
                    return fieldWatcher;
                }
            }
        }
        
        return null;
    }
}
