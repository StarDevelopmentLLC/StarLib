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
import com.stardevllc.starlib.observable.collections.list.ListChangeListener;
import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.value.ObservableValue;
import com.stardevllc.starlib.observable.writable.WritableListValue;

public class ListProperty<E> extends ReadOnlyListProperty<E> implements Property<ObservableList<E>>, WritableListValue<E> {

    private final ListChangeListener<E> listChangeListener = this::fireValueChangedEvent;

    protected ObservableValue<? extends ObservableList<E>> observable = null;
    protected boolean valid = true;
    
    public ListProperty() {
        super();
    }

    public ListProperty(ObservableList<E> initialValue) {
        super(initialValue);
    }

    public ListProperty(Object bean, String name) {
        super(bean, name);
    }

    public ListProperty(Object bean, String name, ObservableList<E> initialValue) {
        super(bean, name, initialValue);
        if (initialValue != null) {
            initialValue.addListener(listChangeListener);
        }
    }

    @Override
    public void setValue(ObservableList<E> v) {
        set(v);
    }
    
    @Override
    public void bindBidirectional(Property<ObservableList<E>> other) {
        BidirectionalBinding.bind(this, other);
    }
    
    @Override
    public void unbindBidirectional(Property<ObservableList<E>> other) {
        BidirectionalBinding.unbind(this, other);
    }

    @Override
    public ObservableList<E> get() {
        if (!valid) {
            value = observable == null ? value : observable.getValue();
            valid = true;
            if (value != null) {
                value.addListener(listChangeListener);
            }
        }
        return value;
    }

    @Override
    public void set(ObservableList<E> newValue) {
        if (isBound()) {
            throw new java.lang.RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : " : "") + "A bound value cannot be set.");
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
    public void bind(final ObservableValue<? extends ObservableList<E>> newObservable) {
        if (newObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        if (newObservable != observable) {
            unbind();
            observable = newObservable;
        }
    }

    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.getValue();
            observable = null;
        }
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("ListProperty [");
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