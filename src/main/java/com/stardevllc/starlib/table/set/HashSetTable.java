package com.stardevllc.starlib.table.set;

import com.stardevllc.starlib.table.HashTable;

import java.util.*;

public class HashSetTable<R, C, V> extends HashTable<R, C, Set<V>> implements SetTable<R, C, V> {
    
    @Override
    public int size() {
        int size = 0;
        
        for (Map.Entry<R, HashMap<C, Set<V>>> e1 : this.backingMap.entrySet()) {
            for (Map.Entry<C, Set<V>> e2 : e1.getValue().entrySet()) {
                size += e2.getValue().size();
            }
        }
        
        return size;
    }
    
    @Override
    public void add(R row, C column, V value) {
        Set<V> set = computeIfAbsent(row, column, (r, c) -> new LinkedHashSet<>());
        set.add(value);
    }
    
    @Override
    public void remove(R row, C column, V value) {
        Set<V> set = get(row, column);
        if (set != null) {
            set.remove(value);
            if (set.isEmpty()) {
                remove(row, column);
            }
        }
    }
    
    @Override
    public Collection<V> elements() {
        return new Elements();
    }
    
    protected class ElementIterator implements Iterator<V> {
        
        private final ValueIterator valueIterator;
        
        private Iterator<V> currentListIterator;
        
        public ElementIterator() {
            this.valueIterator = new ValueIterator();
        }
        
        @Override
        public boolean hasNext() {
            if (this.currentListIterator.hasNext()) {
                return true;
            }
            
            if (this.valueIterator.hasNext()) {
                currentListIterator = valueIterator.next().iterator();
                return hasNext();
            }
            
            return false;
        }
        
        @Override
        public V next() {
            return currentListIterator.next();
        }
        
        @Override
        public void remove() {
            currentListIterator.remove();
        }
    }
    
    protected class Elements extends AbstractCollection<V> {
        @Override
        public Iterator<V> iterator() {
            return new ElementIterator();
        }
        
        @Override
        public int size() {
            return HashSetTable.this.size();
        }
    }
}
