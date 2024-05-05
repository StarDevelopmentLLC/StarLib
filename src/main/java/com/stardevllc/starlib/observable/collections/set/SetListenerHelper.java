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

package com.stardevllc.starlib.observable.collections.set;

import java.util.Arrays;

public abstract class SetListenerHelper<E> {

    public static <E> SetListenerHelper<E> addListener(SetListenerHelper<E> helper, SetChangeListener<? super E> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null)? new SingleChange<>(listener) : helper.addListener(listener);
    }

    public static <E> SetListenerHelper<E> removeListener(SetListenerHelper<E> helper, SetChangeListener<? super E> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null)? null : helper.removeListener(listener);
    }

    public static <E> void fireValueChangedEvent(SetListenerHelper<E> helper, SetChangeListener.Change<? extends E> change) {
        if (helper != null) {
            helper.fireValueChangedEvent(change);
        }
    }

    public static <E> boolean hasListeners(SetListenerHelper<E> helper) {
        return helper != null;
    }

    protected abstract SetListenerHelper<E> addListener(SetChangeListener<? super E> listener);
    protected abstract SetListenerHelper<E> removeListener(SetChangeListener<? super E> listener);

    protected abstract void fireValueChangedEvent(SetChangeListener.Change<? extends E> change);

    private static class SingleChange<E> extends SetListenerHelper<E> {

        private final SetChangeListener<? super E> listener;

        private SingleChange(SetChangeListener<? super E> listener) {
            this.listener = listener;
        }

        @Override
        protected SetListenerHelper<E> addListener(SetChangeListener<? super E> listener) {
            return new Generic<>(this.listener, listener);
        }

        @Override
        protected SetListenerHelper<E> removeListener(SetChangeListener<? super E> listener) {
            return (listener.equals(this.listener))? null : this;
        }

        @Override
        protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> change) {
            try {
                listener.onChanged(change);
            } catch (Exception e) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }
    }

    private static class Generic<E> extends SetListenerHelper<E> {

        private SetChangeListener<? super E>[] changeListeners;
        private int changeSize;
        private boolean locked;

        private Generic(SetChangeListener<? super E> listener0, SetChangeListener<? super E> listener1) {
            this.changeListeners = new SetChangeListener[] {listener0, listener1};
            this.changeSize = 2;
        }

        private Generic(SetChangeListener<? super E> changeListener) {
            this.changeListeners = new SetChangeListener[] {changeListener};
            this.changeSize = 1;
        }

        @Override
        protected SetListenerHelper<E> addListener(SetChangeListener<? super E> listener) {
            if (changeListeners == null) {
                changeListeners = new SetChangeListener[] {listener};
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
        protected SetListenerHelper<E> removeListener(SetChangeListener<? super E> listener) {
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
                            final SetChangeListener<? super E>[] oldListeners = changeListeners;
                            if (locked) {
                                changeListeners = new SetChangeListener[changeListeners.length];
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
        protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> change) {
            final SetChangeListener<? super E>[] curChangeList = changeListeners;
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