/*
 * Copyright (c) 2011, 2022, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.expression;

import com.stardevllc.starlib.observable.collections.map.MapChangeListener;
import com.stardevllc.starlib.observable.collections.map.ObservableMap;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableMapValue;

import java.util.Arrays;
import java.util.Map;

public class MapExpressionHelper<K, V> {
    public static <K, V> MapExpressionHelper<K, V> addListener(MapExpressionHelper<K, V> helper, ObservableMapValue<K, V> observable, ChangeListener<? super ObservableMap<K, V>> listener) {
        if ((observable == null) || (listener == null)) {
            throw new NullPointerException();
        }
        return (helper == null) ? new MapExpressionHelper<>(observable, listener) : helper.addListener(listener);
    }

    public static <K, V> MapExpressionHelper<K, V> removeListener(MapExpressionHelper<K, V> helper, ChangeListener<? super ObservableMap<K, V>> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <K, V> MapExpressionHelper<K, V> addListener(MapExpressionHelper<K, V> helper, ObservableMapValue<K, V> observable, MapChangeListener<? super K, ? super V> listener) {
        if ((observable == null) || (listener == null)) {
            throw new NullPointerException();
        }
        return (helper == null) ? new MapExpressionHelper<>(observable, listener) : helper.addListener(listener);
    }

    public static <K, V> MapExpressionHelper<K, V> removeListener(MapExpressionHelper<K, V> helper, MapChangeListener<? super K, ? super V> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <K, V> void fireValueChangedEvent(MapExpressionHelper<K, V> helper) {
        if (helper != null) {
            helper.fireValueChangedEvent();
        }
    }

    public static <K, V> void fireValueChangedEvent(MapExpressionHelper<K, V> helper, MapChangeListener.Change<? extends K, ? extends V> change) {
        if (helper != null) {
            helper.fireValueChangedEvent(change);
        }
    }

    protected final ObservableMapValue<K, V> observable;

    protected MapExpressionHelper(ObservableMapValue<K, V> observable) {
        this.observable = observable;
    }

    private ChangeListener<? super ObservableMap<K, V>>[] changeListeners;
    private MapChangeListener<? super K, ? super V>[] mapChangeListeners;
    private int changeSize;
    private int mapChangeSize;
    private boolean locked;
    private ObservableMap<K, V> currentValue;

    private MapExpressionHelper(ObservableMapValue<K, V> observable, ChangeListener<? super ObservableMap<K, V>> listener0, ChangeListener<? super ObservableMap<K, V>> listener1) {
        this(observable);
        this.changeListeners = new ChangeListener[]{listener0, listener1};
        this.changeSize = 2;
        this.currentValue = observable.getValue();
    }

    private MapExpressionHelper(ObservableMapValue<K, V> observable, MapChangeListener<? super K, ? super V> listener0, MapChangeListener<? super K, ? super V> listener1) {
        this(observable);
        this.mapChangeListeners = new MapChangeListener[]{listener0, listener1};
        this.mapChangeSize = 2;
        this.currentValue = observable.getValue();
    }

    private MapExpressionHelper(ObservableMapValue<K, V> observable, ChangeListener<? super ObservableMap<K, V>> changeListener) {
        this(observable);
        this.changeListeners = new ChangeListener[]{changeListener};
        this.changeSize = 1;
        this.currentValue = observable.getValue();
    }

    private MapExpressionHelper(ObservableMapValue<K, V> observable, MapChangeListener<? super K, ? super V> listChangeListener) {
        this(observable);
        this.mapChangeListeners = new MapChangeListener[]{listChangeListener};
        this.mapChangeSize = 1;
        this.currentValue = observable.getValue();
    }

    private MapExpressionHelper(ObservableMapValue<K, V> observable, ChangeListener<? super ObservableMap<K, V>> changeListener, MapChangeListener<? super K, ? super V> listChangeListener) {
        this(observable);
        this.changeListeners = new ChangeListener[]{changeListener};
        this.changeSize = 1;
        this.mapChangeListeners = new MapChangeListener[]{listChangeListener};
        this.mapChangeSize = 1;
        this.currentValue = observable.getValue();
    }

    protected MapExpressionHelper<K, V> addListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        if (changeListeners == null) {
            changeListeners = new ChangeListener[]{listener};
            changeSize = 1;
        } else {
            final int oldCapacity = changeListeners.length;
            if (locked) {
                final int newCapacity = (changeSize < oldCapacity) ? oldCapacity : (oldCapacity * 3) / 2 + 1;
                changeListeners = Arrays.copyOf(changeListeners, newCapacity);
            } else if (changeSize == oldCapacity) {
                if (changeSize == oldCapacity) {
                    final int newCapacity = (oldCapacity * 3) / 2 + 1;
                    changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                }
            }
            changeListeners[changeSize++] = listener;
        }
        if (changeSize == 1) {
            currentValue = observable.getValue();
        }
        return this;
    }

    protected MapExpressionHelper<K, V> removeListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        if (changeListeners != null) {
            for (int index = 0; index < changeSize; index++) {
                if (listener.equals(changeListeners[index])) {
                    if (changeSize == 1) {
                        if ((mapChangeSize == 1)) {
                            return new MapExpressionHelper<>(observable, mapChangeListeners[0]);
                        }
                        changeListeners = null;
                        changeSize = 0;
                    } else if ((changeSize == 2) && (mapChangeSize == 0)) {
                        return new MapExpressionHelper<>(observable, changeListeners[1 - index]);
                    } else {
                        final int numMoved = changeSize - index - 1;
                        final ChangeListener<? super ObservableMap<K, V>>[] oldListeners = changeListeners;
                        if (locked) {
                            changeListeners = new ChangeListener[changeListeners.length];
                            System.arraycopy(oldListeners, 0, changeListeners, 0, index + 1);
                        }
                        if (numMoved > 0) {
                            System.arraycopy(oldListeners, index + 1, changeListeners, index, numMoved);
                        }
                        changeSize--;
                        if (!locked) {
                            changeListeners[changeSize] = null; // Let gc do its work
                        }
                    }
                    break;
                }
            }
        }
        return this;
    }

    protected MapExpressionHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener) {
        if (mapChangeListeners == null) {
            mapChangeListeners = new MapChangeListener[]{listener};
            mapChangeSize = 1;
        } else {
            final int oldCapacity = mapChangeListeners.length;
            if (locked) {
                final int newCapacity = (mapChangeSize < oldCapacity) ? oldCapacity : (oldCapacity * 3) / 2 + 1;
                mapChangeListeners = Arrays.copyOf(mapChangeListeners, newCapacity);
            } else if (mapChangeSize == oldCapacity) {
                if (mapChangeSize == oldCapacity) {
                    final int newCapacity = (oldCapacity * 3) / 2 + 1;
                    mapChangeListeners = Arrays.copyOf(mapChangeListeners, newCapacity);
                }
            }
            mapChangeListeners[mapChangeSize++] = listener;
        }
        if (mapChangeSize == 1) {
            currentValue = observable.getValue();
        }
        return this;
    }

    protected MapExpressionHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener) {
        if (mapChangeListeners != null) {
            for (int index = 0; index < mapChangeSize; index++) {
                if (listener.equals(mapChangeListeners[index])) {
                    if (mapChangeSize == 1) {
                        if ((changeSize == 1)) {
                            return new MapExpressionHelper<>(observable, changeListeners[0]);
                        }
                        mapChangeListeners = null;
                        mapChangeSize = 0;
                    } else if ((mapChangeSize == 2) && (changeSize == 0)) {
                        return new MapExpressionHelper<>(observable, mapChangeListeners[1 - index]);
                    } else {
                        final int numMoved = mapChangeSize - index - 1;
                        final MapChangeListener<? super K, ? super V>[] oldListeners = mapChangeListeners;
                        if (locked) {
                            mapChangeListeners = new MapChangeListener[mapChangeListeners.length];
                            System.arraycopy(oldListeners, 0, mapChangeListeners, 0, index + 1);
                        }
                        if (numMoved > 0) {
                            System.arraycopy(oldListeners, index + 1, mapChangeListeners, index, numMoved);
                        }
                        mapChangeSize--;
                        if (!locked) {
                            mapChangeListeners[mapChangeSize] = null; // Let gc do its work
                        }
                    }
                    break;
                }
            }
        }
        return this;
    }

    protected void fireValueChangedEvent() {
        if ((changeSize == 0) && (mapChangeSize == 0)) {
            notifyListeners(currentValue, null);
        } else {
            final ObservableMap<K, V> oldValue = currentValue;
            currentValue = observable.getValue();
            notifyListeners(oldValue, null);
        }
    }

    protected void fireValueChangedEvent(final MapChangeListener.Change<? extends K, ? extends V> change) {
        final SimpleChange<K, V> mappedChange = (mapChangeSize == 0) ? null : new SimpleChange<>(observable, change);
        notifyListeners(currentValue, mappedChange);
    }

    private void notifyListeners(ObservableMap<K, V> oldValue, SimpleChange<K, V> change) {
        final ChangeListener<? super ObservableMap<K, V>>[] curChangeList = changeListeners;
        final int curChangeSize = changeSize;
        final MapChangeListener<? super K, ? super V>[] curListChangeList = mapChangeListeners;
        final int curListChangeSize = mapChangeSize;
        try {
            locked = true;
            if ((currentValue != oldValue) || (change != null)) {
                for (int i = 0; i < curChangeSize; i++) {
                    curChangeList[i].changed(observable, oldValue, currentValue);
                }
                if (curListChangeSize > 0) {
                    if (change != null) {
                        for (int i = 0; i < curListChangeSize; i++) {
                            curListChangeList[i].onChanged(change);
                        }
                    } else {
                        change = new SimpleChange<>(observable);
                        if (currentValue == null) {
                            for (final Map.Entry<K, V> element : oldValue.entrySet()) {
                                change.setRemoved(element.getKey(), element.getValue());
                                for (int i = 0; i < curListChangeSize; i++) {
                                    curListChangeList[i].onChanged(change);
                                }
                            }
                        } else if (oldValue == null) {
                            for (final Map.Entry<K, V> element : currentValue.entrySet()) {
                                change.setAdded(element.getKey(), element.getValue());
                                for (int i = 0; i < curListChangeSize; i++) {
                                    curListChangeList[i].onChanged(change);
                                }
                            }
                        } else {
                            for (final Map.Entry<K, V> element : oldValue.entrySet()) {
                                final K key = element.getKey();
                                final V oldEntry = element.getValue();
                                if (currentValue.containsKey(key)) {
                                    final V newEntry = currentValue.get(key);
                                    if (oldEntry == null ? newEntry != null : !newEntry.equals(oldEntry)) {
                                        change.setPut(key, oldEntry, newEntry);
                                        for (int i = 0; i < curListChangeSize; i++) {
                                            curListChangeList[i].onChanged(change);
                                        }
                                    }
                                } else {
                                    change.setRemoved(key, oldEntry);
                                    for (int i = 0; i < curListChangeSize; i++) {
                                        curListChangeList[i].onChanged(change);
                                    }
                                }
                            }
                            for (final Map.Entry<K, V> element : currentValue.entrySet()) {
                                final K key = element.getKey();
                                if (!oldValue.containsKey(key)) {
                                    change.setAdded(key, element.getValue());
                                    for (int i = 0; i < curListChangeSize; i++) {
                                        curListChangeList[i].onChanged(change);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            locked = false;
        }
    }

    public static class SimpleChange<K, V> extends MapChangeListener.Change<K, V> {
        private K key;
        private V old;
        private V added;
        private boolean removeOp;
        private boolean addOp;

        public SimpleChange(ObservableMap<K, V> set) {
            super(set);
        }

        public SimpleChange(ObservableMap<K, V> set, MapChangeListener.Change<? extends K, ? extends V> source) {
            super(set);
            key = source.getKey();
            old = source.getValueRemoved();
            added = source.getValueAdded();
            addOp = source.wasAdded();
            removeOp = source.wasRemoved();
        }

        public SimpleChange<K, V> setRemoved(K key, V old) {
            this.key = key;
            this.old = old;
            this.added = null;
            addOp = false;
            removeOp = true;
            return this;
        }

        public SimpleChange<K, V> setAdded(K key, V added) {
            this.key = key;
            this.old = null;
            this.added = added;
            addOp = true;
            removeOp = false;
            return this;
        }

        public SimpleChange<K, V> setPut(K key, V old, V added) {
            this.key = key;
            this.old = old;
            this.added = added;
            addOp = true;
            removeOp = true;
            return this;
        }

        @Override
        public boolean wasAdded() {
            return addOp;
        }

        @Override
        public boolean wasRemoved() {
            return removeOp;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValueAdded() {
            return added;
        }

        @Override
        public V getValueRemoved() {
            return old;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (addOp) {
                if (removeOp) {
                    builder.append("replaced ").append(old).append(" by ").append(added);
                } else {
                    builder.append("added ").append(added);
                }
            } else {
                builder.append("removed ").append(old);
            }
            builder.append(" at key ").append(key);
            return builder.toString();
        }
    }
}