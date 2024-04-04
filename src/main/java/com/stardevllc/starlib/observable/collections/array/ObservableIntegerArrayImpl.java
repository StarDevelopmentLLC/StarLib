/*
 * Copyright (c) 2013, 2022, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Arrays;

public class ObservableIntegerArrayImpl extends ObservableArrayBase<ObservableIntegerArray> implements ObservableIntegerArray {

    private static final int[] INITIAL = new int[0];

    private int[] array = INITIAL;

    public ObservableIntegerArrayImpl() {
    }

    public ObservableIntegerArrayImpl(int... elements) {
        setAll(elements);
    }

    public ObservableIntegerArrayImpl(ObservableIntegerArray src) {
        setAll(src);
    }

    @Override
    public void clear() {
        resize(0);
    }

    @Override
    public int size() {
        return size;
    }

    private void addAllInternal(ObservableIntegerArray src, int srcIndex, int length) {
        growCapacity(length);
        src.copyTo(srcIndex, array, size, length);
        size += length;
        fireChange(length != 0, size - length, size);
    }

    private void addAllInternal(int[] src, int srcIndex, int length) {
        growCapacity(length);
        System.arraycopy(src, srcIndex, array, size, length);
        size += length;
        fireChange(length != 0, size - length, size);
    }

    @Override
    public void addAll(ObservableIntegerArray src) {
        addAllInternal(src, 0, src.size());
    }

    @Override
    public void addAll(int... elements) {
        addAllInternal(elements, 0, elements.length);
    }

    @Override
    public void addAll(ObservableIntegerArray src, int srcIndex, int length) {
        rangeCheck(src, srcIndex, length);
        addAllInternal(src, srcIndex, length);
    }

    @Override
    public void addAll(int[] src, int srcIndex, int length) {
        rangeCheck(src, srcIndex, length);
        addAllInternal(src, srcIndex, length);
    }

    @Override
    protected void copyArray(int srcIndex, int length) {
        System.arraycopy(array, srcIndex, array, 0, length);
    }

    private void setAllInternal(int[] src, int srcIndex, int length) {
        boolean sizeChanged = size() != length;
        size = 0;
        ensureCapacity(length);
        System.arraycopy(src, srcIndex, array, 0, length);
        size = length;
        fireChange(sizeChanged, 0, size);
    }

    @Override
    public void setAll(ObservableIntegerArray src) {
        setAllInternal(src, 0, src.size());
    }

    @Override
    public void setAll(ObservableIntegerArray src, int srcIndex, int length) {
        rangeCheck(src, srcIndex, length);
        setAllInternal(src, srcIndex, length);
    }

    @Override
    public void setAll(int[] src, int srcIndex, int length) {
        rangeCheck(src, srcIndex, length);
        setAllInternal(src, srcIndex, length);
    }

    @Override
    public void setAll(int... src) {
        setAllInternal(src, 0, src.length);
    }

    @Override
    public void set(int destIndex, int[] src, int srcIndex, int length) {
        rangeCheck(destIndex + length);
        System.arraycopy(src, srcIndex, array, destIndex, length);
        fireChange(false, destIndex, destIndex + length);
    }

    @Override
    public void set(int destIndex, ObservableIntegerArray src, int srcIndex, int length) {
        rangeCheck(destIndex + length);
        src.copyTo(srcIndex, array, destIndex, length);
        fireChange(false, destIndex, destIndex + length);
    }

    @Override
    public int[] toArray(int[] dest) {
        if ((dest == null) || (size() > dest.length)) {
            dest = new int[size()];
        }
        System.arraycopy(array, 0, dest, 0, size());
        return dest;
    }

    @Override
    public int get(int index) {
        rangeCheck(index + 1);
        return array[index];
    }

    @Override
    public void set(int index, int value) {
        rangeCheck(index + 1);
        array[index] = value;
        fireChange(false, index, index + 1);
    }

    @Override
    public int[] toArray(int index, int[] dest, int length) {
        rangeCheck(index + length);
        if ((dest == null) || (length > dest.length)) {
            dest = new int[length];
        }
        System.arraycopy(array, index, dest, 0, length);
        return dest;
    }

    @Override
    public void copyTo(int srcIndex, int[] dest, int destIndex, int length) {
        rangeCheck(srcIndex + length);
        System.arraycopy(array, srcIndex, dest, destIndex, length);
    }

    @Override
    public void copyTo(int srcIndex, ObservableIntegerArray dest, int destIndex, int length) {
        rangeCheck(srcIndex + length);
        dest.set(destIndex, array, srcIndex, length);
    }

    @Override
    protected void fillArray(int minSize) {
        Arrays.fill(array, minSize, size, 0);
    }

    @Override
    protected int getArrayLength() {
        return array.length;
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    @Override
    public void ensureCapacity(int capacity) {
        if (array.length < capacity) {
            array = Arrays.copyOf(array, capacity);
        }
    }

    protected int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

    @Override
    protected int getMaxArraySize() {
        return MAX_ARRAY_SIZE;
    }

    @Override
    public boolean isNull() {
        return array == null;
    }

    @Override
    public Integer getElement(int index) {
        return array[index];
    }

    @Override
    public void trimToSize() {
        if (array.length != size) {
            int[] newArray = new int[size];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    private void rangeCheck(int size) {
        if (size > this.size) throw new ArrayIndexOutOfBoundsException(this.size);
    }

    private void rangeCheck(ObservableIntegerArray src, int srcIndex, int length) {
        if (src == null) throw new NullPointerException();
        if (srcIndex < 0 || srcIndex + length > src.size()) {
            throw new ArrayIndexOutOfBoundsException(src.size());
        }
        if (length < 0) throw new ArrayIndexOutOfBoundsException(-1);
    }

    private void rangeCheck(int[] src, int srcIndex, int length) {
        if (src == null) throw new NullPointerException();
        if (srcIndex < 0 || srcIndex + length > src.length) {
            throw new ArrayIndexOutOfBoundsException(src.length);
        }
        if (length < 0) throw new ArrayIndexOutOfBoundsException(-1);
    }
}