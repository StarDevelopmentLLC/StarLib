package com.stardevllc.starlib.observable.collections.impl.list;

import java.util.ArrayList;
import java.util.Collection;

public class ObservableArrayList<T> extends AbstractObservableList<T> {
    public ObservableArrayList() {
        super(new ArrayList<>());
    }
    
    public ObservableArrayList(Collection<T> collection) {
        super(new ArrayList<>(collection));
    }
}
