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

import com.stardevllc.starlib.observable.binding.Bindings;
import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.WeakListener;
import com.stardevllc.starlib.observable.value.ObservableValue;
import com.stardevllc.starlib.observable.writable.WritableUUIDValue;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.UUID;

public class UUIDProperty extends ReadOnlyUUIDProperty implements Property<UUID>, WritableUUIDValue {

    private ObservableValue<? extends UUID> observable = null;
    private InvalidationListener listener = null;
    private boolean valid = true;

    public UUIDProperty() {
        super();
    }

    public UUIDProperty(UUID initialValue) {
        super(initialValue);
    }

    public UUIDProperty(Object bean, String name) {
        super(bean, name);
    }

    public UUIDProperty(Object bean, String name, UUID initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public void setValue(UUID v) {
        set(v);
    }
    
    @Override
    public void bindBidirectional(Property<UUID> other) {
        Bindings.bindBidirectional(this, other);
    }
    
    @Override
    public void unbindBidirectional(Property<UUID> other) {
        Bindings.unbindBidirectional(this, other);
    }
    
    public void unbindBidirectional(Object other) {
        Bindings.unbindBidirectional(this, other);
    }

    private void markInvalid() {
        if (valid) {
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    protected void invalidated() {
        //no-op
    }

    @Override
    public UUID get() {
        valid = true;
        return observable == null ? value : observable.getValue();
    }

    @Override
    public void set(UUID newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : ": "") + "A bound value cannot be set.");
        }
        if (!Objects.equals(value, newValue)) {
            value = newValue;
            markInvalid();
        }
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(ObservableValue<? extends UUID> newObservable) {
        if (newObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }
        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
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
        final StringBuilder result = new StringBuilder("StringProperty [");
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

    private static class Listener implements InvalidationListener, WeakListener {

        private final WeakReference<UUIDProperty> wref;

        public Listener(UUIDProperty ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            UUIDProperty ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }

        @Override
        public boolean wasGarbageCollected() {
            return wref.get() == null;
        }
    }
}