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

package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.Callback;
import com.stardevllc.starlib.observable.collections.array.ObservableFloatArray;
import com.stardevllc.starlib.observable.collections.array.ObservableFloatArrayImpl;
import com.stardevllc.starlib.observable.collections.array.ObservableIntegerArray;
import com.stardevllc.starlib.observable.collections.array.ObservableIntegerArrayImpl;
import com.stardevllc.starlib.observable.collections.list.*;
import com.stardevllc.starlib.observable.collections.map.*;
import com.stardevllc.starlib.observable.collections.set.ObservableSetWrapper;
import com.stardevllc.starlib.observable.collections.set.SetAdapterChange;
import com.stardevllc.starlib.observable.collections.set.SetListenerHelper;
import com.stardevllc.starlib.observable.collections.set.WeakSetChangeListener;
import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.set.ObservableSet;
import com.stardevllc.starlib.observable.collections.set.SetChangeListener;

import java.util.*;

public class StarCollections {
    private StarCollections() { }

    public static <E> ObservableList<E> observableList(List<E> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        return list instanceof RandomAccess ? new ObservableListWrapper<>(list) :
                new ObservableSequentialList<>(list);
    }

    public static <E> ObservableList<E> observableList(List<E> list, Callback<E, Observable[]> extractor) {
        if (list == null || extractor == null) {
            throw new NullPointerException();
        }
        return list instanceof RandomAccess ? new ObservableListWrapper<>(list, extractor) :
            new ObservableSequentialList<>(list, extractor);
    }

