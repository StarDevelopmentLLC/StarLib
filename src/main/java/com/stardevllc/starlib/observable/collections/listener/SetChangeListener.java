package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.observable.collections.set.ObservableSet;
import com.stardevllc.starlib.value.WritableBooleanValue;
import com.stardevllc.starlib.value.impl.SimpleBooleanValue;

@FunctionalInterface
public interface SetChangeListener<E> {
    record Change<E>(ObservableSet<E> collection, E added, E removed, WritableBooleanValue cancelled) {
        public Change(ObservableSet<E> collection, E added, E removed, WritableBooleanValue cancelled) {
            this.collection = collection;
            this.added = added;
            this.removed = removed;
            if (cancelled == null) {
                this.cancelled = new SimpleBooleanValue();
            } else {
                this.cancelled = cancelled;
            }
        }
        
        public Change(ObservableSet<E> collection, E added, E removed) {
            this(collection, added, removed, null);
        }
    }
    
    /**
     * Called when changes occur
     *
     * @param change The change information
     */
    void changed(Change<E> change);
}