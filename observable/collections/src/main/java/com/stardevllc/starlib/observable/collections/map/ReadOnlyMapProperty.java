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

package com.stardevllc.starlib.observable.collections.map;

import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.collections.ObservableCollections;
import com.stardevllc.starlib.observable.collections.binding.BidirectionalContentBinding;
import com.stardevllc.starlib.observable.collections.binding.ContentBinding;
import com.stardevllc.starlib.observable.property.binding.StringFormatter;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyBooleanProperty;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyObjectProperty;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;
import com.stardevllc.starlib.observable.value.ObservableObjectValue;
import com.stardevllc.starlib.observable.value.ObservableStringValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ReadOnlyMapProperty<K, V> implements ReadOnlyProperty<ObservableMap<K, V>>, ObservableMapValue<K, V> {
    protected MapExpressionHelper<K, V> helper;
    
    protected final Object bean;
    protected final String name;
    protected ObservableMap<K, V> value;

    protected final MapChangeListener<K, V> mapChangeListener = this::fireValueChangedEvent;
    protected ObservableValue<? extends ObservableMap<K, V>> observable = null;

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
    public ObservableMap<K, V> getValue() {
        return get();
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
        value = observable == null ? value : observable.getValue();
        if (value != null) {
            value.addListener(mapChangeListener);
        }
        return value;
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(final ObservableValue<? extends ObservableMap<K, V>> newObservable) {
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

    protected void fireValueChangedEvent() {
        MapExpressionHelper.fireValueChangedEvent(helper);
    }

    protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change) {
        MapExpressionHelper.fireValueChangedEvent(helper, change);
    }

    @Override
    public ObservableValue<Boolean> isEqualTo(ObservableValue<ObservableMap<K, V>> other) {
        return new ReadOnlyBooleanProperty(getValue().equals(other.getValue()));
    }

    @Override
    public ObservableValue<Boolean> isNotEqualTo(ObservableValue<ObservableMap<K, V>> other) {
        return new ReadOnlyBooleanProperty(!getValue().equals(other.getValue()));
    }

    @Override
    public ObservableValue<String> asString(String format) {
        return StringFormatter.format(format, this);
    }

    @Override
    public ObservableValue<ObservableMap<K, V>> orElse(ObservableMap<K, V> constant) {
        return null;
    }

    public int getSize() {
        return size();
    }

    public ObservableObjectValue<V> valueAt(K key) {
        return new ReadOnlyObjectProperty<>(get(key));
    }

    public ObservableObjectValue<V> valueAt(ObservableValue<K> key) {
        return new ReadOnlyObjectProperty<>(get(key.getValue()));
    }

    public ObservableBooleanValue isEqualTo(final ObservableMap<?, ?> other) {
        return new ReadOnlyBooleanProperty(get().equals(other));
    }

    public ObservableBooleanValue isNotEqualTo(final ObservableMap<?, ?> other) {
        return new ReadOnlyBooleanProperty(!get().equals(other));
    }

    public ObservableBooleanValue isNull() {
        return new ReadOnlyBooleanProperty(get() == null);
    }

    public ObservableBooleanValue isNotNull() {
        return new ReadOnlyBooleanProperty(get() != null);
    }

    public ObservableStringValue asString() {
        return StringFormatter.convert(this);
    }

    @Override
    public int size() {
        return getNonNull().size();
    }

    @Override
    public boolean isEmpty() {
        return getNonNull().isEmpty();
    }

    @Override
    public boolean containsKey(Object obj) {
        return getNonNull().containsKey(obj);
    }

    @Override
    public boolean containsValue(Object obj) {
        return getNonNull().containsValue(obj);
    }

    @Override
    public V put(K key, V value) {
        return getNonNull().put(key, value);
    }

    @Override
    public V remove(Object obj) {
        return getNonNull().remove(obj);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> elements) {
        getNonNull().putAll(elements);
    }

    @Override
    public void clear() {
        getNonNull().clear();
    }

    @Override
    public Set<K> keySet() {
        return getNonNull().keySet();
    }

    @Override
    public Collection<V> values() {
        return getNonNull().values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return getNonNull().entrySet();
    }

    @Override
    public V get(Object key) {
        return getNonNull().get(key);
    }

    private ObservableMap<K, V> getNonNull() {
        ObservableMap<K, V> map = get();

        return map == null ? ObservableCollections.emptyObservableMap() : map;
    }

    @Override
    public void addListener(MapChangeListener<? super K, ? super V> listener) {
        helper = MapExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }
}