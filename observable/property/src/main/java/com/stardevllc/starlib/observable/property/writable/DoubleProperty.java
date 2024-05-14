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

package com.stardevllc.starlib.observable.property.writable;

import com.stardevllc.starlib.observable.ChangeListener;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.property.binding.BidirectionalBinding;
import com.stardevllc.starlib.observable.property.expression.ExpressionHelper;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyDoubleProperty;
import com.stardevllc.starlib.observable.value.ObservableDoubleValue;
import com.stardevllc.starlib.observable.value.ObservableNumberValue;
import com.stardevllc.starlib.observable.writable.WritableDoubleValue;

public class DoubleProperty extends ReadOnlyDoubleProperty implements Property<Number>, WritableDoubleValue {
    protected ExpressionHelper helper;
    
    public DoubleProperty() {
        super();
    }

    public DoubleProperty(double initialValue) {
        super(initialValue);
    }

    public DoubleProperty(Object bean, String name) {
        super(bean, name);
    }

    public DoubleProperty(Object bean, String name, double initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void setValue(Number v) {
        if (v == null) {
            set(0.0);
        } else {
            set(v.doubleValue());
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

    @Override
    public void set(double newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : " : "") + "A bound value cannot be set.");
        }
        if (value != newValue) {
            value = newValue;
            fireValueChangedEvent();
        }
    }
    
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
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

        ObservableDoubleValue newObservable;
        if (rawObservable instanceof ObservableDoubleValue) {
            newObservable = (ObservableDoubleValue) rawObservable;
        } else if (rawObservable instanceof ObservableNumberValue numberValue) {
            newObservable = new ReadOnlyDoubleProperty(numberValue.doubleValue());
        } else {
            newObservable = new ReadOnlyDoubleProperty((rawObservable.getValue() == null) ? 0.0 : rawObservable.getValue().doubleValue());
        }

        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
        }
    }

    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.get();
            observable = null;
        }
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("DoubleProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.isEmpty())) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
        } else {
            result.append("value: ").append(get());
        }
        result.append("]");
        return result.toString();
    }
}