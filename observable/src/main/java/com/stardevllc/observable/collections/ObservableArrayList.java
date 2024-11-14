package com.stardevllc.observable.collections;

import com.stardevllc.observable.collections.event.CollectionAddEvent;
import com.stardevllc.observable.collections.event.CollectionRemoveEvent;

import java.util.*;
import java.util.stream.Stream;

public class ObservableArrayList<E> extends AbstractObservableList<E> {
    
    protected final ArrayList<E> backingList = new ArrayList<>();

    public ObservableArrayList() {}
    
    public ObservableArrayList(Collection<E> collection) {
        this.backingList.addAll(collection);
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        return backingList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backingList.toArray(a);
    }

    @Override
    public Stream<E> stream() {
        return backingList.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return backingList.parallelStream();
    }

    @Override
    public E get(int index) {
        return backingList.get(index);
    }

    @Override
    public E set(int index, E element) {
        E removed = backingList.set(index, element);
        eventBus.post(new CollectionAddEvent<>(this, element, removed));
        return removed;
    }

    @Override
    public void add(int index, E element) {
        this.backingList.add(index, element);
        eventBus.post(new CollectionAddEvent<>(this, element, null));
    }

    @Override
    public E remove(int index) {
        E removed = this.backingList.remove(index);
        eventBus.post(new CollectionRemoveEvent(this, removed));
        return removed;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ObservableListIterator<>(this, backingList.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ObservableListIterator<>(this, backingList.listIterator(), index);
    }

    @Override
    public ObservableList<E> subList(int fromIndex, int toIndex) {
        ObservableArrayList<E> subList = new ObservableArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(get(i));
        }
        return subList;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        backingList.sort(c);
    }

    @Override
    public void addFirst(E e) {
        backingList.addFirst(e);
        eventBus.post(new CollectionAddEvent<>(this, e, null));
    }

    @Override
    public void addLast(E e) {
        backingList.addLast(e);
        eventBus.post(new CollectionAddEvent<>(this, e, null));
    }

    @Override
    public E getFirst() {
        return backingList.getFirst();
    }

    @Override
    public E getLast() {
        return backingList.getLast();
    }

    @Override
    public E removeFirst() {
        E removed = backingList.removeFirst();
        eventBus.post(new CollectionRemoveEvent(this, removed));
        return removed;
    }

    @Override
    public E removeLast() {
        E removed = backingList.removeLast();
        eventBus.post(new CollectionRemoveEvent(this, removed));
        return removed;
    }

    @Override
    public ObservableList<E> reversed() {
        return new ObservableArrayList<>(backingList.reversed());
    }
}
