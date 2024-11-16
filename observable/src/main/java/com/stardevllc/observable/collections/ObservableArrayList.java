package com.stardevllc.observable.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObservableArrayList<E> extends AbstractObservableList<E> {
    
    protected final ArrayList<E> backingArrayList;

    public ObservableArrayList() {
        super(new ArrayList<>());
        this.backingArrayList = (ArrayList<E>) this.backingList;
    }
    
    public ObservableArrayList(Collection<E> collection) {
        this();
        this.backingList.addAll(collection);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new ObservableArrayList<>(this.backingArrayList.subList(fromIndex, toIndex));
    }

    @Override
    public List<E> reversed() {
        return new ObservableArrayList<>(this.backingArrayList.reversed());
    }
}
