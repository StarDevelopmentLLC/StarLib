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

package com.stardevllc.starlib.observable.collections.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ObservableHashSet<E> implements ObservableSet<E> {

    private final Set<E> backingSet;

    private SetListenerHelper<E> listenerHelper;

    public ObservableHashSet(Set<E> set) {
        this.backingSet = set;
    }

    private class SimpleAddChange extends SetChangeListener.Change<E> {

        private final E added;

        public SimpleAddChange(E added) {
            super(ObservableHashSet.this);
            this.added = added;
        }

        @Override
        public boolean wasAdded() {
            return true;
        }

        @Override
        public boolean wasRemoved() {
            return false;
        }

        @Override
        public E getElementAdded() {
            return added;
        }

        @Override
        public E getElementRemoved() {
            return null;
        }

        @Override
        public String toString() {
            return "added " + added;
        }

    }

    private class SimpleRemoveChange extends SetChangeListener.Change<E> {

        private final E removed;

        public SimpleRemoveChange(E removed) {
            super(ObservableHashSet.this);
            this.removed = removed;
        }

        @Override
        public boolean wasAdded() {
            return false;
        }

        @Override
        public boolean wasRemoved() {
            return true;
        }

        @Override
        public E getElementAdded() {
            return null;
        }

        @Override
        public E getElementRemoved() {
            return removed;
        }

        @Override
        public String toString() {
            return "removed " + removed;
        }

    }

    private void callObservers(SetChangeListener.Change<E> change) {
        SetListenerHelper.fireValueChangedEvent(listenerHelper, change);
    }

    @Override
    public void addListener(SetChangeListener<?super E> observer) {
        listenerHelper = SetListenerHelper.addListener(listenerHelper, observer);
    }

    @Override
    public void removeListener(SetChangeListener<?super E> observer) {
        listenerHelper = SetListenerHelper.removeListener(listenerHelper, observer);
    }

    @Override
    public int size() {
        return backingSet.size();
    }

    @Override
    public boolean isEmpty() {
        return backingSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backingSet.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private final Iterator<E> backingIt = backingSet.iterator();
            private E lastElement;

            @Override
            public boolean hasNext() {
                return backingIt.hasNext();
            }

            @Override
            public E next() {
                lastElement = backingIt.next();
                return lastElement;
            }

            @Override
            public void remove() {
                backingIt.remove();
                callObservers(new SimpleRemoveChange(lastElement));
            }
        };
    }

    @Override
    public Object[] toArray() {
        return backingSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backingSet.toArray(a);
    }

    @Override
    public boolean add(E o) {
        boolean ret = backingSet.add(o);
        if (ret) {
            callObservers(new SimpleAddChange(o));
        }
        return ret;
    }

    @Override
    public boolean remove(Object o) {
        boolean ret = backingSet.remove(o);
        if (ret) {
            callObservers(new SimpleRemoveChange((E)o));
        }
        return ret;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backingSet.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<?extends E> c) {
        boolean ret = false;
        for (E element : c) {
            ret |= add(element);
        }
        return ret;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // implicit check to ensure c != null
        if (c.isEmpty() && !backingSet.isEmpty()) {
            clear();
            return true;
        }

        if (backingSet.isEmpty()) {
            return false;
        }

        return removeRetain(c, false);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // implicit check to ensure c != null
        if (c.isEmpty() || backingSet.isEmpty()) {
            return false;
        }

        return removeRetain(c, true);
    }

    private boolean removeRetain(Collection<?> c, boolean remove) {
        boolean removed = false;
        for (Iterator<E> i = backingSet.iterator(); i.hasNext();) {
            E element = i.next();
            if (remove == c.contains(element)) {
                removed = true;
                i.remove();
                callObservers(new SimpleRemoveChange(element));
            }
        }
        return removed;
    }

    @Override
    public void clear() {
        for (Iterator<E> i = backingSet.iterator(); i.hasNext(); ) {
            E element = i.next();
            i.remove();
            callObservers(new SimpleRemoveChange(element));
        }
    }

    @Override
    public String toString() {
        return backingSet.toString();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return backingSet.equals(obj);
    }

    @Override
    public int hashCode() {
        return backingSet.hashCode();
    }
}