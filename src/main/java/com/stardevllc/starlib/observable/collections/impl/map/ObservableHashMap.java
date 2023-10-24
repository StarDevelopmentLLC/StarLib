package com.stardevllc.starlib.observable.collections.impl.map;

import java.util.HashMap;
import java.util.Map;

public class ObservableHashMap<K, V> extends AbstractObservableMap<K, V> {
    public ObservableHashMap() {
        super(new HashMap<>());
    }

    public ObservableHashMap(Map<K, V> value) {
        super(new HashMap<>(value));
    }
}
