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

package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.expression.LongExpression;

public class ReadOnlyLongProperty extends LongExpression implements ReadOnlyProperty<Number> {

    protected final Object bean;
    protected final String name;
    protected long value;

    public ReadOnlyLongProperty() {
        this(ReadOnlyProperty.DEFAULT_BEAN, ReadOnlyProperty.DEFAULT_NAME);
    }

    public ReadOnlyLongProperty(long initialValue) {
        this(ReadOnlyProperty.DEFAULT_BEAN, ReadOnlyProperty.DEFAULT_NAME, initialValue);
    }

    public ReadOnlyLongProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? ReadOnlyProperty.DEFAULT_NAME : name;
    }

    public ReadOnlyLongProperty(Object bean, String name, long initialValue) {
        this.value = initialValue;
        this.bean = bean;
        this.name = (name == null) ? ReadOnlyProperty.DEFAULT_NAME : name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public long get() {
        return value;
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
    
    public static <T extends Number> ReadOnlyLongProperty readOnlyLongProperty(final ReadOnlyProperty<T> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }

        return property instanceof ReadOnlyLongProperty ? (ReadOnlyLongProperty) property: new ReadOnlyLongProperty(null, property.getName(), property.getValue().longValue());
    }
    
    @Override
    public ReadOnlyObjectProperty<Long> asObject() {
        return new ReadOnlyObjectProperty<>(null, getName(), getValue());
    }

    @Override
    protected void fireValueChangedEvent() {
        super.fireValueChangedEvent();
    }
}