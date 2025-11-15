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
    default <S extends Set<E>> S addContentMirror(S set) {
        set.addAll(this);
        getHandler().addListener(c -> {
            if (c.added() != null) {
                set.add(c.added());
            } else if (c.removed() != null) {
                set.remove(c.removed());
            }
        });
        
        return set;
    }
}