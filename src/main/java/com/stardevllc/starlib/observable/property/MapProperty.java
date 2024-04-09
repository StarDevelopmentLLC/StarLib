/*
 * Copyright (c) 2011, 2020, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.WeakListener;
import com.stardevllc.starlib.observable.binding.BidirectionalBinding;
import com.stardevllc.starlib.observable.collections.map.MapChangeListener;
import com.stardevllc.starlib.observable.collections.map.ObservableMap;
import com.stardevllc.starlib.observable.value.ObservableValue;
import com.stardevllc.starlib.observable.writable.WritableMapValue;

import java.lang.ref.WeakReference;

public class MapProperty<K, V> extends ReadOnlyMapProperty<K, V> implements Property<ObservableMap<K, V>>, WritableMapValue<K, V> {

    protected final MapChangeListener<K, V> mapChangeListener = change -> {
        invalidated();
        fireValueChangedEvent(change);
    };
    protected ObservableValue<? extends ObservableMap<K, V>> observable = null;
    protected InvalidationListener listener = null;
    protected boolean valid = true;

    public MapProperty() {
    }

    @Override
    public ObservableMap<K, V> get() {
        if (!valid) {
            value = observable == null ? value : observable.getValue();
            valid = true;
            if (value != null) {
                value.addListener(mapChangeListener);
            }
        }
        return value;
    }
    
    @Override
    public void setValue(ObservableMap<K, V> v) {
        set(v);
    }
    
    @Override
    public void bindBidirectional(Property<ObservableMap<K, V>> other) {
        BidirectionalBinding.bind(this, other);
    }
    
    @Override
    public void unbindBidirectional(Property<ObservableMap<K, V>> other) {
        BidirectionalBinding.unbind(this, other);
    }

    private void markInvalid(ObservableMap<K, V> oldValue) {
        if (valid) {
            if (oldValue != null) {
                oldValue.removeListener(mapChangeListener);
            }
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    protected void invalidated() {
        //no-op
    }

    @Override
    public void set(ObservableMap<K, V> newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : ": "") + "A bound value cannot be set.");
        }
        if (value != newValue) {
            final ObservableMap<K, V> oldValue = value;
            value = newValue;
            markInvalid(oldValue);
        }
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(final ObservableValue<? extends ObservableMap<K, V>> newObservable) {
        if (newObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }
        if (newObservable != observable) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener<>(this);
            }
            observable.addListener(listener);
            markInvalid(value);
        }
    }

    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.getValue();
            observable.removeListener(listener);
            observable = null;
        }
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("MapProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.isEmpty())) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
            if (valid) {
                result.append("value: ").append(get());
            } else {
                result.append("invalid");
            }
        } else {
            result.append("value: ").append(get());
        }
        result.append("]");
        return result.toString();
    }

    private static class Listener<K,V> implements InvalidationListener, WeakListener {

        private final WeakReference<MapProperty<K,V>> wref;

        public Listener(MapProperty<K,V> ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            MapProperty<K,V> ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid(ref.value);
            }
        }

        @Override
        public boolean wasGarbageCollected() {
            return wref.get() == null;
        }
    }
}