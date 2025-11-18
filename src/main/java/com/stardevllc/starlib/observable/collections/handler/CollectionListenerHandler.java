package com.stardevllc.starlib.observable.collections.handler;

import com.stardevllc.starlib.observable.collections.ObservableCollection;
import com.stardevllc.starlib.observable.collections.listener.CollectionChangeListener;
import com.stardevllc.starlib.observable.collections.listener.CollectionChangeListener.Change;

import java.util.*;

/**
 * Handles the listener stuff for collections
 *
 * @param <E> The element type
 */
@SuppressWarnings("RedundantNoArgConstructor")
public class CollectionListenerHandler<E> {
    private final List<CollectionChangeListener<E>> listeners = new ArrayList<>();
    
    /**
     * Constructs a new handler
     */
    public CollectionListenerHandler() {
        
    }
    
    /**
     * Adds a listener to this handler
     *
     * @param listener The listener
     */
    public void addListener(CollectionChangeListener<E> listener) {
        this.listeners.add(listener);
    }
    
    /**
     * Removes a listener from this handler
     *
     * @param listener The listener
     */
    public void removeListener(CollectionChangeListener<E> listener) {
        this.listeners.remove(listener);
    }
    
    /**
     * Handles the change in the collection
     *
     * @param change The change information
     * @return If the change was cancelled
     */
    public boolean handleChange(CollectionChangeListener.Change<E> change) {
        boolean cancelled = false;
        for (CollectionChangeListener<E> listener : listeners) {
            listener.changed(change);
            if (!cancelled) {
                cancelled = change.cancelled().get();
            }
        }
        
        return cancelled;
    }
    
    /**
     * Handles the change on a collection. Delegates to {@link #handleChange(Change)}
     *
     * @param collection The source collection
     * @param added      The added element
     * @param removed    The removed element
     */
    public boolean handleChange(ObservableCollection<E> collection, E added, E removed) {
        return handleChange(new Change<>(collection, added, removed));
    }
}