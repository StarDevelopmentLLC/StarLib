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

import com.stardevllc.starlib.observable.binding.BidirectionalBinding;
import com.stardevllc.starlib.observable.binding.Bindings;
import com.stardevllc.starlib.observable.binding.IntegerBinding;
import com.stardevllc.starlib.observable.expression.ExpressionHelper;
import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.WeakListener;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableIntegerValue;
import com.stardevllc.starlib.observable.value.ObservableNumberValue;
import com.stardevllc.starlib.observable.value.ObservableValue;
import com.stardevllc.starlib.observable.writable.WritableIntegerValue;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class IntegerProperty extends ReadOnlyIntegerProperty implements Property<Number>, WritableIntegerValue {

    private ObservableIntegerValue observable = null;
    private InvalidationListener listener = null;
    private boolean valid = true;
    private ExpressionHelper<Number> helper = null;
    
    public IntegerProperty() {
        super();
    }

    public IntegerProperty(int initialValue) {
        super(initialValue);
    }

    public IntegerProperty(Object bean, String name) {
        super(bean, name);
    }

    public IntegerProperty(Object bean, String name, int initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }
    
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
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
    public int get() {
        valid = true;
        return observable == null ? value : observable.get();
    }
    
    @Override
    public void set(int newValue) {
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
    public void setValue(Number v) {
        if (v == null) {
            set(0);
        } else {
            set(v.intValue());
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

        ObservableIntegerValue newObservable;
        if (rawObservable instanceof ObservableIntegerValue) {
            newObservable = (ObservableIntegerValue)rawObservable;
        } else if (rawObservable instanceof ObservableNumberValue numberValue) {
            newObservable = new IntegerProperty.ValueWrapper(rawObservable) {

                @Override
                protected int computeValue() {
                    return numberValue.intValue();
                }
            };
        } else {
            newObservable = new IntegerProperty.ValueWrapper(rawObservable) {

                @Override
                protected int computeValue() {
                    final Number value = rawObservable.getValue();
                    return (value == null)? 0 : value.intValue();
                }
            };
        }

        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new IntegerProperty.Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.get();
            observable.removeListener(listener);
            if (observable instanceof IntegerProperty.ValueWrapper) {
                ((IntegerProperty.ValueWrapper)observable).dispose();
            }
            observable = null;
        }
    }
    
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("IntegerProperty [");
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

        private final WeakReference<IntegerProperty> wref;

        public Listener(IntegerProperty ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            IntegerProperty ref = wref.get();
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

    private abstract static class ValueWrapper extends IntegerBinding {

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
    
    @Override
    public void bindBidirectional(Property<Number> other) {
        Bindings.bindBidirectional(this, other);
    }
    
    @Override
    public void unbindBidirectional(Property<Number> other) {
        Bindings.unbindBidirectional(this, other);
    }
    
    public static IntegerProperty integerProperty(final Property<Integer> property) {
        Objects.requireNonNull(property, "Property cannot be null");
        IntegerProperty integerProperty = new IntegerProperty(null, property.getName());
        BidirectionalBinding.bindNumber(integerProperty, property);
        return integerProperty;
    }
    
    @Override
    public ObjectProperty<Integer> asObject() {
        ObjectProperty<Integer> objectProperty = new ObjectProperty<>(null, getName(), get());
        BidirectionalBinding.bindNumber(objectProperty, this);
        return objectProperty;
    }
}