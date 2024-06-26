/*
 * Copyright (c) 2011, 2022, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.ChangeListener;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.ReadOnlyProperty;
import com.stardevllc.starlib.observable.property.binding.ChangeHelper;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;
import com.stardevllc.starlib.observable.value.ObservableStringValue;

import java.util.Objects;

public class ReadOnlyBooleanProperty implements ReadOnlyProperty<Boolean>, ObservableBooleanValue{
    protected ChangeHelper<Boolean> helper;
    
    protected boolean value;
    protected ObservableBooleanValue observable = null;

    protected final Object bean;
    protected final String name;

    public ReadOnlyBooleanProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public ReadOnlyBooleanProperty(boolean initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public ReadOnlyBooleanProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public ReadOnlyBooleanProperty(Object bean, String name, boolean initialValue) {
        this.value = initialValue;
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    protected void fireValueChangedEvent() {
        ChangeHelper.fireValueChangedEvent(helper);
    }


    @Override
    public void addListener(ChangeListener<? super Boolean> listener) {
        helper = ChangeHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {
        helper = ChangeHelper.removeListener(helper, listener);
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean get() {
        return observable == null ? value : observable.get();
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

        ObservableBooleanValue newObservable = (rawObservable instanceof ObservableBooleanValue obv) ? obv : new ReadOnlyBooleanProperty(rawObservable.getValue());

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
    public ObservableBooleanValue isNull() {
        return new ReadOnlyBooleanProperty(getValue() == null);
    }

    @Override
    public ObservableBooleanValue isNotNull() {
        return new ReadOnlyBooleanProperty(getValue() != null);
    }

    @Override
    public ObservableBooleanValue isEqualTo(ObservableValue<Boolean> other) {
        return new ReadOnlyBooleanProperty(Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(ObservableValue<Boolean> other) {
        return new ReadOnlyBooleanProperty(!Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Boolean> orElse(Boolean constant) {
        return this;
    }

    @Override
    public Boolean getValue() {
        return get();
    }

    @Override
    public ObservableBooleanValue and(final ObservableBooleanValue other) {
        return new ReadOnlyBooleanProperty(get() && other.get());
    }

    @Override
    public ObservableBooleanValue or(final ObservableBooleanValue other) {
        return new ReadOnlyBooleanProperty(get() || other.get());
    }

    @Override
    public ObservableBooleanValue not() {
        return new ReadOnlyBooleanProperty(!get());
    }

    @Override
    public ObservableStringValue asString() {
        return null;
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlyBooleanProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && !name.isEmpty()) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }
}