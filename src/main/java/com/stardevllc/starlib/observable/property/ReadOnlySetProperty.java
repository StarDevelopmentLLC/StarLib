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
import com.stardevllc.starlib.observable.collections.set.ObservableSet;
import com.stardevllc.starlib.observable.collections.set.SetChangeListener;
import com.stardevllc.starlib.observable.expression.SetExpression;

import java.util.Set;

public class ReadOnlySetProperty<E> extends SetExpression<E> implements ReadOnlyProperty<ObservableSet<E>> {

    protected final Object bean;
    protected final String name;
    protected ObservableSet<E> value;
    protected SizeProperty size0;
    protected EmptyProperty empty0;

    public ReadOnlySetProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public ReadOnlySetProperty(ObservableSet<E> initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public ReadOnlySetProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public ReadOnlySetProperty(Object bean, String name, ObservableSet<E> initialValue) {
        this.value = initialValue;
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }
    
    public void bindContentBidirectional(ObservableSet<E> set) {
        BidirectionalContentBinding.bind(this, set);
    }
    
    public void unbindContentBidirectional(Object object) {
        BidirectionalContentBinding.unbind(this, object);
    }
    
    public void bindContent(ObservableSet<E> set) {
        ContentBinding.bind(this, set);
    }
    
    public void unbindContent(Object object) {
        ContentBinding.unbind(this, object);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Set<?> otherSet) || otherSet.size() != size()) {
            return false;
        }

        try {
            return containsAll(otherSet);
        } catch (ClassCastException | NullPointerException unused)   {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int h = 0;
        for (E e : this) {
            if (e != null)
                h += e.hashCode();
        }
        return h;
    }
    
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlySetProperty [");
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
    protected void fireValueChangedEvent() {
        super.fireValueChangedEvent();
    }

    @Override
    protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> change) {
        super.fireValueChangedEvent(change);
    }

    @Override
    public ReadOnlyIntegerProperty sizeProperty() {
        if (size0 == null) {
            size0 = new SizeProperty();
        }
        return size0;
    }

    @Override
    public ReadOnlyBooleanProperty emptyProperty() {
        if (empty0 == null) {
            empty0 = new EmptyProperty();
        }
        return empty0;
    }

    @Override
    public ObservableSet<E> get() {
        return value;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    protected class SizeProperty extends ReadOnlyIntegerProperty {
        @Override
        public int get() {
            return size();
        }

        @Override
        public Object getBean() {
            return ReadOnlySetProperty.this;
        }

        @Override
        public String getName() {
            return "size";
        }
    }

    protected class EmptyProperty extends ReadOnlyBooleanProperty {

        @Override
        public boolean get() {
            return isEmpty();
        }

        @Override
        public Object getBean() {
            return ReadOnlySetProperty.this;
        }

        @Override
        public String getName() {
            return "empty";
        }
    }
}