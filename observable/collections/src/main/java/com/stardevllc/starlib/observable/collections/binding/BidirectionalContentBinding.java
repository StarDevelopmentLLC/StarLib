/*
 * Copyright (c) 2011, 2023, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.collections.binding;

import com.stardevllc.starlib.observable.collections.list.ListChangeListener;
import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.collections.map.MapChangeListener;
import com.stardevllc.starlib.observable.collections.map.ObservableMap;
import com.stardevllc.starlib.observable.collections.set.ObservableSet;
import com.stardevllc.starlib.observable.collections.set.SetChangeListener;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;

public class BidirectionalContentBinding {

    private static void checkParameters(Object property1, Object property2) {
        if ((property1 == null) || (property2 == null)) {
            throw new NullPointerException("Both parameters must be specified.");
        }
        if (property1 == property2) {
            throw new IllegalArgumentException("Cannot bind object to itself");
        }
    }

    public static <E> Object bind(ObservableList<E> list1, ObservableList<E> list2) {
        checkParameters(list1, list2);
        final ListContentBinding<E> binding = new ListContentBinding<>(list1, list2);
        list1.setAll(list2);
        list1.addListener(binding);
        list2.addListener(binding);
        return binding;
    }

    public static <E> Object bind(ObservableSet<E> set1, ObservableSet<E> set2) {
        checkParameters(set1, set2);
        final SetContentBinding<E> binding = new SetContentBinding<>(set1, set2);
        set1.clear();
        set1.addAll(set2);
        set1.addListener(binding);
        set2.addListener(binding);
        return binding;
    }

    public static <K, V> Object bind(ObservableMap<K, V> map1, ObservableMap<K, V> map2) {
        checkParameters(map1, map2);
        final MapContentBinding<K, V> binding = new MapContentBinding<>(map1, map2);
        map1.clear();
        map1.putAll(map2);
        map1.addListener(binding);
        map2.addListener(binding);
        return binding;
    }

    public static void unbind(Object obj1, Object obj2) {
        checkParameters(obj1, obj2);
        if ((obj1 instanceof ObservableList<?> list1) && (obj2 instanceof ObservableList<?> list2)) {
            @SuppressWarnings("unchecked")
            final ListContentBinding<Object> binding = new ListContentBinding<>((ObservableList<Object>) list1, (ObservableList<Object>) list2);
            list1.removeListener(binding);
            list2.removeListener(binding);
        } else if ((obj1 instanceof ObservableSet<?> set1) && (obj2 instanceof ObservableSet<?> set2)) {
            @SuppressWarnings("unchecked")
            final SetContentBinding<Object> binding = new SetContentBinding<>((ObservableSet<Object>) set1, (ObservableSet<Object>) set2);
            set1.removeListener(binding);
            set2.removeListener(binding);
        } else if ((obj1 instanceof ObservableMap<?, ?> map1) && (obj2 instanceof ObservableMap<?, ?> map2)) {
            @SuppressWarnings("unchecked")
            final MapContentBinding<Object, Object> binding = new MapContentBinding<>((ObservableMap<Object, Object>) map1, (ObservableMap<Object, Object>) map2);
            map1.removeListener(binding);
            map2.removeListener(binding);
        }
    }

    private static class ListContentBinding<E> implements ListChangeListener<E> {

        private final WeakReference<ObservableList<E>> propertyRef1;
        private final WeakReference<ObservableList<E>> propertyRef2;

        private boolean updating = false;


        public ListContentBinding(ObservableList<E> list1, ObservableList<E> list2) {
            propertyRef1 = new WeakReference<>(list1);
            propertyRef2 = new WeakReference<>(list2);
        }

        @Override
        public void onChanged(Change<? extends E> change) {
            if (!updating) {
                final ObservableList<E> list1 = propertyRef1.get();
                final ObservableList<E> list2 = propertyRef2.get();
                if ((list1 == null) || (list2 == null)) {
                    if (list1 != null) {
                        list1.removeListener(this);
                    }
                    if (list2 != null) {
                        list2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        final ObservableList<E> dest = (list1 ==  change.getList())? list2 : list1;
                        while (change.next()) {
                            if (change.wasPermutated()) {
                                dest.remove(change.getFrom(), change.getTo());
                                dest.addAll(change.getFrom(), change.getList().subList(change.getFrom(), change.getTo()));
                            } else {
                                if (change.wasRemoved()) {
                                    dest.remove(change.getFrom(), change.getFrom() + change.getRemovedSize());
                                }
                                if (change.wasAdded()) {
                                    dest.addAll(change.getFrom(), change.getAddedSubList());
                                }
                            }
                        }
                    } finally {
                        updating = false;
                    }
                }
            }
        }

        @Override
        public int hashCode() {
            final ObservableList<E> list1 = propertyRef1.get();
            final ObservableList<E> list2 = propertyRef2.get();
            final int hc1 = (list1 == null)? 0 : list1.hashCode();
            final int hc2 = (list2 == null)? 0 : list2.hashCode();
            return hc1 * hc2;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            final Object propertyA1 = propertyRef1.get();
            final Object propertyA2 = propertyRef2.get();
            if ((propertyA1 == null) || (propertyA2 == null)) {
                return false;
            }

            if (obj instanceof ListContentBinding<?> otherBinding) {
                final Object propertyB1 = otherBinding.propertyRef1.get();
                final Object propertyB2 = otherBinding.propertyRef2.get();
                if ((propertyB1 == null) || (propertyB2 == null)) {
                    return false;
                }

                if ((propertyA1 == propertyB1) && (propertyA2 == propertyB2)) {
                    return true;
                }
                return (propertyA1 == propertyB2) && (propertyA2 == propertyB1);
            }
            return false;
        }
    }

    private static class SetContentBinding<E> implements SetChangeListener<E> {

        private final WeakReference<ObservableSet<E>> propertyRef1;
        private final WeakReference<ObservableSet<E>> propertyRef2;

        private boolean updating = false;


        public SetContentBinding(ObservableSet<E> list1, ObservableSet<E> list2) {
            propertyRef1 = new WeakReference<>(list1);
            propertyRef2 = new WeakReference<>(list2);
        }

        @Override
        public void onChanged(Change<? extends E> change) {
            if (!updating) {
                final ObservableSet<E> set1 = propertyRef1.get();
                final ObservableSet<E> set2 = propertyRef2.get();
                if ((set1 == null) || (set2 == null)) {
                    if (set1 != null) {
                        set1.removeListener(this);
                    }
                    if (set2 != null) {
                        set2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        final Set<E> dest = (set1 == change.getSet())? set2 : set1;
                        if (change.wasRemoved()) {
                            dest.remove(change.getElementRemoved());
                        } else {
                            dest.add(change.getElementAdded());
                        }
                    } finally {
                        updating = false;
                    }
                }
            }
        }

        @Override
        public int hashCode() {
            final ObservableSet<E> set1 = propertyRef1.get();
            final ObservableSet<E> set2 = propertyRef2.get();
            final int hc1 = (set1 == null)? 0 : set1.hashCode();
            final int hc2 = (set2 == null)? 0 : set2.hashCode();
            return hc1 * hc2;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            final Object propertyA1 = propertyRef1.get();
            final Object propertyA2 = propertyRef2.get();
            if ((propertyA1 == null) || (propertyA2 == null)) {
                return false;
            }

            if (obj instanceof SetContentBinding<?> otherBinding) {
                final Object propertyB1 = otherBinding.propertyRef1.get();
                final Object propertyB2 = otherBinding.propertyRef2.get();
                if ((propertyB1 == null) || (propertyB2 == null)) {
                    return false;
                }

                if ((propertyA1 == propertyB1) && (propertyA2 == propertyB2)) {
                    return true;
                }
                return (propertyA1 == propertyB2) && (propertyA2 == propertyB1);
            }
            return false;
        }
    }

    private static class MapContentBinding<K, V> implements MapChangeListener<K, V> {

        private final WeakReference<ObservableMap<K, V>> propertyRef1;
        private final WeakReference<ObservableMap<K, V>> propertyRef2;

        private boolean updating = false;


        public MapContentBinding(ObservableMap<K, V> list1, ObservableMap<K, V> list2) {
            propertyRef1 = new WeakReference<>(list1);
            propertyRef2 = new WeakReference<>(list2);
        }

        @Override
        public void onChanged(Change<? extends K, ? extends V> change) {
            if (!updating) {
                final ObservableMap<K, V> map1 = propertyRef1.get();
                final ObservableMap<K, V> map2 = propertyRef2.get();
                if ((map1 == null) || (map2 == null)) {
                    if (map1 != null) {
                        map1.removeListener(this);
                    }
                    if (map2 != null) {
                        map2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        final Map<K, V> dest = (map1 == change.getMap())? map2 : map1;
                        if (change.wasRemoved()) {
                            dest.remove(change.getKey());
                        }
                        if (change.wasAdded()) {
                            dest.put(change.getKey(), change.getValueAdded());
                        }
                    } finally {
                        updating = false;
                    }
                }
            }
        }

        @Override
        public int hashCode() {
            final ObservableMap<K, V> map1 = propertyRef1.get();
            final ObservableMap<K, V> map2 = propertyRef2.get();
            final int hc1 = (map1 == null)? 0 : map1.hashCode();
            final int hc2 = (map2 == null)? 0 : map2.hashCode();
            return hc1 * hc2;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            final Object propertyA1 = propertyRef1.get();
            final Object propertyA2 = propertyRef2.get();
            if ((propertyA1 == null) || (propertyA2 == null)) {
                return false;
            }

            if (obj instanceof MapContentBinding<?, ?> otherBinding) {
                final Object propertyB1 = otherBinding.propertyRef1.get();
                final Object propertyB2 = otherBinding.propertyRef2.get();
                if ((propertyB1 == null) || (propertyB2 == null)) {
                    return false;
                }

                if ((propertyA1 == propertyB1) && (propertyA2 == propertyB2)) {
                    return true;
                }
                return (propertyA1 == propertyB2) && (propertyA2 == propertyB1);
            }
            return false;
        }
    }
}