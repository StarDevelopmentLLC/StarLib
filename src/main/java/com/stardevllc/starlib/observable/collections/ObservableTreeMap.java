package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.listener.MapChangeListener;

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
        if (map != null) {
            this.backingTreeMap.putAll(map);
        }
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
    public Map.Entry<K, V> lowerEntry(K key) {
        return new ObservableEntry<>(this, this.backingTreeMap.lowerEntry(key));
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
    public Map.Entry<K, V> floorEntry(K key) {
        return new ObservableEntry<>(this, this.backingTreeMap.floorEntry(key));
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
    public Map.Entry<K, V> ceilingEntry(K key) {
        return new ObservableEntry<>(this, this.backingTreeMap.ceilingEntry(key));
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
    public Map.Entry<K, V> higherEntry(K key) {
        return new ObservableEntry<>(this, this.backingTreeMap.higherEntry(key));
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
    public Map.Entry<K, V> firstEntry() {
        return new ObservableEntry<>(this, this.backingTreeMap.firstEntry());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Map.Entry<K, V> lastEntry() {
        return new ObservableEntry<>(this, this.backingTreeMap.lastEntry());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        Map.Entry<K, V> entry = new ObservableEntry<>(this, this.backingTreeMap.pollFirstEntry());
        this.handler.handleChange(this, entry.getKey(), null, entry.getValue());
        return entry;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Map.Entry<K, V> pollLastEntry() {
        Map.Entry<K, V> entry = new ObservableEntry<>(this, this.backingTreeMap.pollLastEntry());
        this.handler.handleChange(this, entry.getKey(), null, entry.getValue());
        return entry;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> descendingMap() {
        ObservableTreeMap<K, V> decendingMap = new ObservableTreeMap<>(this.backingTreeMap.descendingMap());
        decendingMap.addListener(c -> {
            if (c.added() != null) {
                put(c.key(), c.added());
            } else if (c.removed() != null) {
                remove(c.key());
            }
        });
        return decendingMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<K> navigableKeySet() {
        ObservableTreeSet<K> keySet = new ObservableTreeSet<>(this.backingTreeMap.navigableKeySet());
        keySet.addListener(c -> {
            if (c.removed() != null) {
                remove(c.removed());
            }
        });
        return keySet;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<K> descendingKeySet() {
        ObservableTreeSet<K> decendingKeySet = new ObservableTreeSet<>(this.backingTreeMap.descendingKeySet());
        decendingKeySet.addListener(c -> {
            if (c.removed() != null) {
                remove(c.removed());
            }
        });
        return decendingKeySet;
    }
    
    private class SubMapChangeListener implements MapChangeListener<K, V> {
        @Override
        public void changed(Change<K, V> c) {
            if (c.added() != null) {
                put(c.key(), c.added());
            } else if (c.removed() != null) {
                remove(c.key());
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        ObservableTreeMap<K, V> subMap = new ObservableTreeMap<>(this.backingTreeMap.subMap(fromKey, fromInclusive, toKey, toInclusive));
        subMap.addListener(new SubMapChangeListener());
        return subMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        ObservableTreeMap<K, V> headMap = new ObservableTreeMap<>(this.backingTreeMap.headMap(toKey, inclusive));
        headMap.addListener(new SubMapChangeListener());
        return headMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        ObservableTreeMap<K, V> tailMap = new ObservableTreeMap<>(this.backingTreeMap.tailMap(fromKey, inclusive));
        tailMap.addListener(new SubMapChangeListener());
        return tailMap;
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
        ObservableTreeMap<K, V> subMap = new ObservableTreeMap<>(this.backingTreeMap.subMap(fromKey, toKey));
        subMap.addListener(new SubMapChangeListener());
        return subMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> headMap(K toKey) {
        ObservableTreeMap<K, V> headMap = new ObservableTreeMap<>(this.backingTreeMap.headMap(toKey));
        headMap.addListener(new SubMapChangeListener());
        return headMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        ObservableTreeMap<K, V> tailMap = new ObservableTreeMap<>(this.backingTreeMap.tailMap(fromKey));
        tailMap.addListener(new SubMapChangeListener());
        return tailMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> reversed() {
        ObservableTreeMap<K, V> reversed = new ObservableTreeMap<>(this.backingTreeMap.reversed());
        reversed.addListener(new SubMapChangeListener());
        return reversed;
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
        ObservableTreeSet<K> keySet = new ObservableTreeSet<>(this.backingTreeMap.keySet());
        keySet.addListener(c -> {
            if (c.added() != null) {
                put(c.added(), null);
            } else if (c.removed() != null) {
                remove(c.removed());
            }
        });
        return keySet;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        ObservableLinkedList<V> values = new ObservableLinkedList<>(this.backingTreeMap.values());
        values.addListener(c -> {
            if (c.removed() != null) {
                this.backingTreeMap.values().remove(c.removed());
            }
        });
        return values;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        ObservableTreeSet<Map.Entry<K, V>> entrySet = new ObservableTreeSet<>();
        for (Map.Entry<K, V> entry : this.backingTreeMap.entrySet()) {
            entrySet.add(new ObservableEntry<>(this, entry));
        }
        return entrySet;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V putFirst(K k, V v) {
        V r = this.backingTreeMap.putFirst(k, v);
        boolean cancelled = this.handler.handleChange(this, k, v, r);
        if (cancelled) {
            this.backingTreeMap.putFirst(k, r);
            return null;
        }
        return r;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V putLast(K k, V v) {
        V r = this.backingTreeMap.putLast(k, v);
        boolean cancelled = this.handler.handleChange(this, k, v, r);
        if (cancelled) {
            this.backingTreeMap.putLast(k, r);
            return null;
        }
        return r;
    }
}