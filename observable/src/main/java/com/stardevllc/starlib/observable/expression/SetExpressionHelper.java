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

import com.stardevllc.starlib.observable.collections.set.ObservableSet;
import com.stardevllc.starlib.observable.collections.set.SetChangeListener;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableSetValue;

import java.util.Arrays;

public class SetExpressionHelper<E> {
    public static <E> SetExpressionHelper<E> addListener(SetExpressionHelper<E> helper, ObservableSetValue<E> observable, ChangeListener<? super ObservableSet<E>> listener) {
        if ((observable == null) || (listener == null)) {
            throw new NullPointerException();
        }
        return (helper == null) ? new SetExpressionHelper<>(observable, listener) : helper.addListener(listener);
    }

    public static <E> SetExpressionHelper<E> removeListener(SetExpressionHelper<E> helper, ChangeListener<? super ObservableSet<E>> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <E> SetExpressionHelper<E> addListener(SetExpressionHelper<E> helper, ObservableSetValue<E> observable, SetChangeListener<? super E> listener) {
        if ((observable == null) || (listener == null)) {
            throw new NullPointerException();
        }
        return (helper == null) ? new SetExpressionHelper<>(observable, listener) : helper.addListener(listener);
    }

    public static <E> SetExpressionHelper<E> removeListener(SetExpressionHelper<E> helper, SetChangeListener<? super E> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <E> void fireValueChangedEvent(SetExpressionHelper<E> helper) {
        if (helper != null) {
            helper.fireValueChangedEvent();
        }
    }

    public static <E> void fireValueChangedEvent(SetExpressionHelper<E> helper, SetChangeListener.Change<? extends E> change) {
        if (helper != null) {
            helper.fireValueChangedEvent(change);
        }
    }

    protected final ObservableSetValue<E> observable;

    protected SetExpressionHelper(ObservableSetValue<E> observable) {
        this.observable = observable;
    }

    private ChangeListener<? super ObservableSet<E>>[] changeListeners;
    private SetChangeListener<? super E>[] setChangeListeners;
    private int changeSize;
    private int setChangeSize;
    private boolean locked;
    private ObservableSet<E> currentValue;

    private SetExpressionHelper(ObservableSetValue<E> observable, ChangeListener<? super ObservableSet<E>> listener0, ChangeListener<? super ObservableSet<E>> listener1) {
        this(observable);
        this.changeListeners = new ChangeListener[]{listener0, listener1};
        this.changeSize = 2;
        this.currentValue = observable.getValue();
    }

    private SetExpressionHelper(ObservableSetValue<E> observable, SetChangeListener<? super E> listener0, SetChangeListener<? super E> listener1) {
        this(observable);
        this.setChangeListeners = new SetChangeListener[]{listener0, listener1};
        this.setChangeSize = 2;
        this.currentValue = observable.getValue();
    }

    private SetExpressionHelper(ObservableSetValue<E> observable, ChangeListener<? super ObservableSet<E>> changeListener) {
        this(observable);
        this.changeListeners = new ChangeListener[]{changeListener};
        this.changeSize = 1;
        this.currentValue = observable.getValue();
    }

    private SetExpressionHelper(ObservableSetValue<E> observable, SetChangeListener<? super E> listChangeListener) {
        this(observable);
        this.setChangeListeners = new SetChangeListener[]{listChangeListener};
        this.setChangeSize = 1;
        this.currentValue = observable.getValue();
    }

    private SetExpressionHelper(ObservableSetValue<E> observable, ChangeListener<? super ObservableSet<E>> changeListener, SetChangeListener<? super E> listChangeListener) {
        this(observable);
        this.changeListeners = new ChangeListener[]{changeListener};
        this.changeSize = 1;
        this.setChangeListeners = new SetChangeListener[]{listChangeListener};
        this.setChangeSize = 1;
        this.currentValue = observable.getValue();
    }

    protected SetExpressionHelper<E> addListener(ChangeListener<? super ObservableSet<E>> listener) {
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

    protected SetExpressionHelper<E> removeListener(ChangeListener<? super ObservableSet<E>> listener) {
        if (changeListeners != null) {
            for (int index = 0; index < changeSize; index++) {
                if (listener.equals(changeListeners[index])) {
                    if (changeSize == 1) {
                        if ((setChangeSize == 1)) {
                            return new SetExpressionHelper<>(observable, setChangeListeners[0]);
                        }
                        changeListeners = null;
                        changeSize = 0;
                    } else if ((changeSize == 2) && (setChangeSize == 0)) {
                        return new SetExpressionHelper<>(observable, changeListeners[1 - index]);
                    } else {
                        final int numMoved = changeSize - index - 1;
                        final ChangeListener<? super ObservableSet<E>>[] oldListeners = changeListeners;
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

    protected SetExpressionHelper<E> addListener(SetChangeListener<? super E> listener) {
        if (setChangeListeners == null) {
            setChangeListeners = new SetChangeListener[]{listener};
            setChangeSize = 1;
        } else {
            final int oldCapacity = setChangeListeners.length;
            if (locked) {
                final int newCapacity = (setChangeSize < oldCapacity) ? oldCapacity : (oldCapacity * 3) / 2 + 1;
                setChangeListeners = Arrays.copyOf(setChangeListeners, newCapacity);
            } else if (setChangeSize == oldCapacity) {
                if (setChangeSize == oldCapacity) {
                    final int newCapacity = (oldCapacity * 3) / 2 + 1;
                    setChangeListeners = Arrays.copyOf(setChangeListeners, newCapacity);
                }
            }
            setChangeListeners[setChangeSize++] = listener;
        }
        if (setChangeSize == 1) {
            currentValue = observable.getValue();
        }
        return this;
    }

    protected SetExpressionHelper<E> removeListener(SetChangeListener<? super E> listener) {
        if (setChangeListeners != null) {
            for (int index = 0; index < setChangeSize; index++) {
                if (listener.equals(setChangeListeners[index])) {
                    if (setChangeSize == 1) {
                        if ((changeSize == 1)) {
                            return new SetExpressionHelper<>(observable, changeListeners[0]);
                        }
                        setChangeListeners = null;
                        setChangeSize = 0;
                    } else if ((setChangeSize == 2) && (changeSize == 0)) {
                        return new SetExpressionHelper<>(observable, setChangeListeners[1 - index]);
                    } else {
                        final int numMoved = setChangeSize - index - 1;
                        final SetChangeListener<? super E>[] oldListeners = setChangeListeners;
                        if (locked) {
                            setChangeListeners = new SetChangeListener[setChangeListeners.length];
                            System.arraycopy(oldListeners, 0, setChangeListeners, 0, index + 1);
                        }
                        if (numMoved > 0) {
                            System.arraycopy(oldListeners, index + 1, setChangeListeners, index, numMoved);
                        }
                        setChangeSize--;
                        if (!locked) {
                            setChangeListeners[setChangeSize] = null; // Let gc do its work
                        }
                    }
                    break;
                }
            }
        }
        return this;
    }

    protected void fireValueChangedEvent() {
        if ((changeSize == 0) && (setChangeSize == 0)) {
            notifyListeners(currentValue, null);
        } else {
            final ObservableSet<E> oldValue = currentValue;
            currentValue = observable.getValue();
            notifyListeners(oldValue, null);
        }
    }

    protected void fireValueChangedEvent(final SetChangeListener.Change<? extends E> change) {
        final SimpleChange<E> mappedChange = (setChangeSize == 0) ? null : new SimpleChange<>(observable, change);
        notifyListeners(currentValue, mappedChange);
    }

    private void notifyListeners(ObservableSet<E> oldValue, SimpleChange<E> change) {
        final ChangeListener<? super ObservableSet<E>>[] curChangeList = changeListeners;
        final int curChangeSize = changeSize;
        final SetChangeListener<? super E>[] curListChangeList = setChangeListeners;
        final int curListChangeSize = setChangeSize;
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
                            for (final E element : oldValue) {
                                change.setRemoved(element);
                                for (int i = 0; i < curListChangeSize; i++) {
                                    curListChangeList[i].onChanged(change);
                                }
                            }
                        } else if (oldValue == null) {
                            for (final E element : currentValue) {
                                change.setAdded(element);
                                for (int i = 0; i < curListChangeSize; i++) {
                                    curListChangeList[i].onChanged(change);
                                }
                            }
                        } else {
                            for (final E element : oldValue) {
                                if (!currentValue.contains(element)) {
                                    change.setRemoved(element);
                                    for (int i = 0; i < curListChangeSize; i++) {
                                        curListChangeList[i].onChanged(change);
                                    }
                                }
                            }
                            for (final E element : currentValue) {
                                if (!oldValue.contains(element)) {
                                    change.setAdded(element);
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

    public static class SimpleChange<E> extends SetChangeListener.Change<E> {

        private E old;
        private E added;
        private boolean addOp;

        public SimpleChange(ObservableSet<E> set) {
            super(set);
        }

        public SimpleChange(ObservableSet<E> set, SetChangeListener.Change<? extends E> source) {
            super(set);
            old = source.getElementRemoved();
            added = source.getElementAdded();
            addOp = source.wasAdded();
        }

        public SimpleChange<E> setRemoved(E old) {
            this.old = old;
            this.added = null;
            addOp = false;
            return this;
        }

        public SimpleChange<E> setAdded(E added) {
            this.old = null;
            this.added = added;
            addOp = true;
            return this;
        }

        @Override
        public boolean wasAdded() {
            return addOp;
        }

        @Override
        public boolean wasRemoved() {
            return !addOp;
        }

        @Override
        public E getElementAdded() {
            return added;
        }

        @Override
        public E getElementRemoved() {
            return old;
        }

        @Override
        public String toString() {
            return addOp ? "added " + added : "removed " + old;
        }
    }
}