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

import com.stardevllc.starlib.observable.expression.IntegerExpression;

public class ReadOnlyIntegerProperty extends IntegerExpression implements ReadOnlyProperty<Number> {

    protected final Object bean;
    protected final String name;

    protected int value;
    
    public ReadOnlyIntegerProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public ReadOnlyIntegerProperty(int initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public ReadOnlyIntegerProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public ReadOnlyIntegerProperty(Object bean, String name, int initialValue) {
        this.value = initialValue;
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
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
    public int get() {
        return value;
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlyIntegerProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && !name.isEmpty()) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }
    
    public static <T extends Number> ReadOnlyIntegerProperty readOnlyIntegerProperty(final ReadOnlyProperty<T> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }

        return property instanceof ReadOnlyIntegerProperty ? (ReadOnlyIntegerProperty) property: new ReadOnlyIntegerProperty(null, property.getName(), property.getValue().intValue());
    }
    
    @Override
    public ReadOnlyObjectProperty<Integer> asObject() {
        return new ReadOnlyObjectProperty<>(null, getName(), get());
    }

    @Override
    protected void fireValueChangedEvent() {
        super.fireValueChangedEvent();
    }
}