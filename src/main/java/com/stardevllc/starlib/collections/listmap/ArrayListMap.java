package com.stardevllc.starlib.collections.listmap;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public class ArrayListMap<K, V> implements ListMap<K, V> {
    
    private final Map<K, ArrayList<V>> backingMap = new HashMap<>();
    
    @Override
    public int size() {
        int size = 0;
        for (ArrayList<V> list : this.backingMap.values()) {
            size += list.size();
        }
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }
    
    @Override
    public boolean containsKey(K key) {
        return backingMap.containsKey(key);
    }
    
    @Override
    public boolean containsValue(V value) {
        for (ArrayList<V> list : this.backingMap.values()) {
            if (list.contains(value)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public V get(K key, int index) {
        ArrayList<V> list = this.backingMap.get(key);
        if (list == null) {
            return null;
        }
        
        return list.get(index);
    }
    
    @Override
    public List<V> get(K key) {
        return this.backingMap.get(key);
    }
    
    @Override
    public V remove(K key, int index) {
        ArrayList<V> list = this.backingMap.get(key);
        if (list == null) {
            return null;
        }
        
        V remove = list.remove(index);
        if (list.isEmpty()) {
            this.backingMap.remove(key);
        }
        return remove;
    }
    
    @Override
    public boolean remove(K key, V value) {
        ArrayList<V> list = this.backingMap.get(key);
        if (list == null) {
            return false;
        }
        
        return list.remove(value);
    }
    
    @Override
    public void add(K key, V value) {
        this.backingMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }
    
    @Override
    public void clear() {
        this.backingMap.clear();
    }
    
    @Override
    public @NotNull Set<K> keySet() {
        return this.backingMap.keySet();
    }
    
    @Override
    public @NotNull Collection<V> values() {
        return new Values();
    }
    
    @Override
    public void forEach(BiConsumer<K, V> consumer) {
        for (Map.Entry<K, ArrayList<V>> entry : this.backingMap.entrySet()) {
            for (V v : entry.getValue()) {
                consumer.accept(entry.getKey(), v);
            }
        }
    }
    
    private class ValuesIterator implements Iterator<V> {
        
        private final Iterator<ArrayList<V>> listIterator;
        
        private ArrayList<V> currentList;
        private Iterator<V> currentIterator;
        
        public ValuesIterator() {
            this.listIterator = backingMap.values().iterator();
        }
        
        @Override
        public boolean hasNext() {
            //Check to see if the current iterator is null
            if (this.currentIterator == null) {
                //If it is, check the list iterator for a next
                if (!listIterator.hasNext()) {
                    //If not, return false
                    return false;
                }
                
                //Get and store the current list
                currentList = listIterator.next();
                
                //Check to see if the list is null, if it is, skip and try again
                //This should accountt for null mappings without throwing an exception
                if (currentList == null) {
                    return hasNext();
                }
                
                //If it does, get the next iterator
                currentIterator = currentList.iterator();
                //Recursive call to prevent having to repeat code
                return hasNext();
            }
            
            //Check if the current iterator does not have a next value
            if (!currentIterator.hasNext()) {
                //If it doesn't then set it to null and recursive call this method which will handle the logic of cycling the iterator
                currentIterator = null;
                return hasNext();
            }
            
            return true;
        }
        
        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            if (this.currentIterator == null) {
                throw new NullPointerException("currentIterator is null");
            }
            
            return this.currentIterator.next();
        }
        
        @Override
        public void remove() {
            //If the current iterator is null, throw an NPE as this shouldn't be called without one
            if (this.currentIterator == null) {
                throw new NullPointerException("currentIterator is null");
            }
            
            //Remove the element from the iterator
            this.currentIterator.remove();
            
            //Check to see if it is empty now
            if (this.currentList.isEmpty()) {
                //If it is, remove from the iterator of lists and set the currentList and currentIterator to null
                listIterator.remove();
                this.currentList = null;
                this.currentIterator = null;
            }
        }
    }
    
    private class Values extends AbstractCollection<V> {
        
        @Override
        public Iterator<V> iterator() {
            return new ValuesIterator();
        }
        
        @Override
        public int size() {
            return ArrayListMap.this.size();
        }
    }
}
