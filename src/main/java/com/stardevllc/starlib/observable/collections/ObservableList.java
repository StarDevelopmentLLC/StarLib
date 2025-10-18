package com.stardevllc.starlib.observable.collections;

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
    default List<E> addContentMirror(List<E> list) {
        list.addAll(this);
        getHandler().addListener((collection, added, removed) -> {
            if (added != null && !list.contains(added)) {
                list.add(added);
            } else if (removed != null && added == null) {
                list.remove(removed);
            }
        });
        
        return list;
    }
}