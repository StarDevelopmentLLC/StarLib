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

import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.binding.BidirectionalContentBinding;
import com.stardevllc.starlib.observable.binding.ContentBinding;
import com.stardevllc.starlib.observable.collections.map.MapChangeListener;
import com.stardevllc.starlib.observable.collections.map.ObservableMap;
import com.stardevllc.starlib.observable.expression.MapExpression;
import com.stardevllc.starlib.observable.expression.MapExpressionHelper;
import com.stardevllc.starlib.observable.value.ChangeListener;

import java.util.Map;

public class ReadOnlyMapProperty<K, V> extends MapExpression<K, V> implements ReadOnlyProperty<ObservableMap<K, V>> {

    protected final Object bean;
    protected final String name;
    protected ObservableMap<K, V> value;
    protected SizeProperty size0;
    protected EmptyProperty empty0;

    public ReadOnlyMapProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public ReadOnlyMapProperty(ObservableMap<K, V> initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public ReadOnlyMapProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public ReadOnlyMapProperty(Object bean, String name, ObservableMap<K, V> initialValue) {
        this.value = initialValue;
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }
    
    public void bindContentBidirectional(ObservableMap<K, V> map) {
        BidirectionalContentBinding.bind(this, map);
    }
    
    public void unbindContentBidirectional(Object object) {
        BidirectionalContentBinding.unbind(this, object);
    }
    
    public void bindContent(ObservableMap<K, V> map) {
        ContentBinding.bind(this, map);
    }
    
    public void unbindContent(Object object) {
        ContentBinding.unbind(this, object);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map<?, ?> otherMap) || otherMap.size() != size()) {
            return false;
        }

        try {
            for (Entry<K, V> e : entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                if (value == null) {
                    if (otherMap.get(key) != null || !otherMap.containsKey(key)) {
                        return false;
                    }
                } else if (!value.equals(otherMap.get(key))) {
                    return false;
                }
            }

            return true;
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int h = 0;
        for (Entry<K,V> e : entrySet()) {
            h += e.hashCode();
        }
        return h;
    }

    @Override
    public ObservableMap<K, V> get() {
        return value;
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlyMapProperty [");
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
    public void addListener(InvalidationListener listener) {
        helper = MapExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        helper = MapExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(MapChangeListener<? super K, ? super V> listener) {
        helper = MapExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        MapExpressionHelper.fireValueChangedEvent(helper);
    }

    protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change) {
        MapExpressionHelper.fireValueChangedEvent(helper, change);
    }

    protected class SizeProperty extends ReadOnlyIntegerProperty {
        @Override
        public int get() {
            return size();
        }

        @Override
        public Object getBean() {
            return ReadOnlyMapProperty.this;
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
            return ReadOnlyMapProperty.this;
        }

        @Override
        public String getName() {
            return "empty";
        }
    }
}