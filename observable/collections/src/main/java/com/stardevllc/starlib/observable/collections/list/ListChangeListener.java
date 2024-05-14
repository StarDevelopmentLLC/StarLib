/*
 * Copyright (c) 2010, 2021, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface ListChangeListener<E> {

    abstract class Change<E> {
        private final ObservableList<E> list;

        public abstract boolean next();

        public abstract void reset();

        public Change(ObservableList<E> list) {
            this.list = list;
        }

        public ObservableList<E> getList() {
            return list;
        }

        public abstract int getFrom();

        public abstract int getTo();

        public abstract List<E> getRemoved();

        public boolean wasPermutated() {
            return getPermutation().length != 0;
        }

        public boolean wasAdded() {
            return !wasPermutated() && !wasUpdated() && getFrom() < getTo();
        }

        public boolean wasRemoved() {
            return !getRemoved().isEmpty();
        }

        public boolean wasReplaced() {
            return wasAdded() && wasRemoved();
        }

        public boolean wasUpdated() {
            return false;
        }

        public List<E> getAddedSubList() {
            return wasAdded()? getList().subList(getFrom(), getTo()) : Collections.emptyList();
        }

        public int getRemovedSize() {
            return getRemoved().size();
        }

        public int getAddedSize() {
            return wasAdded() ? getTo() - getFrom() : 0;
        }

        protected abstract int[] getPermutation();

        public int getPermutation(int i) {
            if (!wasPermutated()) {
                throw new IllegalStateException("Not a permutation change");
            }
            return getPermutation()[i - getFrom()];
        }

    }
    void onChanged(Change<? extends E> c);
}