    public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map) {
        if (map == null) {
            throw new NullPointerException();
        }
        return new ObservableMapWrapper<>(map);
    }

    public static <E> ObservableSet<E> observableSet(Set<E> set) {
        if (set == null) {
            throw new NullPointerException();
        }
        return new ObservableSetWrapper<>(set);
    }

    public static <E> ObservableSet<E> observableSet(E... elements) {
        if (elements == null) {
            throw new NullPointerException();
        }
        Set<E> set = new HashSet<>(elements.length);
        Collections.addAll(set, elements);
        return new ObservableSetWrapper<>(set);
    }

    public static <K, V> ObservableMap<K, V> unmodifiableObservableMap(ObservableMap<K, V> map) {
        if (map == null) {
            throw new NullPointerException();
        }
        return new UnmodifiableObservableMap<>(map);
    }

    public static <K, V> ObservableMap<K, V> synchronizedObservableMap(ObservableMap<K, V> map) {
        if (map == null) {
            throw new NullPointerException();
        }
        return new SynchronizedObservableMap<>(map);
    }

    private static final ObservableMap<?, ?> EMPTY_OBSERVABLE_MAP = new UnmodifiableObservableMap<>();

    @SuppressWarnings("unchecked")
    public static <K, V> ObservableMap<K, V> emptyObservableMap() {
        return (ObservableMap<K, V>) EMPTY_OBSERVABLE_MAP;
    }

    public static ObservableIntegerArray observableIntegerArray() {
        return new ObservableIntegerArrayImpl();
    }

    public static ObservableIntegerArray observableIntegerArray(int... values) {
        return new ObservableIntegerArrayImpl(values);
    }

    public static ObservableIntegerArray observableIntegerArray(ObservableIntegerArray array) {
        return new ObservableIntegerArrayImpl(array);
    }

    public static ObservableFloatArray observableFloatArray() {
        return new ObservableFloatArrayImpl();
    }

    public static ObservableFloatArray observableFloatArray(float... values) {
        return new ObservableFloatArrayImpl(values);
    }

    public static ObservableFloatArray observableFloatArray(ObservableFloatArray array) {
        return new ObservableFloatArrayImpl(array);
    }

    public static <E> ObservableList<E> observableArrayList() {
        return observableList(new ArrayList<>());
    }

    public static <E> ObservableList<E> observableArrayList(Callback<E, Observable[]> extractor) {
        return observableList(new ArrayList<>(), extractor);
    }

    public static <E> ObservableList<E> observableArrayList(E... items) {
        return observableList(new ArrayList<>(Arrays.asList(items)));
    }

    public static <E> ObservableList<E> observableArrayList(Collection<? extends E> col) {
        return observableList(new ArrayList<>(col));
    }

    public static <K,V> ObservableMap<K,V> observableHashMap() {
        return observableMap(new HashMap<>());
    }

    public static <E> ObservableList<E> concat(ObservableList<E>... lists) {
        if (lists.length == 0 ) {
            return observableArrayList();
        }
        if (lists.length == 1) {
            return observableArrayList(lists[0]);
        }
        ArrayList<E> backingList = new ArrayList<>();
        for (ObservableList<E> s : lists) {
            backingList.addAll(s);
        }

        return observableList(backingList);
    }

    public static<E> ObservableList<E> unmodifiableObservableList(ObservableList<E> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        return new ImmutableObservableList<>(list);
    }

    public static<E> ObservableList<E> synchronizedObservableList(ObservableList<E> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        return new SynchronizedObservableList<>(list);
    }

    private static final ObservableList<?> EMPTY_OBSERVABLE_LIST = new ImmutableObservableList<>();

    @SuppressWarnings("unchecked")
    public static <E> ObservableList<E> emptyObservableList() {
        return (ObservableList<E>) EMPTY_OBSERVABLE_LIST;
    }

    public static<E> ObservableList<E> singletonObservableList(E e) {
        return new ImmutableObservableList<>(e);
    }

    public static<E> ObservableSet<E> unmodifiableObservableSet(ObservableSet<E> set) {
        if (set == null) {
            throw new NullPointerException();
        }
        return new UnmodifiableObservableSet<>(set);
    }

    public static<E> ObservableSet<E> synchronizedObservableSet(ObservableSet<E> set) {
        if (set == null) {
            throw new NullPointerException();
        }
        return new SynchronizedObservableSet<>(set);
    }

    private static final ObservableSet<?> EMPTY_OBSERVABLE_SET = new EmptyObservableSet<>();

    @SuppressWarnings("unchecked")
    public static <E> ObservableSet<E> emptyObservableSet() {
        return (ObservableSet<E>) EMPTY_OBSERVABLE_SET;
    }

    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    public static <T> void copy(ObservableList<? super T> dest, List<? extends T> src) {
        final int srcSize = src.size();
        if (srcSize > dest.size()) {
            throw new IndexOutOfBoundsException("Source does not fit in dest");
        }
        T[] destArray = (T[]) dest.toArray();
        System.arraycopy(src.toArray(), 0, destArray, 0, srcSize);
        dest.setAll(destArray);
    }

    @SuppressWarnings("unchecked")
    public static <T> void fill(ObservableList<? super T> list, T obj) {
        T[] newContent = (T[]) new Object[list.size()];
        Arrays.fill(newContent, obj);
        list.setAll(newContent);
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean replaceAll(ObservableList<T> list, T oldVal, T newVal) {
        T[] newContent = (T[]) list.toArray();
        boolean modified = false;
        for (int i = 0 ; i < newContent.length; ++i) {
            if (newContent[i].equals(oldVal)) {
                newContent[i] = newVal;
                modified = true;
            }
        }
        if (modified) {
            list.setAll(newContent);
        }
        return modified;
    }

    @SuppressWarnings("unchecked")
    public static void reverse(ObservableList list) {
        Object[] newContent = list.toArray();
        for (int i = 0; i < newContent.length / 2; ++i) {
            Object tmp = newContent[i];
            newContent[i] = newContent[newContent.length - i - 1];
            newContent[newContent.length -i - 1] = tmp;
        }
        list.setAll(newContent);
    }

    @SuppressWarnings("unchecked")
    public static void rotate(ObservableList list, int distance) {
        Object[] newContent = list.toArray();

        int size = list.size();
        distance = distance % size;
        if (distance < 0)
            distance += size;
        if (distance == 0)
            return;

        for (int cycleStart = 0, nMoved = 0; nMoved != size; cycleStart++) {
            Object displaced = newContent[cycleStart];
            Object tmp;
            int i = cycleStart;
            do {
                i += distance;
                if (i >= size)
                    i -= size;
                tmp = newContent[i];
                newContent[i] = displaced;
                displaced = tmp;
                nMoved ++;
            } while(i != cycleStart);
        }
        list.setAll(newContent);
    }

    public static void shuffle(ObservableList<?> list) {
        if (r == null) {
            r = new Random();
        }
        shuffle(list, r);
    }
    private static Random r;

    @SuppressWarnings("unchecked")
    public static void shuffle(ObservableList list, Random rnd) {
        Object[] newContent = list.toArray();

        for (int i = list.size(); i > 1; i--) {
            swap(newContent, i - 1, rnd.nextInt(i));
        }

        list.setAll(newContent);
    }

    private static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static <T extends Comparable<? super T>> void sort(ObservableList<T> list) {
        sort(list, Comparator.naturalOrder());
    }

    public static <T> void sort(ObservableList<T> list, Comparator<? super T> comparator) {
        if (list instanceof SortableList) {
            list.sort(comparator);
        } else {
            List<T> newContent = new ArrayList<>(list);
            newContent.sort(comparator);
            list.setAll(newContent);
        }
    }

    private static class SynchronizedList<T> implements List<T> {
        final Object mutex;
        private final List<T> backingList;

        SynchronizedList(List<T> list, Object mutex) {
            this.backingList = list;
            this.mutex = mutex;
        }

        SynchronizedList(List<T> list) {
            this.backingList = list;
            this.mutex = this;
        }

        @Override
        public int size() {
            synchronized(mutex) {
                return backingList.size();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized(mutex) {
                return backingList.isEmpty();
            }
        }

        @Override
        public boolean contains(Object o) {
            synchronized(mutex) {
                return backingList.contains(o);
            }
        }

        @Override
        public Iterator<T> iterator() {
            return backingList.iterator();
        }

        @Override
        public Object[] toArray() {
            synchronized(mutex)  {
                return backingList.toArray();
            }
        }

        @Override
        public <X> X[] toArray(X[] a) {
            synchronized(mutex) {
                return backingList.toArray(a);
            }
        }

        @Override
        public boolean add(T e) {
            synchronized(mutex) {
                return backingList.add(e);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized(mutex) {
                return backingList.remove(o);
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            synchronized(mutex) {
                return new HashSet<>(backingList).containsAll(c);
            }
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            synchronized(mutex) {
                return backingList.addAll(c);
            }
        }

        @Override
        public boolean addAll(int index, Collection<? extends T> c) {
            synchronized(mutex) {
                return backingList.addAll(index, c);

            }
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            synchronized(mutex) {
                return backingList.removeAll(c);
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            synchronized(mutex) {
                return backingList.retainAll(c);
            }
        }

        @Override
        public void clear() {
            synchronized(mutex) {
                backingList.clear();
            }
        }

        @Override
        public T get(int index) {
            synchronized(mutex) {
                return backingList.get(index);
            }
        }

        @Override
        public T set(int index, T element) {
            synchronized(mutex) {
                return backingList.set(index, element);
            }
        }

        @Override
        public void add(int index, T element) {
            synchronized(mutex) {
                backingList.add(index, element);
            }
        }

        @Override
        public T remove(int index) {
            synchronized(mutex) {
                return backingList.remove(index);
            }
        }

        @Override
        public int indexOf(Object o) {
            synchronized(mutex) {
                return backingList.indexOf(o);
            }
        }

        @Override
        public int lastIndexOf(Object o) {
            synchronized(mutex) {
                return backingList.lastIndexOf(o);
            }
        }

        @Override
        public ListIterator<T> listIterator() {
            return backingList.listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            synchronized(mutex) {
                return backingList.listIterator(index);
            }
        }

        @Override
        public List<T> subList(int fromIndex, int toIndex) {
            synchronized(mutex) {
                return new SynchronizedList<>(backingList.subList(fromIndex, toIndex),
                        mutex);
            }
        }

        @Override
        public String toString() {
            synchronized(mutex) {
                return backingList.toString();
            }
        }

        @Override
        public int hashCode() {
            synchronized(mutex) {
                return backingList.hashCode();
            }
        }

        @Override
        public boolean equals(Object o) {
            synchronized(mutex) {
                return backingList.equals(o);
            }
        }

    }

    private static class SynchronizedObservableList<T> extends SynchronizedList<T> implements ObservableList<T> {

        private ListListenerHelper<T> helper;

        private final ObservableList<T> backingList;
        private final ListChangeListener<T> listener;

        SynchronizedObservableList(ObservableList<T> seq) {
            super(seq);
            this.backingList = seq;
            listener = c -> ListListenerHelper.fireValueChangedEvent(helper, new SourceAdapterChange<>(SynchronizedObservableList.this, c));
            backingList.addListener(new WeakListChangeListener<>(listener));
        }

        @Override
        public boolean addAll(T... elements) {
            synchronized(mutex) {
                return backingList.addAll(elements);
            }
        }

        @Override
        public boolean setAll(T... elements) {
            synchronized(mutex) {
                return backingList.setAll(elements);
            }
        }

        @Override
        public boolean removeAll(T... elements) {
            synchronized(mutex) {
                return backingList.removeAll(elements);
            }
        }

        @Override
        public boolean retainAll(T... elements) {
            synchronized(mutex) {
                return backingList.retainAll(elements);
            }
        }

        @Override
        public void remove(int from, int to) {
            synchronized(mutex) {
                backingList.remove(from, to);
            }
        }

        @Override
        public boolean setAll(Collection<? extends T> col) {
            synchronized(mutex) {
                return backingList.setAll(col);
            }
        }

        @Override
        public final void addListener(InvalidationListener listener) {
            synchronized (mutex) {
                helper = ListListenerHelper.addListener(helper, listener);
            }
        }

        @Override
        public final void removeListener(InvalidationListener listener) {
            synchronized (mutex) {
                helper = ListListenerHelper.removeListener(helper, listener);
            }
        }

        @Override
        public void addListener(ListChangeListener<? super T> listener) {
            synchronized (mutex) {
                helper = ListListenerHelper.addListener(helper, listener);
            }
        }

        @Override
        public void removeListener(ListChangeListener<? super T> listener) {
            synchronized (mutex) {
                helper = ListListenerHelper.removeListener(helper, listener);
            }
        }
    }

    private static class EmptyObservableSet<E> extends AbstractSet<E> implements ObservableSet<E> {

        private final Iterator<E> iterator = new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        public EmptyObservableSet() {
        }

        @Override
        public void addListener(InvalidationListener listener) {
        }

        @Override
        public void removeListener(InvalidationListener listener) {
        }

        @Override
        public void addListener(SetChangeListener<? super E> listener) {
        }

        @Override
        public void removeListener(SetChangeListener<? super E> listener) {
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean contains(Object obj) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return c.isEmpty();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <X> X[] toArray(X[] a) {
            if (a.length > 0)
                a[0] = null;
            return a;
        }

        @Override
        public Iterator<E> iterator() {
            return iterator;
        }
    }

    private static class UnmodifiableObservableSet<E> extends AbstractSet<E> implements ObservableSet<E> {

        private final ObservableSet<E> backingSet;
        private SetListenerHelper<E> listenerHelper;
        private SetChangeListener<E> listener;

        public UnmodifiableObservableSet(ObservableSet<E> backingSet) {
            this.backingSet = backingSet;
            this.listener = null;
        }

        private void initListener() {
            if (listener == null) {
                listener = c -> callObservers(new SetAdapterChange<>(UnmodifiableObservableSet.this, c));
                this.backingSet.addListener(new WeakSetChangeListener<>(listener));
            }
        }

        private void callObservers(SetChangeListener.Change<? extends E> change) {
            SetListenerHelper.fireValueChangedEvent(listenerHelper, change);
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<>() {
                private final Iterator<? extends E> i = backingSet.iterator();

                @Override
                public boolean hasNext() {
                    return i.hasNext();
                }

                @Override
                public E next() {
                    return i.next();
                }
            };
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
        public void addListener(InvalidationListener listener) {
            initListener();
            listenerHelper = SetListenerHelper.addListener(listenerHelper, listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            listenerHelper = SetListenerHelper.removeListener(listenerHelper, listener);
        }

        @Override
        public void addListener(SetChangeListener<? super E> listener) {
            initListener();
            listenerHelper = SetListenerHelper.addListener(listenerHelper, listener);
        }

        @Override
        public void removeListener(SetChangeListener<? super E> listener) {
            listenerHelper = SetListenerHelper.removeListener(listenerHelper, listener);
        }

        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    private static class SynchronizedSet<E> implements Set<E> {
        final Object mutex;
        private final Set<E> backingSet;

        SynchronizedSet(Set<E> set, Object mutex) {
            this.backingSet = set;
            this.mutex = mutex;
        }

        SynchronizedSet(Set<E> set) {
            this.backingSet = set;
            this.mutex = this;
        }

        @Override
        public int size() {
            synchronized(mutex) {
                return backingSet.size();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized(mutex) {
                return backingSet.isEmpty();
            }
        }

        @Override
        public boolean contains(Object o) {
            synchronized(mutex) {
                return backingSet.contains(o);
            }
        }

        @Override
        public Iterator<E> iterator() {
            return backingSet.iterator();
        }

        @Override
        public Object[] toArray() {
            synchronized(mutex) {
                return backingSet.toArray();
            }
        }

        @Override
        public <X> X[] toArray(X[] a) {
            synchronized(mutex) {
                return backingSet.toArray(a);
            }
        }

        @Override
        public boolean add(E e) {
            synchronized(mutex) {
                return backingSet.add(e);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized(mutex) {
                return backingSet.remove(o);
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            synchronized(mutex) {
                return backingSet.containsAll(c);
            }
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            synchronized(mutex) {
                return backingSet.addAll(c);
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            synchronized(mutex) {
                return backingSet.retainAll(c);
            }
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            synchronized(mutex) {
                return backingSet.removeAll(c);
            }
        }

        @Override
        public void clear() {
            synchronized(mutex) {
                backingSet.clear();
            }
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            synchronized(mutex) {
                return backingSet.equals(o);
            }
        }

        @Override
        public int hashCode() {
            synchronized (mutex) {
                return backingSet.hashCode();
            }
        }
    }

    private static class SynchronizedObservableSet<E> extends SynchronizedSet<E> implements ObservableSet<E> {

        private final ObservableSet<E> backingSet;
        private SetListenerHelper<E> listenerHelper;
        private final SetChangeListener<E> listener;

        SynchronizedObservableSet(ObservableSet<E> set) {
            super(set);
            backingSet = set;
            listener = c -> SetListenerHelper.fireValueChangedEvent(listenerHelper, new SetAdapterChange<>(SynchronizedObservableSet.this, c));
            backingSet.addListener(new WeakSetChangeListener<>(listener));
        }

        @Override
        public void addListener(InvalidationListener listener) {
            synchronized (mutex) {
                listenerHelper = SetListenerHelper.addListener(listenerHelper, listener);
            }
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            synchronized (mutex) {
                listenerHelper = SetListenerHelper.removeListener(listenerHelper, listener);
            }
        }
        @Override
        public void addListener(SetChangeListener<? super E> listener) {
            synchronized (mutex) {
                listenerHelper = SetListenerHelper.addListener(listenerHelper, listener);
            }
        }

        @Override
        public void removeListener(SetChangeListener<? super E> listener) {
            synchronized (mutex) {
                listenerHelper = SetListenerHelper.removeListener(listenerHelper, listener);
            }
        }
    }

    private static class SynchronizedMap<K, V> implements Map<K, V> {
        final Object mutex;
        private final Map<K, V> backingMap;

        SynchronizedMap(Map<K, V> map) {
            backingMap = map;
            this.mutex = this;
        }

        @Override
        public int size() {
            synchronized (mutex) {
                return backingMap.size();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized (mutex) {
                return backingMap.isEmpty();
            }
        }

        @Override
        public boolean containsKey(Object key) {
            synchronized (mutex) {
                return backingMap.containsKey(key);
            }
        }

        @Override
        public boolean containsValue(Object value) {
            synchronized (mutex) {
                return backingMap.containsValue(value);
            }
        }

        @Override
        public V get(Object key) {
            synchronized (mutex) {
                return backingMap.get(key);
            }
        }

        @Override
        public V put(K key, V value) {
            synchronized (mutex) {
                return backingMap.put(key, value);
            }
        }

        @Override
        public V remove(Object key) {
            synchronized (mutex) {
                return backingMap.remove(key);
            }
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            synchronized (mutex) {
                backingMap.putAll(m);
            }
        }

        @Override
        public void clear() {
            synchronized (mutex) {
                backingMap.clear();
            }
        }

        private transient Set<K> keySet = null;
        private transient Set<Map.Entry<K, V>> entrySet = null;
        private transient Collection<V> values = null;

        @Override
        public Set<K> keySet() {
            synchronized (mutex) {
                if (keySet == null)
                    keySet = new SynchronizedSet<>(backingMap.keySet(), mutex);
                return keySet;
            }
        }

        @Override
        public Collection<V> values() {
            synchronized (mutex) {
                if (values == null)
                    values = Collections.synchronizedCollection(backingMap.values());
                return values;
            }
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            synchronized (mutex) {
                if (entrySet == null)
                    entrySet = new SynchronizedSet<>(backingMap.entrySet(), mutex);
                return entrySet;
            }
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            synchronized (mutex) {
                return backingMap.equals(o);
            }
        }

        @Override
        public int hashCode() {
            synchronized (mutex) {
                return backingMap.hashCode();
            }
        }
    }

    private static class SynchronizedObservableMap<K, V> extends SynchronizedMap<K, V> implements ObservableMap<K, V> {

        private final ObservableMap<K, V> backingMap;
        private MapListenerHelper<K, V> listenerHelper;
        private final MapChangeListener<K, V> listener;

        SynchronizedObservableMap(ObservableMap<K, V> map) {
            super(map);
            backingMap = map;
            listener = c -> MapListenerHelper.fireValueChangedEvent(listenerHelper, new MapAdapterChange<>(SynchronizedObservableMap.this, c));
            backingMap.addListener(new WeakMapChangeListener<>(listener));
        }

        @Override
        public void addListener(InvalidationListener listener) {
            synchronized (mutex) {
                listenerHelper = MapListenerHelper.addListener(listenerHelper, listener);
            }
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            synchronized (mutex) {
                listenerHelper = MapListenerHelper.removeListener(listenerHelper, listener);
            }
        }

        @Override
        public void addListener(MapChangeListener<? super K, ? super V> listener) {
            synchronized (mutex) {
                listenerHelper = MapListenerHelper.addListener(listenerHelper, listener);
            }
        }

        @Override
        public void removeListener(MapChangeListener<? super K, ? super V> listener) {
            synchronized (mutex) {
                listenerHelper = MapListenerHelper.removeListener(listenerHelper, listener);
            }
        }
    }
}