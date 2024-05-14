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

package com.stardevllc.starlib.observable.expression;

import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableValue;

import java.util.Arrays;
import java.util.Objects;

public class ExpressionHelper<T> {
    public static <T> ExpressionHelper<T> addListener(ExpressionHelper<T> helper, ObservableValue<T> observable, ChangeListener<? super T> listener) {
        if ((observable == null) || (listener == null)) {
            throw new NullPointerException();
        }
        return (helper == null) ? new ExpressionHelper<>(observable, observable.getValue(), listener) : helper.addListener(listener);
    }

    public static <T> ExpressionHelper<T> removeListener(ExpressionHelper<T> helper, ChangeListener<? super T> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <T> void fireValueChangedEvent(ExpressionHelper<T> helper) {
        if (helper != null) {
            helper.fireValueChangedEvent();
        }
    }

    private final ObservableValue<T> observable;

    private ChangeListener<? super T>[] changeListeners;
    private int changeSize;
    private boolean locked;
    private T currentValue;

    private ExpressionHelper(ObservableValue<T> observable) {
        this.observable = observable;
    }

    private ExpressionHelper(ObservableValue<T> observable, T currentValue, ChangeListener<? super T> changeListener) {
        this(observable);
        this.changeListeners = new ChangeListener[]{changeListener};
        this.changeSize = 1;
        this.currentValue = currentValue;
    }

    protected ExpressionHelper<T> addListener(ChangeListener<? super T> listener) {
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

    protected ExpressionHelper<T> removeListener(ChangeListener<? super T> listener) {
        if (changeListeners != null) {
            for (int index = 0; index < changeSize; index++) {
                if (listener.equals(changeListeners[index])) {
                    if (changeSize == 1) {
                        changeListeners = null;
                        changeSize = 0;
                        currentValue = null;  // clear current value to avoid stale reference
                    } else if ((changeSize == 2)) {
                        return new ExpressionHelper<>(observable, currentValue, changeListeners[1 - index]);
                    } else {
                        final int numMoved = changeSize - index - 1;
                        final ChangeListener<? super T>[] oldListeners = changeListeners;
                        if (locked) {
                            changeListeners = new ChangeListener[changeListeners.length];
                            System.arraycopy(oldListeners, 0, changeListeners, 0, index);
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

    protected void fireValueChangedEvent() {
        final ChangeListener<? super T>[] curChangeList = changeListeners;
        final int curChangeSize = changeSize;

        try {
            locked = true;
            if (curChangeSize > 0) {
                final T oldValue = currentValue;
                currentValue = observable.getValue();
                final boolean changed = !Objects.equals(currentValue, oldValue);
                if (changed) {
                    for (int i = 0; i < curChangeSize; i++) {
                        try {
                            curChangeList[i].changed(observable, oldValue, currentValue);
                        } catch (Exception e) {
                            Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                        }
                    }
                }
            }
        } finally {
            locked = false;
        }
    }
}