package com.stardevllc.starlib.observable.collections.impl.map;

import java.util.Map;
import java.util.TreeMap;

public class ObservableTreeMap<K extends Comparable<K>, V> extends AbstractObservableMap<K, V> {
    public ObservableTreeMap() {
        super(new TreeMap<>());
    }

    public ObservableTreeMap(Map<K, V> value) {
        super(new TreeMap<>(value));
    }
}
