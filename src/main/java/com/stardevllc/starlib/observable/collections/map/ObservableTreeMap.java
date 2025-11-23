package com.stardevllc.starlib.observable.collections.map;

import com.stardevllc.starlib.observable.collections.list.ObservableLinkedList;
import com.stardevllc.starlib.observable.collections.listener.MapChangeListener;
import com.stardevllc.starlib.observable.collections.set.ObservableTreeSet;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Represents a TreeMap that can be observed for changes
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@SuppressWarnings({"SortedCollectionWithNonComparableKeys", "UnnecessaryLocalVariable"})
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
        return decendingMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<K> navigableKeySet() {
        ObservableTreeSet<K> keySet = new ObservableTreeSet<>(this.backingTreeMap.navigableKeySet());
        return keySet;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<K> descendingKeySet() {
        ObservableTreeSet<K> decendingKeySet = new ObservableTreeSet<>(this.backingTreeMap.descendingKeySet());
        return decendingKeySet;
    }
    
    @SuppressWarnings("DuplicatedCode")
    private class SubMapChangeListener implements MapChangeListener<K, V> {
        
        private final WeakReference<ObservableTreeMap<K, V>> backingMapRef;
        private final WeakReference<ObservableTreeMap<K, V>> subMapRef;
        
        private K fromKey, toKey;
        private boolean fromInclusive, toInclusive; 
        
        public SubMapChangeListener() {
            this(null, null);
        }
        
        public SubMapChangeListener(ObservableTreeMap<K, V> backingMap, ObservableTreeMap<K, V> subMap) {
            this.backingMapRef = new WeakReference<>(backingMap);
            this.subMapRef = new WeakReference<>(subMap);
        }
        
        public SubMapChangeListener(ObservableTreeMap<K, V> backingMap, ObservableTreeMap<K, V> subMap, K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            this(backingMap, subMap);
            this.fromKey = fromKey;
            this.fromInclusive = fromInclusive;
            this.toKey = toKey;
            this.toInclusive = toInclusive;
        }
        
        @Override
        public void changed(Change<K, V> c) {
            ObservableTreeMap<K, V> backingMap = this.backingMapRef.get();
            ObservableTreeMap<K, V> subMap = this.subMapRef.get();
            if (backingMap == null && subMap == null) {
                return;
            } else if (backingMap == null) {
                subMap.removeListener(this);
                return;
            } else if (subMap == null) {
                backingMap.removeListener(this);
                return;
            }
            
            ObservableTreeMap<K, V> mapToChange;
            if (c.map() == backingMap) {
                mapToChange = subMap;
            } else if (c.map() == subMap) {
                mapToChange = backingMap;
            } else {
                return;
            }
            
            if (c.added() != null && c.removed() != null) {
                mapToChange.getBackingMap().remove(c.key());
                mapToChange.getBackingMap().put(c.key(), c.added());
            } else if (c.added() != null) {
                mapToChange.getBackingMap().put(c.key(), c.added());
            } else if (c.removed() != null) {
                mapToChange.getBackingMap().remove(c.key());
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        ObservableTreeMap<K, V> subMap = new ObservableTreeMap<>(this.backingTreeMap.subMap(fromKey, fromInclusive, toKey, toInclusive));
        SubMapChangeListener listener = new SubMapChangeListener(this, subMap, fromKey, fromInclusive, toKey, toInclusive);
        subMap.addListener(listener);
        this.addListener(listener);
        return subMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        ObservableTreeMap<K, V> subMap = new ObservableTreeMap<>(this.backingTreeMap.headMap(toKey, inclusive));
        SubMapChangeListener listener = new SubMapChangeListener(this, subMap, null, true, toKey, inclusive);
        subMap.addListener(listener);
        this.addListener(listener);
        return subMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        ObservableTreeMap<K, V> subMap = new ObservableTreeMap<>(this.backingTreeMap.tailMap(fromKey, inclusive));
        SubMapChangeListener listener = new SubMapChangeListener(this, subMap, fromKey, inclusive, null, false);
        subMap.addListener(listener);
        this.addListener(listener);
        return subMap;
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
        SubMapChangeListener listener = new SubMapChangeListener(this, subMap, fromKey, true, toKey, false);
        subMap.addListener(listener);
        this.addListener(listener);
        return subMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> headMap(K toKey) {
        ObservableTreeMap<K, V> subMap = new ObservableTreeMap<>(this.backingTreeMap.headMap(toKey));
        SubMapChangeListener listener = new SubMapChangeListener(this, subMap, null, true, toKey, false);
        subMap.addListener(listener);
        this.addListener(listener);
        return subMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        ObservableTreeMap<K, V> subMap = new ObservableTreeMap<>(this.backingTreeMap.tailMap(fromKey));
        SubMapChangeListener listener = new SubMapChangeListener(this, subMap, fromKey, true, null, false);
        subMap.addListener(listener);
        this.addListener(listener);
        return subMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableMap<K, V> reversed() {
        ObservableTreeMap<K, V> subMap = new ObservableTreeMap<>(this.backingTreeMap.reversed());
        SubMapChangeListener listener = new SubMapChangeListener(this, subMap, null, false, null, false);
        subMap.addListener(listener);
        this.addListener(listener);
        return subMap;
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