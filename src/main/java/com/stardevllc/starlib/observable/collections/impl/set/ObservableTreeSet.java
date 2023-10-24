package com.stardevllc.starlib.observable.collections.impl.set;

import java.util.Collection;
import java.util.TreeSet;

public class ObservableTreeSet<E extends Comparable<E>> extends AbstractObservableSet<E> {
    public ObservableTreeSet() {
        super(new TreeSet<>());
    }
    
    public ObservableTreeSet(Collection<E> collection) {
        super(new TreeSet<>(collection));
    }
}
