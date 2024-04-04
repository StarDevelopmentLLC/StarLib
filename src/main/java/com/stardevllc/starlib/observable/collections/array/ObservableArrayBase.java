/*
 * Copyright (c) 2013, 2020, Oracle and/or its affiliates. All rights reserved.
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

import com.stardevllc.starlib.observable.InvalidationListener;

public abstract class ObservableArrayBase<T extends ObservableArray<T>> implements ObservableArray<T> {

    private ArrayListenerHelper<T> listenerHelper;
    protected int size = 0;

    public ObservableArrayBase() {
    }
    
    protected abstract void copyArray(int srcIndex, int length);

    protected void setAllInternal(ObservableArray src, int srcIndex, int length) {
        boolean sizeChanged = size() != length;
        if (src == this) {
            if (srcIndex == 0) {
                resize(length);
            } else {
                copyArray(srcIndex, length);
                size = length;
                fireChange(sizeChanged, 0, size);
            }
        } else {
            size = 0;
            ensureCapacity(length);
            copyArray(srcIndex, length);
            size = length;
            fireChange(sizeChanged, 0, size);
        }
    }
    
    protected abstract void fillArray(int minSize);

    @Override
    public void resize(int newSize) {
        if (newSize < 0) {
            throw new NegativeArraySizeException("Can't resize to negative value: " + newSize);
        }
        ensureCapacity(newSize);
        int minSize = Math.min(size, newSize);
        boolean sizeChanged = size != newSize;
        size = newSize;
        fillArray(minSize);
        fireChange(sizeChanged, minSize, newSize);
    }
    
    protected abstract int getArrayLength();

    protected abstract int hugeCapacity(int minCapacity);
    
    protected abstract int getMaxArraySize();

    protected void growCapacity(int length) {
        int minCapacity = size + length;
        int oldCapacity = getArrayLength();
        if (minCapacity > getArrayLength()) {
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity < minCapacity) newCapacity = minCapacity;
            if (newCapacity > getMaxArraySize()) newCapacity = hugeCapacity(minCapacity);
            ensureCapacity(newCapacity);
        } else if (length > 0 && minCapacity < 0) {
            throw new OutOfMemoryError(); // overflow
        }
    }

    @Override public final void addListener(InvalidationListener listener) {
        listenerHelper = ArrayListenerHelper.addListener(listenerHelper, (T) this, listener);
    }

    @Override public final void removeListener(InvalidationListener listener) {
        listenerHelper = ArrayListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override public final void addListener(ArrayChangeListener<T> listener) {
        listenerHelper = ArrayListenerHelper.addListener(listenerHelper, (T) this, listener);
    }

    @Override public final void removeListener(ArrayChangeListener<T> listener) {
        listenerHelper = ArrayListenerHelper.removeListener(listenerHelper, listener);
    }

    protected final void fireChange(boolean sizeChanged, int from, int to) {
        ArrayListenerHelper.fireValueChangedEvent(listenerHelper, sizeChanged, from, to);
    }
    
    public abstract boolean isNull();
    public abstract Object getElement(int index);

    @Override
    public String toString() {
        if (isNull())
            return "null";

        int iMax = getArrayLength() - 1;
        if (iMax == -1)
            return "[]";
        
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(getElement(i));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }
}