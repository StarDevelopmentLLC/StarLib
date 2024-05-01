/*
 * Copyright (c) 2013, 2023, Oracle and/or its affiliates. All rights reserved.
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
package com.stardevllc.starlib.observable.collections.array;

import com.stardevllc.starlib.observable.expression.ExpressionHelperBase;

import java.util.Arrays;

public abstract class ArrayListenerHelper<T extends ObservableArray<T>> extends ExpressionHelperBase {
    
    public static <T extends ObservableArray<T>> ArrayListenerHelper<T> addListener(ArrayListenerHelper<T> helper, T observable, ArrayChangeListener<T> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null)? new ArrayListenerHelper.SingleChange<>(observable, listener) : helper.addListener(listener);
    }

    public static <T extends ObservableArray<T>> ArrayListenerHelper<T> removeListener(ArrayListenerHelper<T> helper, ArrayChangeListener<T> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null)? null : helper.removeListener(listener);
    }

    public static <T extends ObservableArray<T>> void fireValueChangedEvent(ArrayListenerHelper<T> helper, boolean sizeChanged, int from, int to) {
        if (helper != null && (from < to || sizeChanged)) {
            helper.fireValueChangedEvent(sizeChanged, from, to);
        }
    }

    public static <T extends ObservableArray<T>> boolean hasListeners(ArrayListenerHelper<T> helper) {
        return helper != null;
    }

    protected final T observable;

    public ArrayListenerHelper(T observable) {
        this.observable = observable;
    }

    protected abstract ArrayListenerHelper<T> addListener(ArrayChangeListener<T> listener);
    protected abstract ArrayListenerHelper<T> removeListener(ArrayChangeListener<T> listener);

    protected abstract void fireValueChangedEvent(boolean sizeChanged, int from, int to);

    private static class SingleChange<T extends ObservableArray<T>> extends ArrayListenerHelper<T> {

        private final ArrayChangeListener<T> listener;

        private SingleChange(T observable, ArrayChangeListener<T> listener) {
            super(observable);
            this.listener = listener;
        }

        @Override
        protected ArrayListenerHelper<T> addListener(ArrayChangeListener<T> listener) {
            return new Generic<>(observable, this.listener, listener);
        }

        @Override
        protected ArrayListenerHelper<T> removeListener(ArrayChangeListener<T> listener) {
            return (listener.equals(this.listener))? null : this;
        }

        @Override
        protected void fireValueChangedEvent(boolean sizeChanged, int from, int to) {
            try {
                listener.onChanged(observable, sizeChanged, from, to);
            } catch (Exception e) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }
    }

    private static class Generic<T extends ObservableArray<T>> extends ArrayListenerHelper<T> {

        private ArrayChangeListener<T>[] changeListeners;
        private int changeSize;
        private boolean locked;

        private Generic(T observable, ArrayChangeListener<T> listener0, ArrayChangeListener<T> listener1) {
            super(observable);
            this.changeListeners = new ArrayChangeListener[] {listener0, listener1};
            this.changeSize = 2;
        }

        private Generic(T observable, ArrayChangeListener<T> changeListener) {
            super(observable);
            this.changeListeners = new ArrayChangeListener[] {changeListener};
            this.changeSize = 1;
        }

        @Override
        protected ArrayListenerHelper<T> addListener(ArrayChangeListener<T> listener) {
            if (changeListeners == null) {
                changeListeners = new ArrayChangeListener[] {listener};
                changeSize = 1;
            } else {
                final int oldCapacity = changeListeners.length;
                if (locked) {
                    final int newCapacity = (changeSize < oldCapacity)? oldCapacity : (oldCapacity * 3)/2 + 1;
                    changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                } else if (changeSize == oldCapacity) {
                    changeSize = trim(changeSize, changeListeners);
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
        protected ArrayListenerHelper<T> removeListener(ArrayChangeListener<T> listener) {
            if (changeListeners != null) {
                for (int index = 0; index < changeSize; index++) {
                    if (listener.equals(changeListeners[index])) {
                        if (changeSize == 1) {
                            changeListeners = null;
                            changeSize = 0;
                        } else if ((changeSize == 2)) {
                            return new SingleChange<>(observable, changeListeners[1-index]);
                        } else {
                            final int numMoved = changeSize - index - 1;
                            final ArrayChangeListener<T>[] oldListeners = changeListeners;
                            if (locked) {
                                changeListeners = new ArrayChangeListener[changeListeners.length];
                                System.arraycopy(oldListeners, 0, changeListeners, 0, index+1);
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
        protected void fireValueChangedEvent(boolean sizeChanged, int from, int to) {
            final ArrayChangeListener<T>[] curChangeList = changeListeners;
            final int curChangeSize = changeSize;

            try {
                locked = true;
                for (int i = 0; i < curChangeSize; i++) {
                    try {
                        curChangeList[i].onChanged(observable, sizeChanged, from, to);
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