/*
 * Copyright (c) 2010, 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.stardevllc.starlib.observable.collections.list;

import com.stardevllc.starlib.Callback;
import com.stardevllc.starlib.observable.collections.ElementObserver;
import com.stardevllc.starlib.observable.collections.SortHelper;
import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.Observable;

import java.util.*;

public class ObservableSequentialList<E> extends ModifiableObservableList<E> implements SortableList<E> {

    private final List<E> backingList;
    private final ElementObserver<E> elementObserver;

    public ObservableSequentialList(List<E> list) {
        backingList = list;
        elementObserver = null;
    }

    public ObservableSequentialList(List<E> list, Callback<E, Observable[]> extractor) {
        backingList = list;
        this.elementObserver = new ElementObserver<>(extractor, e -> new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                beginChange();
                int i = 0;
                for (E value : backingList) {
                    if (value == e) {
                        nextUpdate(i);
                    }
                    ++i;
                }
                endChange();
            }
        }, this);
        for (E e : backingList) {
            elementObserver.attachListener(e);
        }
    }

    @Override
    public boolean contains(Object o) {
        return backingList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(backingList).containsAll(c);
    }

    @Override
    public int indexOf(Object o) {
        return backingList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return backingList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        return new ListIterator<>() {

            private final ListIterator<E> backingIt = backingList.listIterator(index);
            private E lastReturned;

            @Override
            public boolean hasNext() {
                return backingIt.hasNext();
            }

            @Override
            public E next() {
                return lastReturned = backingIt.next();
            }

            @Override
            public boolean hasPrevious() {
                return backingIt.hasPrevious();
            }

            @Override
            public E previous() {
                return lastReturned = backingIt.previous();
            }

            @Override
            public int nextIndex() {
                return backingIt.nextIndex();
            }

            @Override
            public int previousIndex() {
                return backingIt.previousIndex();
            }

            @Override
            public void remove() {
                beginChange();
                int idx = previousIndex();
                backingIt.remove();
                nextRemove(idx, lastReturned);
                endChange();
            }

            @Override
            public void set(E e) {
                beginChange();
                int idx = previousIndex();
                backingIt.set(e);
                nextSet(idx, lastReturned);
                endChange();
            }

            @Override
            public void add(E e) {
                beginChange();
                int idx = nextIndex();
                backingIt.add(e);
                nextAdd(idx, idx + 1);
                endChange();
            }
        };
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public E get(int index) {
        try {
            return backingList.listIterator(index).next();
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        // implicit check to ensure c != null
        if (c.isEmpty()) {
            return false;
        }

        try {
            beginChange();
            boolean modified = false;
            ListIterator<E> e1 = listIterator(index);
            for (E e : c) {
                e1.add(e);
                modified = true;
            }
            endChange();
            return modified;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    protected void doAdd(int index, E element) {
        try {
            backingList.listIterator(index).add(element);
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    @Override
    protected E doSet(int index, E element) {
        try {
            ListIterator<E> e = backingList.listIterator(index);
            E oldVal = e.next();
            e.set(element);
            return oldVal;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    @Override
    protected E doRemove(int index) {
        try {
            ListIterator<E> e = backingList.listIterator(index);
            E outCast = e.next();
            e.remove();
            return outCast;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    private SortHelper helper;

    @Override
    public void doSort(Comparator<? super E> comparator) {
        int[] perm = getSortHelper().sort(backingList, comparator);
        fireChange(new NonIterableChange.SimplePermutationChange<>(0, size(), perm, this));
    }

    private SortHelper getSortHelper() {
        if (helper == null) {
            helper = new SortHelper();
        }
        return helper;
    }
}