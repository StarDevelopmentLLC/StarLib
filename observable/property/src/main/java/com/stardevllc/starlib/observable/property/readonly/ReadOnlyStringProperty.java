/*
 * Copyright (c) 2011, 2017, Oracle and/or its affiliates. All rights reserved.
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

import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.constants.StringConstant;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;
import com.stardevllc.starlib.observable.value.ObservableIntegerValue;
import com.stardevllc.starlib.observable.value.ObservableStringValue;

import java.util.Objects;

import static com.stardevllc.starlib.helper.StringHelper.getStringSafe;

public class ReadOnlyStringProperty implements ReadOnlyProperty<String>, ObservableStringValue {
    protected final Object bean;
    protected final String name;
    protected String value;

    protected ObservableValue<? extends String> observable = null;

    public ReadOnlyStringProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public ReadOnlyStringProperty(String initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public ReadOnlyStringProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public ReadOnlyStringProperty(Object bean, String name, String initialValue) {
        this.value = initialValue;
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    @Override
    public String get() {
        return observable == null ? value : observable.getValue();
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(ObservableValue<? extends String> newObservable) {
        if (newObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }
        if (!newObservable.equals(observable)) {
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
        final StringBuilder result = new StringBuilder(
                "ReadOnlyStringProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && !name.isEmpty()) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
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
    public String getValue() {
        return get();
    }

    @Override
    public ObservableValue<Boolean> isEqualTo(ObservableValue<String> other) {
        return new ReadOnlyBooleanProperty(Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Boolean> isNotEqualTo(ObservableValue<String> other) {
        return new ReadOnlyBooleanProperty(!Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<String> asString() {
        return new ReadOnlyStringProperty(getValue());
    }

    @Override
    public ObservableValue<String> asString(String format) {
        return null;
    }

    @Override
    public ObservableValue<String> orElse(String constant) {
        return new ReadOnlyStringProperty(getValue() == null ? constant : getValueSafe());
    }

    public final String getValueSafe() {
        return getStringSafe(get());
    }

    public ObservableStringValue concat(Object other) {
        return null;
    }

    public ObservableBooleanValue isEqualTo(ObservableStringValue other) {
        return new ReadOnlyBooleanProperty(getStringSafe(get()).equals(getStringSafe(get())));
    }

    public ObservableBooleanValue isEqualTo(final String other) {
        return isEqualTo(StringConstant.valueOf(other));
    }

    public ObservableBooleanValue isNotEqualTo(final ObservableStringValue other) {
        return new ReadOnlyBooleanProperty(!getStringSafe(get()).equals(getStringSafe(get())));
    }

    public ObservableBooleanValue isNotEqualTo(final String other) {
        return isNotEqualTo(StringConstant.valueOf(other));
    }

    public ObservableBooleanValue isEqualToIgnoreCase(final ObservableStringValue other) {
        return new ReadOnlyBooleanProperty(getStringSafe(get()).equalsIgnoreCase(getStringSafe(get())));
    }

    public ObservableBooleanValue isEqualToIgnoreCase(final String other) {
        return isEqualToIgnoreCase(StringConstant.valueOf(other));
    }

    public ObservableBooleanValue isNotEqualToIgnoreCase(final ObservableStringValue other) {
        return new ReadOnlyBooleanProperty(!getStringSafe(get()).equalsIgnoreCase(getStringSafe(get())));
    }

    public ObservableBooleanValue isNotEqualToIgnoreCase(final String other) {
        return isNotEqualToIgnoreCase(StringConstant.valueOf(other));
    }

    public ObservableBooleanValue isNull() {
        return new ReadOnlyBooleanProperty(get() == null);
    }

    public ObservableBooleanValue isNotNull() {
        return new ReadOnlyBooleanProperty(get() != null);
    }

    public ObservableIntegerValue length() {
        return new ReadOnlyIntegerProperty(getValueSafe().length());
    }

    public ObservableBooleanValue isEmpty() {
        return new ReadOnlyBooleanProperty(getValueSafe().isEmpty());
    }

    public ObservableBooleanValue isNotEmpty() {
        return new ReadOnlyBooleanProperty(!getValueSafe().isEmpty());
    }
}