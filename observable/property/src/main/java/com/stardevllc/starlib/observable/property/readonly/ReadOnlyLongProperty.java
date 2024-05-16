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

import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.ReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableLongValue;
import com.stardevllc.starlib.observable.value.ObservableNumberValue;

public class ReadOnlyLongProperty extends ReadOnlyNumberProperty implements ObservableLongValue {

    protected long value;
    protected ObservableLongValue observable = null;

    public ReadOnlyLongProperty() {
        this(ReadOnlyProperty.DEFAULT_BEAN, ReadOnlyProperty.DEFAULT_NAME);
    }

    public ReadOnlyLongProperty(long initialValue) {
        this(ReadOnlyProperty.DEFAULT_BEAN, ReadOnlyProperty.DEFAULT_NAME, initialValue);
    }

    public ReadOnlyLongProperty(Object bean, String name) {
        super(bean, name);
    }

    public ReadOnlyLongProperty(Object bean, String name, long initialValue) {
        super(bean, name);
        this.value = initialValue;
    }

    @Override
    public long get() {
        return observable == null ? value : observable.get();
    }

    @Override
    public Long getValue() {
        return get();
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
            newObservable = new ReadOnlyLongProperty(numberValue.longValue());
        } else {
            newObservable = new ReadOnlyLongProperty((rawObservable.getValue() == null)? 0L : rawObservable.getValue().longValue());
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
        final StringBuilder result = new StringBuilder("ReadOnlyLongProperty [");
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