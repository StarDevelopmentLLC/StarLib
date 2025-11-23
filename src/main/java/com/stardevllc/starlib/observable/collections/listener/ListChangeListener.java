package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.value.WritableBooleanValue;
import com.stardevllc.starlib.value.impl.SimpleBooleanValue;

@FunctionalInterface
public interface ListChangeListener<E> {
    record Change<E>(ObservableList<E> collection, int index, E added, E removed, WritableBooleanValue cancelled) {
        public Change(ObservableList<E> collection, int index, E added, E removed, WritableBooleanValue cancelled) {
            this.collection = collection;
            this.index = index;
            this.added = added;
            this.removed = removed;
            if (cancelled == null) {
                this.cancelled = new SimpleBooleanValue();
            } else {
                this.cancelled = cancelled;
            }
        }
        
        public Change(ObservableList<E> collection, int index, E added, E removed) {
            this(collection, index, added, removed, null);
        }
    }
    
    /**
     * Called when changes occur
     *
     * @param change The change information
     */
    void changed(Change<E> change);
}