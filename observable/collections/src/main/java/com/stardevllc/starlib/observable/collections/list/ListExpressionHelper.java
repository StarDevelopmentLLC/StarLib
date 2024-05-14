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

package com.stardevllc.starlib.observable.collections.list;

import com.stardevllc.starlib.observable.ChangeListener;
import com.stardevllc.starlib.observable.collections.ObservableCollections;
import com.stardevllc.starlib.observable.collections.list.ListChangeListener.Change;

import java.util.Arrays;

public class ListExpressionHelper<E> {
    public static <E> ListExpressionHelper<E> addListener(ListExpressionHelper<E> helper, ObservableListValue<E> observable, ChangeListener<? super ObservableList<E>> listener) {
        if ((observable == null) || (listener == null)) {
            throw new NullPointerException();
        }
        return (helper == null)? new ListExpressionHelper<>(observable, listener) : helper.addListener(listener);
    }

    public static <E> ListExpressionHelper<E> removeListener(ListExpressionHelper<E> helper, ChangeListener<? super ObservableList<E>> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null)? null : helper.removeListener(listener);
    }

    public static <E> ListExpressionHelper<E> addListener(ListExpressionHelper<E> helper, ObservableListValue<E> observable, ListChangeListener<? super E> listener) {
        if ((observable == null) || (listener == null)) {
            throw new NullPointerException();
        }
        return (helper == null)? new ListExpressionHelper<>(observable, listener) : helper.addListener(listener);
    }

    public static <E> ListExpressionHelper<E> removeListener(ListExpressionHelper<E> helper, ListChangeListener<? super E> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null)? null : helper.removeListener(listener);
    }

    public static <E> void fireValueChangedEvent(ListExpressionHelper<E> helper) {
        if (helper != null) {
            helper.fireValueChangedEvent();
        }
    }

    public static <E> void fireValueChangedEvent(ListExpressionHelper<E> helper, Change<? extends E> change) {
        if (helper != null) {
            helper.fireValueChangedEvent(change);
        }
    }

    protected final ObservableListValue<E> observable;

    private ChangeListener<? super ObservableList<E>>[] changeListeners;
    private ListChangeListener<? super E>[] listChangeListeners;
    private int changeSize;
    private int listChangeSize;
    private boolean locked;
    private ObservableList<E> currentValue;

    protected ListExpressionHelper(ObservableListValue<E> observable) {
        this.observable = observable;
    }

    private ListExpressionHelper(ObservableListValue<E> observable, ChangeListener<? super ObservableList<E>> listener0, ChangeListener<? super ObservableList<E>> listener1) {
        this(observable);
        this.changeListeners = new ChangeListener[] {listener0, listener1};
        this.changeSize = 2;
        this.currentValue = observable.getValue();
    }

    private ListExpressionHelper(ObservableListValue<E> observable, ListChangeListener<? super E> listener0, ListChangeListener<? super E> listener1) {
        this(observable);
        this.listChangeListeners = new ListChangeListener[] {listener0, listener1};
        this.listChangeSize = 2;
        this.currentValue = observable.getValue();
    }

    private ListExpressionHelper(ObservableListValue<E> observable, ChangeListener<? super ObservableList<E>> changeListener) {
        this(observable);
        this.changeListeners = new ChangeListener[] {changeListener};
        this.changeSize = 1;
        this.currentValue = observable.getValue();
    }

    private ListExpressionHelper(ObservableListValue<E> observable, ListChangeListener<? super E> listChangeListener) {
        this(observable);
        this.listChangeListeners = new ListChangeListener[] {listChangeListener};
        this.listChangeSize = 1;
        this.currentValue = observable.getValue();
    }

    private ListExpressionHelper(ObservableListValue<E> observable, ChangeListener<? super ObservableList<E>> changeListener, ListChangeListener<? super E> listChangeListener) {
        this(observable);
        this.changeListeners = new ChangeListener[] {changeListener};
        this.changeSize = 1;
        this.listChangeListeners = new ListChangeListener[] {listChangeListener};
        this.listChangeSize = 1;
        this.currentValue = observable.getValue();
    }

    protected ListExpressionHelper<E> addListener(ChangeListener<? super ObservableList<E>> listener) {
        if (changeListeners == null) {
            changeListeners = new ChangeListener[] {listener};
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
        if (changeSize == 1) {
            currentValue = observable.getValue();
        }
        return this;
    }

    protected ListExpressionHelper<E> removeListener(ChangeListener<? super ObservableList<E>> listener) {
        if (changeListeners != null) {
            for (int index = 0; index < changeSize; index++) {
                if (listener.equals(changeListeners[index])) {
                    if (changeSize == 1) {
                        if ((listChangeSize == 1)) {
                            return new ListExpressionHelper<>(observable, listChangeListeners[0]);
                        }
                        changeListeners = null;
                        changeSize = 0;
                    } else if ((changeSize == 2) && (listChangeSize == 0)) {
                        return new ListExpressionHelper<>(observable, changeListeners[1-index]);
                    } else {
                        final int numMoved = changeSize - index - 1;
                        final ChangeListener<? super ObservableList<E>>[] oldListeners = changeListeners;
                        if (locked) {
                            changeListeners = new ChangeListener[changeListeners.length];
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

    protected ListExpressionHelper<E> addListener(ListChangeListener<? super E> listener) {
        if (listChangeListeners == null) {
            listChangeListeners = new ListChangeListener[] {listener};
            listChangeSize = 1;
        } else {
            final int oldCapacity = listChangeListeners.length;
            if (locked) {
                final int newCapacity = (listChangeSize < oldCapacity)? oldCapacity : (oldCapacity * 3)/2 + 1;
                listChangeListeners = Arrays.copyOf(listChangeListeners, newCapacity);
            } else if (listChangeSize == oldCapacity) {
                if (listChangeSize == oldCapacity) {
                    final int newCapacity = (oldCapacity * 3)/2 + 1;
                    listChangeListeners = Arrays.copyOf(listChangeListeners, newCapacity);
                }
            }
            listChangeListeners[listChangeSize++] = listener;
        }
        if (listChangeSize == 1) {
            currentValue = observable.getValue();
        }
        return this;
    }

    protected ListExpressionHelper<E> removeListener(ListChangeListener<? super E> listener) {
        if (listChangeListeners != null) {
            for (int index = 0; index < listChangeSize; index++) {
                if (listener.equals(listChangeListeners[index])) {
                    if (listChangeSize == 1) {
                        if ((changeSize == 1)) {
                            return new ListExpressionHelper<>(observable, changeListeners[0]);
                        }
                        listChangeListeners = null;
                        listChangeSize = 0;
                    } else if ((listChangeSize == 2) && (changeSize == 0)) {
                        return new ListExpressionHelper<>(observable, listChangeListeners[1-index]);
                    } else {
                        final int numMoved = listChangeSize - index - 1;
                        final ListChangeListener<? super E>[] oldListeners = listChangeListeners;
                        if (locked) {
                            listChangeListeners = new ListChangeListener[listChangeListeners.length];
                            System.arraycopy(oldListeners, 0, listChangeListeners, 0, index+1);
                        }
                        if (numMoved > 0) {
                            System.arraycopy(oldListeners, index+1, listChangeListeners, index, numMoved);
                        }
                        listChangeSize--;
                        if (!locked) {
                            listChangeListeners[listChangeSize] = null; // Let gc do its work
                        }
                    }
                    break;
                }
            }
        }
        return this;
    }

    protected void fireValueChangedEvent() {
        if ((changeSize == 0) && (listChangeSize == 0)) {
            notifyListeners(currentValue, null, false);
        } else {
            final ObservableList<E> oldValue = currentValue;
            currentValue = observable.getValue();
            if (currentValue != oldValue) {
                Change<E> change = null;
                if (listChangeSize > 0) {
                    final int safeSize = (currentValue == null)? 0 : currentValue.size();
                    final ObservableList<E> safeOldValue = (oldValue == null)?
                            ObservableCollections.emptyObservableList()
                            : ObservableCollections.unmodifiableObservableList(oldValue);
                    change = new NonIterableChange.GenericAddRemoveChange<>(0, safeSize, safeOldValue, observable);
                }
                notifyListeners(oldValue, change, false);
            } else {
                notifyListeners(currentValue, null, true);
            }
        }
    }


    protected void fireValueChangedEvent(final Change<? extends E> change) {
        final Change<E> mappedChange = (listChangeSize == 0)? null : new SourceAdapterChange<>(observable, change);
        notifyListeners(currentValue, mappedChange, false);
    }

    private void notifyListeners(ObservableList<E> oldValue, Change<E> change, boolean noChange) {
        final ChangeListener<? super ObservableList<E>>[] curChangeList = changeListeners;
        final int curChangeSize = changeSize;
        final ListChangeListener<? super E>[] curListChangeList = listChangeListeners;
        final int curListChangeSize = listChangeSize;
        try {
            locked = true;
            if (!noChange) {
                for (int i = 0; i < curChangeSize; i++) {
                    curChangeList[i].changed(observable, oldValue, currentValue);
                }
                if (change != null) {
                    for (int i = 0; i < curListChangeSize; i++) {
                        change.reset();
                        curListChangeList[i].onChanged(change);
                    }
                }
            }
        } finally {
            locked = false;
        }
    }
}