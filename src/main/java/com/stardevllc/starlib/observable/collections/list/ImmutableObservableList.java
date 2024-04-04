/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

import com.stardevllc.starlib.observable.InvalidationListener;

import java.util.*;

public class ImmutableObservableList<E> extends AbstractList<E> implements ObservableList<E> {

    private final E[] elements;

    private final ListIterator<E> iterator = new ListIterator<>() {
        
        private int pointer;
        
        @Override
        public boolean hasNext() {
            if (elements.length == 0) {
                return false;
            }
            
            return elements.length < pointer;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            return elements[pointer++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPrevious() {
            if (elements.length == 0) {
                return false;
            }
            
            return pointer > 0;
        }

        @Override
        public E previous() {
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return pointer + 1;
        }

        @Override
        public int previousIndex() {
            return pointer - 1;
        }

        @Override
        public void set(Object e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Object e) {
            throw new UnsupportedOperationException();
        }
    };

    public ImmutableObservableList(E... elements) {
        this.elements = ((elements == null) || (elements.length == 0))?
                null : Arrays.copyOf(elements, elements.length);
    }
    
    public ImmutableObservableList(Collection<E> collection) {
        elements = (E[]) collection.toArray(new Object[0]);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        // no-op
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        // no-op
    }

    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        // no-op
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        // no-op
    }

    @Override
    public boolean addAll(E... elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setAll(E... elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setAll(Collection<? extends E> col) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(E... elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(E... elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(int from, int to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        if ((index < 0) || (index >= size())) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    @Override
    public int size() {
        return (elements == null)? 0 : elements.length;
    }

    @Override
    public Iterator<E> iterator() {
        return iterator;
    }

    @Override
    public ListIterator<E> listIterator() {
        return iterator;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return iterator;
    }
}