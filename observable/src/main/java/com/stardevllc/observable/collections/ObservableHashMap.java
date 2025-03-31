package com.stardevllc.observable.collections;

import java.util.*;

public class ObservableHashMap<K, V> extends AbstractObservableMap<K, V> {
    public ObservableHashMap() {
        super(new HashMap<>());
    }
    
    public ObservableHashMap(Map<K, V> map) {
        this();
        this.backingMap.putAll(map);
    }

    @Override
    public Set<K> keySet() {
        return new ObservableHashSet<>(this.backingMap.keySet());
    }

    @Override
    public Collection<V> values() {
        return new ObservableLinkedList<>(this.backingMap.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new ObservableHashSet<>(this.backingMap.entrySet());
    }
}
