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
import com.stardevllc.starlib.observable.binding.BooleanBinding;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;
import com.stardevllc.starlib.observable.value.ObservableValue;
import com.stardevllc.starlib.observable.writable.WritableBooleanValue;

import java.lang.ref.WeakReference;
import java.util.Objects;


public class BooleanProperty extends ReadOnlyBooleanProperty implements Property<Boolean>, WritableBooleanValue {

    private ObservableBooleanValue observable = null;
    private InvalidationListener listener = null;
    private boolean valid = true;
    
    public BooleanProperty() {
        super();
    }
    
    public BooleanProperty(boolean initialValue) {
        super(initialValue);
    }
    
    public BooleanProperty(Object bean, String name) {
        super(bean, name);
    }
    
    public BooleanProperty(Object bean, String name, boolean initialValue) {
        super(bean, name, initialValue);
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
    public boolean get() {
        valid = true;
        return observable == null ? value : observable.get();
    }

    @Override
    public void set(boolean newValue) {
        if (isBound()) {
            throw new java.lang.RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : ": "") + "A bound value cannot be set.");
        }
        if (value != newValue) {
            value = newValue;
            markInvalid();
        }
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(final ObservableValue<? extends Boolean> rawObservable) {
        if (rawObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        ObservableBooleanValue newObservable = (rawObservable instanceof ObservableBooleanValue obv) ? obv : new BooleanBinding(rawObservable::getValue, rawObservable);

        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new BooleanProperty.Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
        }
    }
    
    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.get();
            observable.removeListener(listener);
            if (observable instanceof BooleanBinding booleanBinding) {
                booleanBinding.dispose();
            }
            observable = null;
        }
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("BooleanProperty [");
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

        private final WeakReference<BooleanProperty> wref;

        public Listener(BooleanProperty ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            BooleanProperty ref = wref.get();
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

    @Override
    public void setValue(Boolean v) {
        set(Objects.requireNonNullElse(v, false));
    }

    @Override
    public void bindBidirectional(Property<Boolean> other) {
        BidirectionalBinding.bind(this, other);
    }

    @Override
    public void unbindBidirectional(Property<Boolean> other) {
        BidirectionalBinding.unbind(this, other);
    }
    
    public static BooleanProperty booleanProperty(final Property<Boolean> property) {
        Objects.requireNonNull(property, "Property cannot be null");
        BooleanProperty booleanProperty = new BooleanProperty(null, property.getName());
        BidirectionalBinding.bind(booleanProperty, property);
        return booleanProperty;
    }
    
    @Override
    public ObjectProperty<Boolean> asObject() {
        ObjectProperty<Boolean> objectProperty = new ObjectProperty<>(null, getName(), get());
        BidirectionalBinding.bind(objectProperty, this);
        return objectProperty;
    }
}
