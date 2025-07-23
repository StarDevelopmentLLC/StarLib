package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.event.MapChangeEvent;

import java.util.*;

@SuppressWarnings("SortedCollectionWithNonComparableKeys")
public class ObservableTreeMap<K, V> extends AbstractObservableMap<K, V> implements NavigableMap<K, V> {
    
    protected final TreeMap<K, V> backingTreeMap = new TreeMap<>();
    
    public ObservableTreeMap() {
    }

    public ObservableTreeMap(Map<K, V> map) {
       this.backingTreeMap.putAll(map);
    }
    
    @Override
    protected Map<K, V> getBackingMap() {
        return backingTreeMap;
    }
    
    @Override
    public Entry<K, V> lowerEntry(K key) {
        return this.backingTreeMap.lowerEntry(key);
    }

    @Override
    public K lowerKey(K key) {
        return this.backingTreeMap.lowerKey(key);
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        return this.backingTreeMap.floorEntry(key);
    }

    @Override
    public K floorKey(K key) {
        return this.backingTreeMap.floorKey(key);
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return this.backingTreeMap.ceilingEntry(key);
    }

    @Override
    public K ceilingKey(K key) {
        return this.backingTreeMap.ceilingKey(key);
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        return this.backingTreeMap.higherEntry(key);
    }

    @Override
    public K higherKey(K key) {
        return this.backingTreeMap.higherKey(key);
    }

    @Override
    public Entry<K, V> firstEntry() {
        return this.backingTreeMap.firstEntry();
    }

    @Override
    public Entry<K, V> lastEntry() {
        return this.backingTreeMap.lastEntry();
    }

    @Override
    public Entry<K, V> pollFirstEntry() {
        Entry<K, V> entry = this.backingTreeMap.pollFirstEntry();
        this.eventBus.post(new MapChangeEvent<>(this, entry.getKey(), null, entry.getValue()));
        return entry;
    }

    @Override
    public Entry<K, V> pollLastEntry() {
        Entry<K, V> entry = this.backingTreeMap.pollLastEntry();
        this.eventBus.post(new MapChangeEvent<>(this, entry.getKey(), null, entry.getValue()));
        return entry;
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        return new ObservableTreeMap<>(this.backingTreeMap.descendingMap());
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return new ObservableTreeSet<>(this.backingTreeMap.navigableKeySet());
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return new ObservableTreeSet<>(this.backingTreeMap.descendingKeySet());
    }

    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return new ObservableTreeMap<>(this.backingTreeMap.subMap(fromKey, fromInclusive, toKey, toInclusive));
    }

    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return new ObservableTreeMap<>(this.backingTreeMap.headMap(toKey, inclusive));
    }

    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return new ObservableTreeMap<>(this.backingTreeMap.tailMap(fromKey, inclusive));
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.backingTreeMap.comparator();
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return new ObservableTreeMap<>(this.backingTreeMap.subMap(fromKey, toKey));
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return new ObservableTreeMap<>(this.backingTreeMap.headMap(toKey));
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return new ObservableTreeMap<>(this.backingTreeMap.tailMap(fromKey));
    }

    @Override
    public NavigableMap<K, V> reversed() {
        return new ObservableTreeMap<>(this.backingTreeMap.reversed());
    }

    @Override
    public K firstKey() {
        return this.backingTreeMap.firstKey();
    }

    @Override
    public K lastKey() {
        return this.backingTreeMap.lastKey();
    }

    @Override
    public Set<K> keySet() {
        return new ObservableTreeSet<>(this.backingTreeMap.keySet());
    }

    @Override
    public Collection<V> values() {
        return new ObservableLinkedList<>(this.backingTreeMap.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new ObservableTreeSet<>(this.backingTreeMap.entrySet());
    }

    @Override
    public V putFirst(K k, V v) {
        V r = this.backingTreeMap.putFirst(k, v);
        this.eventBus.post(new MapChangeEvent<>(this, k, v, r));
        return r;
    }

    @Override
    public V putLast(K k, V v) {
        V r = this.backingTreeMap.putLast(k, v);
        this.eventBus.post(new MapChangeEvent<>(this, k, v, r));
        return r;
    }
}
