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
import com.stardevllc.starlib.observable.ReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableUUIDValue;

import java.util.Objects;
import java.util.UUID;

public class ReadOnlyUUIDProperty implements ReadOnlyProperty<UUID>, ObservableUUIDValue {
    protected final Object bean;
    protected final String name;
    protected UUID value;
    protected ObservableUUIDValue observable = null;

    public ReadOnlyUUIDProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public ReadOnlyUUIDProperty(UUID initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public ReadOnlyUUIDProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public ReadOnlyUUIDProperty(Object bean, String name, UUID initialValue) {
        this.value = initialValue;
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    @Override
    public UUID get() {
        return observable == null ? value : observable.getValue();
    }

    @Override
    public UUID getValue() {
        return get();
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
            observable = (ObservableUUIDValue) newObservable;
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
    public ObservableValue<Boolean> isNull() {
        return new ReadOnlyBooleanProperty(getValue() == null);
    }

    @Override
    public ObservableValue<Boolean> isNotNull() {
        return new ReadOnlyBooleanProperty(getValue() != null);
    }

    @Override
    public ObservableValue<Boolean> isEqualTo(ObservableValue<UUID> other) {
        return new ReadOnlyBooleanProperty(Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Boolean> isNotEqualTo(ObservableValue<UUID> other) {
        return new ReadOnlyBooleanProperty(!Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<String> asString() {
        return new ReadOnlyStringProperty(getValue().toString());
    }

    @Override
    public ObservableValue<UUID> orElse(UUID constant) {
        return new ReadOnlyUUIDProperty(getValue() == null ? constant : getValue());
    }
}