package com.stardevllc.starlib.observable.collections.listeners;

import com.stardevllc.starlib.observable.collections.ObservableMap;

@FunctionalInterface
public interface MapChangeListener<K, V> {
    void onChange(Change<K, V> change);
    
    class Change<K, V> {
        private final ObservableMap<K, V> map;
        private K key;
        private V added, removed;

        public Change(ObservableMap<K, V> map) {
            this.map = map;
        }

        public Change(ObservableMap<K, V> map, K key, V added, V removed) {
            this.map = map;
            this.key = key;
            this.added = added;
            this.removed = removed;
        }

        public ObservableMap<K, V> getMap() {
            return map;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValueAdded() {
            return added;
        }

        public void setValueAdded(V added) {
            this.added = added;
        }

        public V getValueRemoved() {
            return removed;
        }

        public void setValueRemoved(V removed) {
            this.removed = removed;
        }
    }
}
