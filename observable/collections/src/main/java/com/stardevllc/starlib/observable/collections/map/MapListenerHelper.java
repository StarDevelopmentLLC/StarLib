/*
 * Copyright (c) 2012, 2022, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.collections.map;

import java.util.Arrays;

public abstract class MapListenerHelper<K, V> {

    public static <K, V> MapListenerHelper<K, V> addListener(MapListenerHelper<K, V> helper, MapChangeListener<? super K, ? super V> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null)? new SingleChange<>(listener) : helper.addListener(listener);
    }

    public static <K, V> MapListenerHelper<K, V> removeListener(MapListenerHelper<K, V> helper, MapChangeListener<? super K, ? super V> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null)? null : helper.removeListener(listener);
    }

    public static <K, V> void fireValueChangedEvent(MapListenerHelper<K, V> helper, MapChangeListener.Change<? extends K, ? extends V> change) {
        if (helper != null) {
            helper.fireValueChangedEvent(change);
        }
    }

    public static <K, V> boolean hasListeners(MapListenerHelper<K, V> helper) {
        return helper != null;
    }

    protected abstract MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener);
    protected abstract MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener);

    protected abstract void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change);

    private static class SingleChange<K, V> extends MapListenerHelper<K, V> {

        private final MapChangeListener<? super K, ? super V> listener;

        private SingleChange(MapChangeListener<? super K, ? super V> listener) {
            this.listener = listener;
        }

        @Override
        protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener) {
            return new Generic<>(this.listener, listener);
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener) {
            return (listener.equals(this.listener))? null : this;
        }

        @Override
        protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change) {
            try {
                listener.onChanged(change);
            } catch (Exception e) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }
    }

    private static class Generic<K, V> extends MapListenerHelper<K, V> {

        private MapChangeListener<? super K, ? super V>[] changeListeners;
        private int changeSize;
        private boolean locked;

        private Generic(MapChangeListener<? super K, ? super V> listener0, MapChangeListener<? super K, ? super V> listener1) {
            this.changeListeners = new MapChangeListener[] {listener0, listener1};
            this.changeSize = 2;
        }

        private Generic(MapChangeListener<? super K, ? super V> changeListener) {
            this.changeListeners = new MapChangeListener[] {changeListener};
            this.changeSize = 1;
        }

        @Override
        protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener) {
            if (changeListeners == null) {
                changeListeners = new MapChangeListener[] {listener};
                changeSize = 1;
            } else {
                final int oldCapacity = changeListeners.length;
                if (locked) {
                    final int newCapacity = (changeSize < oldCapacity)? oldCapacity : (oldCapacity * 3)/2 + 1;
                    changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                } else if (changeSize == oldCapacity) {
                    if (changeSize == oldCapacity) {
                        final int newCapacity = (oldCapacity * 3)/2 + 1;
                        changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                    }
                }
                changeListeners[changeSize++] = listener;
            }
            return this;
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener) {
            if (changeListeners != null) {
                for (int index = 0; index < changeSize; index++) {
                    if (listener.equals(changeListeners[index])) {
                        if (changeSize == 1) {
                            changeListeners = null;
                            changeSize = 0;
                        } else if ((changeSize == 2)) {
                            return new SingleChange<>(changeListeners[1-index]);
                        } else {
                            final int numMoved = changeSize - index - 1;
                            final MapChangeListener<? super K, ? super V>[] oldListeners = changeListeners;
                            if (locked) {
                                changeListeners = new MapChangeListener[changeListeners.length];
                                System.arraycopy(oldListeners, 0, changeListeners, 0, index);
                            }
                            if (numMoved > 0) {
                                System.arraycopy(oldListeners, index+1, changeListeners, index, numMoved);
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

        @Override
        protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change) {
            final MapChangeListener<? super K, ? super V>[] curChangeList = changeListeners;
            final int curChangeSize = changeSize;

            try {
                locked = true;
                for (int i = 0; i < curChangeSize; i++) {
                    try {
                        curChangeList[i].onChanged(change);
                    } catch (Exception e) {
                        Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                    }
                }
            } finally {
                locked = false;
            }
        }
    }
}