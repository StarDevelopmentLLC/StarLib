package com.stardevllc.starlib.observable.collections;

import java.util.Set;

/**
 * Represents a set that can be observed for changes
 *
 * @param <E> The element type
 */
public interface ObservableSet<E> extends ObservableCollection<E>, Set<E> {
    
    /**
     * Adds a listener that mirrors changes in this observable set to the set passed in. <br>
     *
     * @param set The set to mirror changes into
     * @return The same set passed in
     */
    default Set<E> addContentMirror(Set<E> set) {
        set.addAll(this);
        getHandler().addListener((collection, added, removed) -> {
            if (added != null) {
                set.add(added);
            } else if (removed != null) {
                set.remove(removed);
            }
        });
        
        return set;
    }
}