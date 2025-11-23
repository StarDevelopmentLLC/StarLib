package com.stardevllc.starlib.observable.collections.list;

import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.AbstractObservableCollection;
import com.stardevllc.starlib.observable.collections.handler.ListListenerHandler;
import com.stardevllc.starlib.observable.collections.listener.ListChangeListener;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.UnaryOperator;

/**
 * An abstract class that provides common functionality for obserable lists
 *
 * @param <E> The element ty pe
 */
@SuppressWarnings("RedundantNoArgConstructor")
public abstract class AbstractObservableList<E> extends AbstractObservableCollection<E> implements ObservableList<E> {
    
    protected final ListListenerHandler<E> handler = new ListListenerHandler<>();
    
    /**
     * Constructs an empty obserable list
     */
    public AbstractObservableList() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<E> getBackingCollection() {
        return getBackingList();
    }
    
    /**
     * The backing list is similar to the backing collection, should be stored the same
     *
     * @return The list that backs this collection
     */
    protected abstract List<E> getBackingList();
    
    @Override
    public <L extends List<E>> L addContentMirror(L list) {
        list.addAll(this);
        handler.addListener(c -> {
            if (c.added() != null && !list.contains(c.added())) {
                list.add(c.added());
            } else if (c.removed() != null && c.added() == null) {
                list.remove(c.removed());
            }
        });
        
        return list;
    }
    
    @Override
    public void addListener(ListChangeListener<E> changeListener) {
        handler.addListener(changeListener);
    }
    
    @Override
    public void removeListener(ListChangeListener<E> changeListener) {
        handler.addListener(changeListener);
    }
    
    @SuppressWarnings("ConstantValue")
    @Override
    public boolean add(E e) {
        return !handler.handleChange(this, size() - 1, e, null) && this.getBackingList().add(e);
    }
    
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index < 0) {
            return false;
        }
        return !handler.handleChange(this, index, null, (E) o) && this.getBackingList().remove(o);
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            add(e);
            modified = true;
        }
        return modified;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int index) {
        return getBackingList().get(index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        E replaced = getBackingList().set(index, element);
        boolean cancelled = this.handler.handleChange(this, index, element, replaced);
        if (cancelled) {
            getBackingList().set(index, replaced);
            return null;
        }
        return replaced;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        if (!this.handler.handleChange(this, index, element, null)) {
            getBackingList().add(index, element);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        E removed = getBackingList().remove(index);
        boolean cancelled = this.handler.handleChange(this, index, null, removed);
        if (removed != null && cancelled) {
            getBackingList().set(index, removed);
            return null;
        }
        
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object o) {
        return getBackingList().indexOf(o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object o) {
        return getBackingList().lastIndexOf(o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator() {
        return new ObservableListIterator<>(this, handler, getBackingList().listIterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return new ObservableListIterator<>(this, handler, getBackingList().listIterator(), index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sort(Comparator<? super E> c) {
        getBackingList().sort(c);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Spliterator<E> spliterator() {
        return getBackingList().spliterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addFirst(E e) {
        this.add(0, e);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addLast(E e) {
        this.add(e);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E getFirst() {
        return getBackingList().getFirst();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E getLast() {
        return getBackingList().getLast();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeFirst() {
        return this.remove(0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeLast() {
        return this.remove(this.size() - 1);
    }
    
    @Override
    public String toString() {
        return getBackingList().toString();
    }
    
    protected class SubListListener implements ListChangeListener<E> {
        
        private final WeakReference<AbstractObservableList<E>> backingListRef;
        private final WeakReference<AbstractObservableList<E>> subListRef;
        
        private int fromIndex = -1, toIndex = -1;
        
        public SubListListener(AbstractObservableList<E> backingArrayList, AbstractObservableList<E> subList) {
            this.backingListRef = new WeakReference<>(backingArrayList);
            this.subListRef = new WeakReference<>(subList);
        }
        
        public SubListListener(AbstractObservableList<E> backingList, AbstractObservableList<E> subList, int fromIndex, int toIndex) {
            this(backingList, subList);
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }
        
        @Override
        public void changed(Change<E> change) {
            AbstractObservableList<E> backingList = this.backingListRef.get();
            AbstractObservableList<E> subList = subListRef.get();
            if (backingList == null && subList == null) {
                return;
            } else if (backingList == null) {
                subList.removeListener(this);
                return;
            } else if (subList == null) {
                backingList.removeListener(this);
                return;
            }
            
            AbstractObservableList<E> listToChange;
            if (change.collection() == backingList) {
                listToChange = subList;
            } else if (change.collection() == subList) {
                listToChange = backingList;
            } else {
                return;
            }
            
            if (fromIndex > -1 && toIndex > -1) {
                if (!(change.index() >= fromIndex && change.index() < toIndex)) {
                    return;
                }
            }
            
            if (change.added() != null && change.removed() != null) {
                listToChange.getBackingList().set(change.index(), change.added());
            } else if (change.added() != null) {
                listToChange.getBackingList().add(change.index(), change.added());
            } else if (change.removed() != null) {
                listToChange.getBackingList().remove(change.index());
            }
            
            if (fromIndex > -1 && toIndex > -1) {
                ListIterator<E> iterator = listToChange.getBackingList().listIterator(toIndex);
                while (iterator.hasNext()) {
                    iterator.next();
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * The obserable list iterator that allows for listening to changes when using an iterator
     *
     * @param <E> The element type
     */
    protected static class ObservableListIterator<E> implements Observable, ListIterator<E> {
        
        /**
         * The backing obserable list
         */
        protected final ObservableList<E> backingList;
        
        /**
         * The backing iterator
         */
        protected final ListIterator<E> backingIterator;
        
        private final ListListenerHandler<E> handler;
        
        /**
         * The current element of the iterator
         */
        protected E current;
        
        /**
         * Constructs a new obserable list iterator
         *
         * @param backingList     The backing list
         * @param backingIterator The backing iterator
         */
        public ObservableListIterator(ObservableList<E> backingList, ListListenerHandler<E> handler, ListIterator<E> backingIterator) {
            this.backingList = backingList;
            this.backingIterator = backingIterator;
            this.handler = handler;
        }
        
        /**
         * Constructs a new obserable list iterator
         *
         * @param backingList     The backing list
         * @param backingIterator The backing iterator
         * @param startingIndex   The index to start at
         */
        public ObservableListIterator(ObservableList<E> backingList, ListListenerHandler<E> handler, ListIterator<E> backingIterator, int startingIndex) {
            this(backingList, handler, backingIterator);
            for (int i = 0; i < startingIndex; i++) {
                next();
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            current = backingIterator.next();
            return current;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasPrevious() {
            return backingIterator.hasPrevious();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public E previous() {
            current = backingIterator.previous();
            return current;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int nextIndex() {
            return backingIterator.nextIndex();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int previousIndex() {
            return backingIterator.previousIndex();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (!handler.handleChange(backingList, nextIndex() - 1, null, current)) {
                backingIterator.remove();
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void set(E e) {
            if (!handler.handleChange(backingList, nextIndex() - 1, e, current)) {
                backingIterator.set(e);
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void add(E e) {
            if (!handler.handleChange(backingList, nextIndex() - 1, e, null)) {
                backingIterator.add(e);
            }
        }
    }
}
