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
    default <L extends List<E>> L addContentMirror(L list) {
        list.addAll(this);
        getHandler().addListener(c -> {
            if (c.added() != null && !list.contains(c.added())) {
                list.add(c.added());
            } else if (c.removed() != null && c.added() == null) {
                list.remove(c.removed());
            }
        });
        
        return list;
    }
}