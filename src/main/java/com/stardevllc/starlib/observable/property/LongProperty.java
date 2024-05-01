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
import com.stardevllc.starlib.observable.binding.LongBinding;
import com.stardevllc.starlib.observable.value.ObservableLongValue;
import com.stardevllc.starlib.observable.value.ObservableNumberValue;
import com.stardevllc.starlib.observable.value.ObservableValue;
import com.stardevllc.starlib.observable.writable.WritableLongValue;

public class LongProperty extends ReadOnlyLongProperty implements Property<Number>, WritableLongValue {

    protected ObservableLongValue observable = null;
    protected boolean valid = true;

    public LongProperty() {
        super();
    }

    public LongProperty(long initialValue) {
        super(initialValue);
    }

    public LongProperty(Object bean, String name) {
        super(bean, name);
    }

    public LongProperty(Object bean, String name, long initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public void setValue(Number v) {
        if (v == null) {
            set(0L);
        } else {
            set(v.longValue());
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
    public ObjectProperty<Long> asObject() {
        ObjectProperty<Long> objectProperty = new ObjectProperty<>(null, getName(), get());
        BidirectionalBinding.bindNumber(objectProperty, this);
        return objectProperty;
    }

    @Override
    public void set(long newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : ": "") + "A bound value cannot be set.");
        }
        if (value != newValue) {
            value = newValue;
            fireValueChangedEvent();
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

        ObservableLongValue newObservable;
        if (rawObservable instanceof ObservableLongValue) {
            newObservable = (ObservableLongValue)rawObservable;
        } else if (rawObservable instanceof ObservableNumberValue numberValue) {
            newObservable = new LongBinding(numberValue::longValue, rawObservable);
        } else {
            newObservable = new LongBinding(() -> {
                Number value = rawObservable.getValue();
                return (value == null)? 0L : value.longValue();
            }, rawObservable);
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
            if (observable instanceof LongBinding longBinding) {
                longBinding.dispose();
            }
            observable = null;
        }
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("LongProperty [");
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
}