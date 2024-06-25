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

package com.stardevllc.starlib.observable.collections.set;

import com.stardevllc.starlib.observable.ChangeListener;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.collections.ObservableCollections;
import com.stardevllc.starlib.observable.collections.binding.BidirectionalContentBinding;
import com.stardevllc.starlib.observable.collections.binding.ContentBinding;
import com.stardevllc.starlib.observable.property.binding.StringFormatter;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyBooleanProperty;
import com.stardevllc.starlib.observable.ReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;
import com.stardevllc.starlib.observable.value.ObservableStringValue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class ReadOnlySetProperty<E> implements ReadOnlyProperty<ObservableSet<E>>, ObservableSetValue<E> {
    protected SetExpressionHelper<E> helper;
    
    protected final Object bean;
    protected final String name;
    protected ObservableSet<E> value;

    protected final SetChangeListener<E> setChangeListener = this::fireValueChangedEvent;
    protected ObservableValue<? extends ObservableSet<E>> observable = null;

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
    public void addListener(ChangeListener<? super ObservableSet<E>> listener) {
        helper = SetExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableSet<E>> listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
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
    public ObservableSet<E> getValue() {
        return get();
    }

    @Override
    public ObservableValue<Boolean> isEqualTo(ObservableValue<ObservableSet<E>> other) {
        return new ReadOnlyBooleanProperty(Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Boolean> isNotEqualTo(ObservableValue<ObservableSet<E>> other) {
        return new ReadOnlyBooleanProperty(!Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<ObservableSet<E>> orElse(ObservableSet<E> constant) {
        return null;
    }

    public int getSize() {
        return size();
    }

    public ObservableBooleanValue isEqualTo(final ObservableSet<?> other) {
        return new ReadOnlyBooleanProperty(get().equals(other));
    }

    public ObservableBooleanValue isNotEqualTo(final ObservableSet<?> other) {
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
    public boolean contains(Object obj) {
        return getNonNull().contains(obj);
    }

    @Override
    public Iterator<E> iterator() {
        return getNonNull().iterator();
    }

    @Override
    public Object[] toArray() {
        return getNonNull().toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return getNonNull().toArray(array);
    }

    @Override
    public boolean add(E element) {
        return getNonNull().add(element);
    }

    @Override
    public boolean remove(Object obj) {
        return getNonNull().remove(obj);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return getNonNull().containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        return getNonNull().addAll(elements);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        return getNonNull().removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        return getNonNull().retainAll(objects);
    }

    @Override
    public void clear() {
        getNonNull().clear();
    }

    private ObservableSet<E> getNonNull() {
        ObservableSet<E> set = get();
        return set == null ? ObservableCollections.emptyObservableSet() : set;
    }

    @Override
    public void addListener(SetChangeListener<? super E> listener) {
        helper = SetExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(SetChangeListener<? super E> listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        SetExpressionHelper.fireValueChangedEvent(helper);
    }

    protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> change) {
        SetExpressionHelper.fireValueChangedEvent(helper, change);
    }

    @Override
    public ObservableSet<E> get() {
        value = observable == null ? value : observable.getValue();
        if (value != null) {
            value.addListener(setChangeListener);
        }
        return value;
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(final ObservableValue<? extends ObservableSet<E>> newObservable) {
        if (newObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        if (newObservable != this.observable) {
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
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }
}