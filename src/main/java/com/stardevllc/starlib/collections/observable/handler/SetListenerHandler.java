package com.stardevllc.starlib.collections.observable.handler;

import com.stardevllc.starlib.collections.observable.set.ObservableSet;
import com.stardevllc.starlib.collections.observable.listener.SetChangeListener;
import com.stardevllc.starlib.collections.observable.listener.SetChangeListener.Change;

import java.util.LinkedList;
import java.util.List;

public class SetListenerHandler<E> {
    private List<SetChangeListener<E>> listeners = new LinkedList<>();
    
    public void addListener(SetChangeListener<E> listener) {
        listeners.add(listener);
    }
    
    public void removeListener(SetChangeListener<E> listener) {
        listeners.remove(listener);
    }
    
    public boolean handleChange(Change<E> change) {
        for (SetChangeListener<E> listener : listeners) {
            listener.changed(change);
        }
        
        return change.cancelled().get();
    }
    
    public boolean handleChange(ObservableSet<E> collection, E added, E removed) {
        return handleChange(new Change<>(collection, added, removed));
    }
}