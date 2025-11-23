package com.stardevllc.starlib.observable.collections.list;

import com.stardevllc.starlib.observable.collections.ObservableCollection;
import com.stardevllc.starlib.observable.collections.listener.ListChangeListener;

import java.util.List;

/**
 * Represents a list that can be observed for changes
 *
 * @param <E> The element type
 */
public interface ObservableList<E> extends ObservableCollection<E>, List<E> {
    
    /**
     * Adds a listener that mirrors the content in this observable list to the list passed in
     *
     * @param list The list to modify
     * @return The list that was passed in. (To allow single line uses)
     */
    <L extends List<E>> L addContentMirror(L list);
    
    void addListener(ListChangeListener<E> changeListener);
    
    void removeListener(ListChangeListener<E> changeListener);
}