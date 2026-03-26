package com.stardevllc.starlib.table.list;

import com.stardevllc.starlib.table.HashTable;

import java.util.*;

public class LinkedListHashTable<R, C, V> extends HashTable<R, C, List<V>> implements ListTable<R, C, V> {
    
    @Override
    public int size() {
        int size = 0;
        
        for (Map.Entry<R, LinkedHashMap<C, List<V>>> e1 : this.backingMap.entrySet()) {
            for (Map.Entry<C, List<V>> e2 : e1.getValue().entrySet()) {
                size += e2.getValue().size();
            }
        }
        
        return size;
    }
    
    @Override
    public void add(R row, C column, V value) {
        List<V> list = computeIfAbsent(row, column, (r, c) -> new LinkedList<>());
        list.add(value);
    }
    
    @Override
    public void add(R row, C column, int index, V value) {
        List<V> list = computeIfAbsent(row, column, (r, c) -> new LinkedList<>());
        list.add(index, value);
    }
    
    @Override
    public V set(R row, C column, int index, V value) {
        List<V> list = computeIfAbsent(row, column, (r, c) -> new LinkedList<>());
        return list.set(index, value);
    }
    
    @Override
    public V remove(R row, C column, int index) {
        List<V> list = get(row, column);
        if (list != null) {
            return list.remove(index);
        }
        
        return null;
    }
    
    @Override
    public void remove(R row, C column, V value) {
        List<V> list = get(row, column);
        if (list != null) {
            list.remove(value);
        }
    }
    
    @Override
    public V get(R row, C column, int index) {
        List<V> list = get(row, column);
        if (list != null) {
            return list.get(index);
        }
        
        return null;
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
            return LinkedListHashTable.this.size();
        }
    }
}
