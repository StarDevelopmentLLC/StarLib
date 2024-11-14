package com.stardevllc.observable.collections;

import java.util.Collection;
import java.util.HashSet;

public class ObservableHashSet<E> extends AbstractObservableSet<E> {
    public ObservableHashSet() {
        super(new HashSet<>());
    }

    public ObservableHashSet(Collection<E> collection) {
        this();
        this.backingSet.addAll(collection);
    }
}
