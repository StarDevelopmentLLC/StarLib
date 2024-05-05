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

import java.util.*;

public abstract class AbstractObservableList<E> extends AbstractList<E> implements ObservableList<E> {

    protected ListListenerHelper<E> listenerHelper;
    private ListChangeBuilder<E> changeBuilder;
    
    protected final List<E> backingList;

    public AbstractObservableList(List<E> backingList) {
        this.backingList = backingList;
    }

    protected final void nextUpdate(int pos) {
        getListChangeBuilder().nextUpdate(pos);
    }

    protected final void nextSet(int idx, E old) {
        getListChangeBuilder().nextSet(idx, old);
    }

    protected final void nextReplace(int from, int to, List<? extends E> removed) {
        getListChangeBuilder().nextReplace(from, to, removed);
    }

    @SuppressWarnings("SameParameterValue")
    protected final void nextRemove(int idx, List<? extends E> removed) {
        getListChangeBuilder().nextRemove(idx, removed);
    }

    protected final void nextRemove(int idx, E removed) {
        getListChangeBuilder().nextRemove(idx, removed);
    }

    protected final void nextPermutation(int from, int to, int[] perm) {
        getListChangeBuilder().nextPermutation(from, to, perm);
    }

    protected final void nextAdd(int from, int to) {
        getListChangeBuilder().nextAdd(from, to);
    }

    protected final void beginChange() {
        getListChangeBuilder().beginChange();
    }

    protected final void endChange() {
        getListChangeBuilder().endChange();
    }

    private ListChangeBuilder<E> getListChangeBuilder() {
        if (changeBuilder == null) {
            changeBuilder = new ListChangeBuilder<>(this);
        }

        return changeBuilder;
    }

    @Override
    public final void addListener(ListChangeListener<? super E> listener) {
        listenerHelper = ListListenerHelper.addListener(listenerHelper, listener);
    }

    @Override
    public final void removeListener(ListChangeListener<? super E> listener) {
        listenerHelper = ListListenerHelper.removeListener(listenerHelper, listener);
    }

    protected final void fireChange(ListChangeListener.Change<? extends E> change) {
        ListListenerHelper.fireValueChangedEvent(listenerHelper, change);
    }

    protected final boolean hasListeners() {
        return ListListenerHelper.hasListeners(listenerHelper);
    }

    @Override
    public boolean addAll(E... elements) {
        return addAll(Arrays.asList(elements));
    }

    @Override
    public boolean setAll(E... elements) {
        return setAll(Arrays.asList(elements));
    }

    @Override
    public boolean removeAll(E... elements) {
        return removeAll(Arrays.asList(elements));
    }

    @Override
    public boolean retainAll(E... elements) {
        return retainAll(Arrays.asList(elements));
    }

    @Override
    public boolean setAll(Collection<? extends E> col) {
        // implicit check to ensure col != null
        if (col.isEmpty() && isEmpty()) {
            return false;
        }

        beginChange();
        try {
            clear();
            addAll(col);
            return true;
        } finally {
            endChange();
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // implicit check to ensure c != null
        if (c.isEmpty()) {
            return false;
        }

        beginChange();
        try {
            return super.addAll(c);
        } finally {
            endChange();
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

        beginChange();
        try {
            return super.addAll(index, c);
        } finally {
            endChange();
        }
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > size()) {
            throw new IndexOutOfBoundsException("Index: " + fromIndex);
        }

        // return early if the range is empty
        if (fromIndex == toIndex) {
            return;
        }

        beginChange();
        try {
            super.removeRange(fromIndex, toIndex);
        } finally {
            endChange();
        }
    }

    @Override
    public E get(int index) {
        return backingList.get(index);
    }

    @Override
    public int size() {
        return backingList.size();
    }

    protected void doAdd(int index, E element) {
        Objects.checkIndex(index, size() + 1);
        backingList.add(index, element);
    }

    protected E doSet(int index, E element) {
        return backingList.set(index, element);
    }

    protected E doRemove(int index) {
        return backingList.remove(index);
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
    public boolean contains(Object o) {
        return backingList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(backingList).containsAll(c);
    }

    @Override
    public void clear() {
        if (hasListeners()) {
            beginChange();
            nextRemove(0, this);
        }
        backingList.clear();
        ++modCount;
        if (hasListeners()) {
            endChange();
        }
    }

    @Override
    public void remove(int fromIndex, int toIndex) {
        Objects.checkFromToIndex(fromIndex, toIndex, size());
        beginChange();
        for (int i = fromIndex; i < toIndex; ++i) {
            remove(fromIndex);
        }
        endChange();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // implicit check to ensure c != null
        if (c.isEmpty() || backingList.isEmpty()) {
            return false;
        }

        beginChange();
        BitSet bs = new BitSet(c.size());
        for (int i = 0; i < size(); ++i) {
            if (c.contains(get(i))) {
                bs.set(i);
            }
        }
        if (!bs.isEmpty()) {
            int cur = size();
            while ((cur = bs.previousSetBit(cur - 1)) >= 0) {
                remove(cur);
            }
        }
        endChange();
        return !bs.isEmpty();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // implicit check to ensure c != null
        if (c.isEmpty() && !isEmpty()) {
            clear();
            return true;
        }

        if (isEmpty()) {
            return false;
        }

        beginChange();
        try {
            return super.retainAll(c);
        } finally {
            endChange();
        }
    }

    @Override
    public void add(int index, E element) {
        doAdd(index, element);
        beginChange();
        nextAdd(index, index + 1);
        ++modCount;
        endChange();
    }

    @Override
    public E set(int index, E element) {
        E old = doSet(index, element);
        beginChange();
        nextSet(index, old);
        endChange();
        return old;
    }

    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i != -1) {
            remove(i);
            return true;
        }
        return false;
    }

    @Override
    public E remove(int index) {
        E old = doRemove(index);
        beginChange();
        nextRemove(index, old);
        ++modCount;
        endChange();
        return old;
    }
}