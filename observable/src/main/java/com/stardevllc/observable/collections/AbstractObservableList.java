package com.stardevllc.observable.collections;

import com.stardevllc.observable.Observable;
import com.stardevllc.observable.collections.event.CollectionAddEvent;
import com.stardevllc.observable.collections.event.CollectionRemoveEvent;

import java.util.Collection;
import java.util.ListIterator;

public abstract class AbstractObservableList<E> extends AbstractObservableCollection<E> implements ObservableList<E> {

    @Override
    public boolean add(E e) {
        add(size(), e);
        return true;
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
    public int indexOf(Object o) {
        ListIterator<E> it = listIterator();
        if (o == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    return it.previousIndex();
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    return it.previousIndex();
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        ListIterator<E> it = listIterator(size());
        if (o == null) {
            while (it.hasPrevious()) {
                if (it.previous() == null) {
                    return it.nextIndex();
                }
            }
        } else {
            while (it.hasPrevious()) {
                if (o.equals(it.previous())) {
                    return it.nextIndex();
                }
            }
        }
        return -1;
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
            backingList.eventBus().post(new CollectionRemoveEvent(backingList, current));
        }

        @Override
        public void set(E e) {
            backingIterator.set(e);
            backingList.eventBus().post(new CollectionAddEvent<>(backingList, e, current));
        }

        @Override
        public void add(E e) {
            backingIterator.add(e);
            backingList.eventBus().post(new CollectionAddEvent<>(backingList, e, null));
        }
    }
}
