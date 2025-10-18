package com.stardevllc.starlib.observable.collections.handler;

import com.stardevllc.starlib.observable.collections.ObservableCollection;
import com.stardevllc.starlib.observable.collections.listener.CollectionChangeListener;

import java.util.ArrayList;
import java.util.List;

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
     * Handles the change on a collection
     *
     * @param collection The source collection
     * @param added      The added element
     * @param removed    The removed element
     */
    public void handleChange(ObservableCollection<E> collection, E added, E removed) {
        for (CollectionChangeListener<E> listener : listeners) {
            listener.changed(collection, added, removed);
        }
    }
}