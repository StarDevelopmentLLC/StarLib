package com.stardevllc.starlib.observable.collections;

import java.util.*;

/**
 * Represents a TreeMap that can be observed for changes
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@SuppressWarnings("SortedCollectionWithNonComparableKeys")
public class ObservableTreeMap<K, V> extends AbstractObservableMap<K, V> implements NavigableMap<K, V> {
    
    private final TreeMap<K, V> backingTreeMap = new TreeMap<>();
    
    /**
     * Creates an empty observable tree map
     */
    public ObservableTreeMap() {
    }
    
    /**
     * Creates an observable tree map from an existing map
     *
     * @param map The map
     */
    public ObservableTreeMap(Map<K, V> map) {
        this.backingTreeMap.putAll(map);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<K, V> getBackingMap() {
        return backingTreeMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<K, V> lowerEntry(K key) {
        return this.backingTreeMap.lowerEntry(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public K lowerKey(K key) {
        return this.backingTreeMap.lowerKey(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<K, V> floorEntry(K key) {
        return this.backingTreeMap.floorEntry(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public K floorKey(K key) {
        return this.backingTreeMap.floorKey(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return this.backingTreeMap.ceilingEntry(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public K ceilingKey(K key) {
        return this.backingTreeMap.ceilingKey(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<K, V> higherEntry(K key) {
        return this.backingTreeMap.higherEntry(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public K higherKey(K key) {
        return this.backingTreeMap.higherKey(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<K, V> firstEntry() {
        return this.backingTreeMap.firstEntry();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<K, V> lastEntry() {
        return this.backingTreeMap.lastEntry();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<K, V> pollFirstEntry() {
        Entry<K, V> entry = this.backingTreeMap.pollFirstEntry();
        this.handler.handleChange(this, entry.getKey(), null, entry.getValue());
        return entry;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<K, V> pollLastEntry() {
        Entry<K, V> entry = this.backingTreeMap.pollLastEntry();
        this.handler.handleChange(this, entry.getKey(), null, entry.getValue());
        return entry;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> descendingMap() {
        return new ObservableTreeMap<>(this.backingTreeMap.descendingMap());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<K> navigableKeySet() {
        return new ObservableTreeSet<>(this.backingTreeMap.navigableKeySet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<K> descendingKeySet() {
        return new ObservableTreeSet<>(this.backingTreeMap.descendingKeySet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return new ObservableTreeMap<>(this.backingTreeMap.subMap(fromKey, fromInclusive, toKey, toInclusive));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return new ObservableTreeMap<>(this.backingTreeMap.headMap(toKey, inclusive));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return new ObservableTreeMap<>(this.backingTreeMap.tailMap(fromKey, inclusive));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<? super K> comparator() {
        return this.backingTreeMap.comparator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return new ObservableTreeMap<>(this.backingTreeMap.subMap(fromKey, toKey));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return new ObservableTreeMap<>(this.backingTreeMap.headMap(toKey));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return new ObservableTreeMap<>(this.backingTreeMap.tailMap(fromKey));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> reversed() {
        return new ObservableTreeMap<>(this.backingTreeMap.reversed());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public K firstKey() {
        return this.backingTreeMap.firstKey();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public K lastKey() {
        return this.backingTreeMap.lastKey();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        return new ObservableTreeSet<>(this.backingTreeMap.keySet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        return new ObservableLinkedList<>(this.backingTreeMap.values());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new ObservableTreeSet<>(this.backingTreeMap.entrySet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V putFirst(K k, V v) {
        V r = this.backingTreeMap.putFirst(k, v);
        this.handler.handleChange(this, k, v, r);
        return r;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V putLast(K k, V v) {
        V r = this.backingTreeMap.putLast(k, v);
        this.handler.handleChange(this, k, v, r);
        return r;
    }
}