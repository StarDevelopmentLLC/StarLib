package com.stardevllc.starlib.observable.collections.handler;

import com.stardevllc.starlib.observable.collections.ObservableCollection;
import com.stardevllc.starlib.observable.collections.listener.CollectionChangeListener;

import java.util.ArrayList;
import java.util.List;

public class CollectionListenerHandler<E> {
    private final List<CollectionChangeListener<E>> listeners = new ArrayList<>();
    
    public void addListener(CollectionChangeListener<E> listener) {
        this.listeners.add(listener);
    }
    
    public void removeListener(CollectionChangeListener<E> listener) {
        this.listeners.remove(listener);
    }
    
    public void handleChange(ObservableCollection<E> collection, E added, E removed) {
        for (CollectionChangeListener<E> listener : listeners) {
            listener.changed(collection, added, removed);
        }
    }
}