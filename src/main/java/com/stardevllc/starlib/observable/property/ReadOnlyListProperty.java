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

import com.stardevllc.starlib.observable.binding.BidirectionalContentBinding;
import com.stardevllc.starlib.observable.binding.ContentBinding;
import com.stardevllc.starlib.observable.collections.list.ListChangeListener;
import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.expression.ListExpression;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ReadOnlyListProperty<E> extends ListExpression<E> implements ReadOnlyProperty<ObservableList<E>> {

    protected final Object bean;
    protected final String name;

    protected ObservableList<E> value;

    public ReadOnlyListProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public ReadOnlyListProperty(ObservableList<E> initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public ReadOnlyListProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public ReadOnlyListProperty(Object bean, String name, ObservableList<E> initialValue) {
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
    protected void fireValueChangedEvent() {
        super.fireValueChangedEvent();
    }

    @Override
    protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> change) {
        super.fireValueChangedEvent(change);
    }

    public void bindContentBidirectional(ObservableList<E> list) {
        BidirectionalContentBinding.bind(this, list);
    }

    public void unbindContentBidirectional(Object object) {
        BidirectionalContentBinding.unbind(this, object);
    }

    public void bindContent(ObservableList<E> list) {
        ContentBinding.bind(this, list);
    }

    public void unbindContent(Object object) {
        ContentBinding.unbind(this, object);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof List<?> otherList) || size() != otherList.size()) {
            return false;
        }

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = otherList.listIterator();

        while (e1.hasNext() && e2.hasNext()) {
            if (!Objects.equals(e1.next(), e2.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlyListProperty [");
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
    public ObservableList<E> get() {
        return value;
    }
}