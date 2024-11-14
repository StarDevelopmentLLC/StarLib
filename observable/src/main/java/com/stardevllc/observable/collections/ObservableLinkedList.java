package com.stardevllc.observable.collections;

import com.stardevllc.observable.collections.event.CollectionAddEvent;
import com.stardevllc.observable.collections.event.CollectionRemoveEvent;

import java.util.*;
import java.util.stream.Stream;

public class ObservableLinkedList<E> extends AbstractObservableList<E> implements Deque<E> {

    protected final LinkedList<E> backingList = new LinkedList<>();

    public ObservableLinkedList() {
    }

    public ObservableLinkedList(Collection<E> collection) {
        this.backingList.addAll(collection);
    }

    public ObservableLinkedList<E> reversed() {
        return null;
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
    public boolean offerFirst(E e) {
        boolean result = backingList.offerFirst(e);
        if (result) {
            eventBus.post(new CollectionAddEvent<>(this, e, null));
        }

        return result;
    }

    @Override
    public boolean offerLast(E e) {
        boolean result = backingList.offerLast(e);
        if (result) {
            eventBus.post(new CollectionAddEvent<>(this, e, null));
        }
        
        return result;
    }

    @Override
    public E pollFirst() {
        E value = backingList.pollFirst();
        eventBus.post(new CollectionRemoveEvent(this, value));
        return value;
    }

    @Override
    public E pollLast() {
        E value = backingList.pollLast();
        eventBus.post(new CollectionRemoveEvent(this, value));
        return value;
    }

    @Override
    public E peekFirst() {
        return backingList.peekFirst();
    }

    @Override
    public E peekLast() {
        return backingList.peekLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        boolean result = this.backingList.removeLastOccurrence(o);
        if (result) {
            eventBus.post(new CollectionRemoveEvent(this, o));
        }

        return result;
    }

    @Override
    public boolean offer(E e) {
        boolean result = backingList.offer(e);
        if (result) {
            eventBus.post(new CollectionAddEvent<>(this, e, null));
        }
        return result;
    }

    @Override
    public E remove() {
        E removed = backingList.remove();
        eventBus.post(new CollectionRemoveEvent(this, removed));
        return null;
    }

    @Override
    public E poll() {
        E value = backingList.poll();
        eventBus.post(new CollectionRemoveEvent(this, value));
        return value;
    }

    @Override
    public E element() {
        return backingList.element();
    }

    @Override
    public E peek() {
        return backingList.peek();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public E get(int index) {
        return backingList.get(index);
    }

    @Override
    public E set(int index, E element) {
        E removed = backingList.set(index, element);
        this.eventBus.post(new CollectionAddEvent<>(this, element, removed));
        return removed;
    }

    @Override
    public void add(int index, E element) {
        backingList.add(index, element);
        this.eventBus.post(new CollectionAddEvent<>(this, element, null));
    }

    @Override
    public E remove(int index) {
        E removed = backingList.remove(index);
        this.eventBus.post(new CollectionRemoveEvent(this, removed));
        return null;
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
    public List<E> subList(int fromIndex, int toIndex) {
        ObservableLinkedList<E> subList = new ObservableLinkedList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(get(i));
        }
        return subList;
    }

    @Override
    public int size() {
        return this.backingList.size();
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        return this.backingList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.backingList.toArray(a);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        this.backingList.sort(c);
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
}
