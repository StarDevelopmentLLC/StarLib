package com.stardevllc.starlib.observable.collections.impl.set;

import java.util.Collection;
import java.util.HashSet;

public class ObservableHashSet<E> extends AbstractObservableSet<E> {
    public ObservableHashSet() {
        super(new HashSet<>());
    }

    public ObservableHashSet(Collection<E> collection) {
        super(new HashSet<>(collection));
    }
}
