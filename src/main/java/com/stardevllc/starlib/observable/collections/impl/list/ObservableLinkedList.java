package com.stardevllc.starlib.observable.collections.impl.list;

import java.util.Collection;
import java.util.LinkedList;

public class ObservableLinkedList<T> extends AbstractObservableList<T> {
    public ObservableLinkedList() {
        super(new LinkedList<>());
    }

    public ObservableLinkedList(Collection<T> collection) {
        super(new LinkedList<>(collection));
    }
}
