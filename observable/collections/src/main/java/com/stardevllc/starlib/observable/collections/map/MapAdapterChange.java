/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

import com.stardevllc.starlib.observable.collections.map.MapChangeListener.Change;

public class MapAdapterChange<K, V> extends Change<K, V> {
    private final Change<? extends K, ? extends V> change;

    public MapAdapterChange(ObservableMap<K, V> map, Change<? extends K, ? extends V> change) {
        super(map);
        this.change = change;
    }

    @Override
    public boolean wasAdded() {
        return change.wasAdded();
    }

    @Override
    public boolean wasRemoved() {
        return change.wasRemoved();
    }

    @Override
    public K getKey() {
        return change.getKey();
    }

    @Override
    public V getValueAdded() {
        return change.getValueAdded();
    }

    @Override
    public V getValueRemoved() {
        return change.getValueRemoved();
    }

    @Override
    public String toString() {
        return change.toString();
    }
}