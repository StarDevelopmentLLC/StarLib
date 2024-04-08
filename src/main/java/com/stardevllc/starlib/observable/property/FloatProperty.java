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
import com.stardevllc.starlib.observable.binding.FloatBinding;
import com.stardevllc.starlib.observable.value.ObservableFloatValue;
import com.stardevllc.starlib.observable.value.ObservableNumberValue;
import com.stardevllc.starlib.observable.value.ObservableValue;
import com.stardevllc.starlib.observable.writable.WritableFloatValue;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class FloatProperty extends ReadOnlyFloatProperty implements Property<Number>, WritableFloatValue {

    protected ObservableFloatValue observable = null;
    protected InvalidationListener listener = null;
    protected boolean valid = true;

    public FloatProperty() {
        super();
    }

    public FloatProperty(float initialValue) {
        super(initialValue);
    }

    public FloatProperty(Object bean, String name) {
        super(bean, name);
    }

    public FloatProperty(Object bean, String name, float initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public void setValue(Number v) {
        if (v == null) {
            set(0.0f);
        } else {
            set(v.floatValue());
        }
    }

    @Override
    public void bindBidirectional(Property<Number> other) {
        BidirectionalBinding.bind(this, other);
    }

    @Override
    public void unbindBidirectional(Property<Number> other) {
        BidirectionalBinding.unbind(this, other);
    }

    public static FloatProperty floatProperty(final Property<Float> property) {
        Objects.requireNonNull(property, "Property cannot be null");
        FloatProperty floatProperty = new FloatProperty(null, property.getName());
        BidirectionalBinding.bindNumber(floatProperty, property);
        return floatProperty;
    }

    @Override
    public ObjectProperty<Float> asObject() {
        ObjectProperty<Float> objectProperty = new ObjectProperty<>(null, getName(), get());
        BidirectionalBinding.bindNumber(objectProperty, this);
        return objectProperty;
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
    public float get() {
        valid = true;
        return observable == null ? value : observable.get();
    }

    @Override
    public void set(float newValue) {
        if (isBound()) {
            throw new java.lang.RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : " : "") + "A bound value cannot be set.");
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
    public void bind(final ObservableValue<? extends Number> rawObservable) {
        if (rawObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        ObservableFloatValue newObservable;
        if (rawObservable instanceof ObservableFloatValue) {
            newObservable = (ObservableFloatValue) rawObservable;
        } else if (rawObservable instanceof ObservableNumberValue numberValue) {
            newObservable = new FloatProperty.ValueWrapper(rawObservable) {

                @Override
                protected float computeValue() {
                    return numberValue.floatValue();
                }
            };
        } else {
            newObservable = new FloatProperty.ValueWrapper(rawObservable) {

                @Override
                protected float computeValue() {
                    final Number value = rawObservable.getValue();
                    return (value == null) ? 0.0f : value.floatValue();
                }
            };
        }


        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new FloatProperty.Listener(this);
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
            if (observable instanceof FloatProperty.ValueWrapper) {
                ((FloatProperty.ValueWrapper) observable).dispose();
            }
            observable = null;
        }
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("FloatProperty [");
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

        private final WeakReference<FloatProperty> wref;

        public Listener(FloatProperty ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            FloatProperty ref = wref.get();
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

    private abstract static class ValueWrapper extends FloatBinding {

        private ObservableValue<? extends Number> observable;

        public ValueWrapper(ObservableValue<? extends Number> observable) {
            this.observable = observable;
            bind(observable);
        }

        @Override
        public void dispose() {
            unbind(observable);
        }
    }
}