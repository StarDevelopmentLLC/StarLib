package com.stardevllc.starlib.collections.observable.listener;

import com.stardevllc.starlib.collections.observable.list.ObservableList;
import com.stardevllc.starlib.values.mutable.MutableBoolean;

@FunctionalInterface
public interface ListChangeListener<E> {
    record Change<E>(ObservableList<E> collection, int index, E added, E removed, MutableBoolean cancelled) {
        public Change(ObservableList<E> collection, int index, E added, E removed, MutableBoolean cancelled) {
            this.collection = collection;
            this.index = index;
            this.added = added;
            this.removed = removed;
            if (cancelled == null) {
                this.cancelled = new MutableBoolean();
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