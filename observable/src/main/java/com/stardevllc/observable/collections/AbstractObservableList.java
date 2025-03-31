package com.stardevllc.observable.collections;

import com.stardevllc.observable.Observable;
import com.stardevllc.observable.collections.event.CollectionChangeEvent;

import java.util.*;
import java.util.function.UnaryOperator;

public abstract class AbstractObservableList<E> extends AbstractObservableCollection<E> implements ObservableList<E> {
    
    protected final List<E> backingList;
    
    public AbstractObservableList(List<E> collection) {
        super(collection);
        this.backingList = collection;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }

    @Override
    public E get(int index) {
        return this.backingList.get(index);
    }

    @Override
    public E set(int index, E element) {
        E replaced = this.backingList.set(index, element);
        this.eventBus.post(new CollectionChangeEvent<>(this, element, replaced));
        return replaced;
    }

    @Override
    public void add(int index, E element) {
        this.backingList.add(index, element);
        this.eventBus.post(new CollectionChangeEvent<>(this, element, null));
    }

    @Override
    public E remove(int index) {
        E removed = this.backingList.remove(index);
        this.eventBus.post(new CollectionChangeEvent(this, null, removed));
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        return this.backingList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return backingList.lastIndexOf(o);
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ObservableListIterator<>(this, this.backingList.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ObservableListIterator<>(this, this.backingList.listIterator(), index);
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }

    @Override
    public void sort(Comparator<? super E> c) {
        this.backingList.sort(c);
    }

    @Override
    public Spliterator<E> spliterator() {
        return this.backingList.spliterator();
    }

    @Override
    public void addFirst(E e) {
        this.add(0, e);
    }

    @Override
    public void addLast(E e) {
        this.add(e);
    }

    @Override
    public E getFirst() {
        return this.backingList.getFirst();
    }

    @Override
    public E getLast() {
        return this.backingList.getLast();
    }

    @Override
    public E removeFirst() {
        return this.remove(0);
    }

    @Override
    public E removeLast() {
        return this.remove(this.size() - 1);
    }

    protected static class ObservableListIterator<E> implements Observable, ListIterator<E> {
        
        protected final ObservableList<E> backingList;
        protected final ListIterator<E> backingIterator;
        
        protected E current;

        public ObservableListIterator(ObservableList<E> backingList, ListIterator<E> backingIterator) {
            this.backingList = backingList;
            this.backingIterator = backingIterator;
        }
        
        public ObservableListIterator(ObservableList<E> backingList, ListIterator<E> backingIterator, int startingIndex) {
            this(backingList, backingIterator);
            for (int i = 0; i < startingIndex; i++) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }

        @Override
        public E next() {
            current = backingIterator.next();
            return current;
        }

        @Override
        public boolean hasPrevious() {
            return backingIterator.hasPrevious();
        }

        @Override
        public E previous() {
            current = backingIterator.previous();
            return current;
        }

        @Override
        public int nextIndex() {
            return backingIterator.nextIndex();
        }

        @Override
        public int previousIndex() {
            return backingIterator.previousIndex();
        }

        @Override
        public void remove() {
            backingIterator.remove();
            backingList.eventBus().post(new CollectionChangeEvent(backingList, null, current));
        }

        @Override
        public void set(E e) {
            backingIterator.set(e);
            backingList.eventBus().post(new CollectionChangeEvent<>(backingList, e, current));
        }

        @Override
        public void add(E e) {
            backingIterator.add(e);
            backingList.eventBus().post(new CollectionChangeEvent<>(backingList, e, null));
        }
    }
}
