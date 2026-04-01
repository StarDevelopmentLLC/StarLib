package com.stardevllc.starlib.collections;

import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("SuspiciousMethodCalls")
public class HashArrayList<E> implements Iterable<E> {
    private final List<E> list = new ArrayList<>();
    private final Set<E> set = new HashSet<>();
    
    public int size() {
        return list.size();
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public boolean contains(Object o) {
        return set.contains(o);
    }
    
    @Override
    public @NotNull Iterator<E> iterator() {
        return new HashArrayIterator(this.list.listIterator());
    }
    
    public @NotNull Object[] toArray() {
        return list.toArray(); 
    }
    
    public @NotNull <T> T[] toArray(@NotNull T[] a) {
        return list.toArray(a);
    }
    
    public boolean add(E e) {
        if (set.add(e)) {
            list.add(e);
            return true;
        }
        
        return false;
    }
    
    public boolean remove(Object o) {
        set.remove(o);
        return list.remove(o);
    }
    
    public boolean containsAll(@NotNull Collection<?> c) {
        return set.containsAll(c);
    }
    
    public boolean addAll(@NotNull Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            add(e);
        }
        return modified;
    }
    
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        return addAll(c);
    }
    
    public boolean removeAll(@NotNull Collection<?> c) {
        set.removeAll(c);
        return list.removeAll(c);
    }
    
    public boolean retainAll(@NotNull Collection<?> c) {
        set.retainAll(c);
        return list.retainAll(c);
    }
    
    public void clear() {
        set.clear();
        list.clear();
    }
    
    public E get(int index) {
        return list.get(index);
    }
    
    public E remove(int index) {
        E removed = list.remove(index);
        set.remove(removed);
        return removed;
    }
    
    public int indexOf(Object o) {
        return list.indexOf(o);
    }
    
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }
    
    public @NotNull ListIterator<E> listIterator() {
        return new HashArrayIterator(this.list.listIterator());
    }
    
    private class HashArrayIterator implements ListIterator<E> {
        
        private final ListIterator<E> backingIterator;
        
        private E currentValue;
        
        public HashArrayIterator(ListIterator<E> backingIterator) {
            this.backingIterator = backingIterator;
        }
        
        @Override
        public boolean hasNext() {
            return this.backingIterator.hasNext();
        }
        
        @Override
        public E next() {
            currentValue = this.backingIterator.next();
            return currentValue;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.backingIterator.hasPrevious();
        }
        
        @Override
        public E previous() {
            currentValue = this.backingIterator.previous();
            return currentValue;
        }
        
        @Override
        public int nextIndex() {
            return this.backingIterator.nextIndex();
        }
        
        @Override
        public int previousIndex() {
            return this.backingIterator.previousIndex();
        }
        
        @Override
        public void remove() {
            this.backingIterator.remove();
            set.remove(currentValue);
        }
        
        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("HashArrayList does not support set operations");
        }
        
        @Override
        public void add(E e) {
            if (set.add(e)) {
                this.backingIterator.add(e);
            }
        }
    }
}