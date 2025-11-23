package com.stardevllc.starlib.observable.collections.handler;

import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.collections.listener.ListChangeListener;
import com.stardevllc.starlib.observable.collections.listener.ListChangeListener.Change;

import java.util.LinkedList;
import java.util.List;

public class ListListenerHandler<E> {
    private List<ListChangeListener<E>> listeners = new LinkedList<>();
    
    public void addListener(ListChangeListener<E> listener) {
        listeners.add(listener);
    }
    
    public void removeListener(ListChangeListener<E> listener) {
        listeners.remove(listener);
    }
    
    public boolean handleChange(ListChangeListener.Change<E> change) {
        for (ListChangeListener<E> listener : listeners) {
            listener.changed(change);
        }
        
        return change.cancelled().get();
    }
    
    public boolean handleChange(ObservableList<E> collection, int index, E added, E removed) {
        return handleChange(new Change<>(collection, index, added, removed));
    }
}