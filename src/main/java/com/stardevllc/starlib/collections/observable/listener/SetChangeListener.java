package com.stardevllc.starlib.collections.observable.listener;

import com.stardevllc.starlib.collections.observable.set.ObservableSet;
import com.stardevllc.starlib.values.mutable.MutableBoolean;

@FunctionalInterface
public interface SetChangeListener<E> {
    record Change<E>(ObservableSet<E> collection, E added, E removed, MutableBoolean cancelled) {
        public Change(ObservableSet<E> collection, E added, E removed, MutableBoolean cancelled) {
            this.collection = collection;
            this.added = added;
            this.removed = removed;
            if (cancelled == null) {
                this.cancelled = new MutableBoolean();
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