/*
 * Copyright (c) 2011, 2023, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.expression;

import com.stardevllc.starlib.observable.collections.StarCollections;
import com.stardevllc.starlib.observable.property.ReadOnlyBooleanProperty;
import com.stardevllc.starlib.observable.property.ReadOnlyIntegerProperty;
import com.stardevllc.starlib.observable.binding.*;
import com.stardevllc.starlib.observable.collections.map.ObservableMap;
import com.stardevllc.starlib.observable.value.ObservableMapValue;
import com.stardevllc.starlib.observable.value.ObservableValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public abstract class MapExpression<K, V> implements ObservableMapValue<K, V> {
    protected MapExpressionHelper<K, V> helper;

    @Override
    public ObservableMap<K, V> getValue() {
        return get();
    }

    public MapExpression() {
    }

    public int getSize() {
        return size();
    }

    public abstract ReadOnlyIntegerProperty sizeProperty();

    public abstract ReadOnlyBooleanProperty emptyProperty();

    public ObjectBinding<V> valueAt(K key) {
        return new ObjectBinding<>(() -> get(key), this);
    }

    public ObjectBinding<V> valueAt(ObservableValue<K> key) {
        return new ObjectBinding<>(() -> get(key.getValue()), this, key);
    }

    public BooleanBinding isEqualTo(final ObservableMap<?, ?> other) {
        return Bindings.equal(this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableMap<?, ?> other) {
        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isNull() {
        return Bindings.isNull(this);
    }

    public BooleanBinding isNotNull() {
        return Bindings.isNotNull(this);
    }

    public StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
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

        return map == null ? StarCollections.emptyObservableMap() : map;
    }

    @Override
    public <U> ObservableValue<U> map(Function<? super ObservableMap<K, V>, ? extends U> function) {
        return new MappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<ObservableMap<K, V>> orElse(ObservableMap<K, V> kvObservableMap) {
        return new OrElseBinding<>(this, kvObservableMap);
    }

    @Override
    public <U> ObservableValue<U> flatMap(Function<? super ObservableMap<K, V>, ? extends ObservableValue<? extends U>> function) {
        return new FlatMappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<ObservableMap<K, V>> when(ObservableValue<Boolean> observableValue) {
        return new ConditionalBinding<>(this, observableValue);
    }
}