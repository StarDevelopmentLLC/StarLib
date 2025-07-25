package com.stardevllc.starlib.observable.collections;

import java.util.*;

public class ObservableArrayList<E> extends AbstractObservableList<E> {
    
    protected final ArrayList<E> backingArrayList = new ArrayList<>();

    public ObservableArrayList() {
    }
    
    public ObservableArrayList(Collection<E> collection) {
        backingArrayList.addAll(collection);
    }
    
    @Override
    protected List<E> getBackingList() {
        return backingArrayList;
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
