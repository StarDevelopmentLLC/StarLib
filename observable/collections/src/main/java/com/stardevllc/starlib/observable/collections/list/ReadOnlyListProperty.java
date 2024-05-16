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

package com.stardevllc.starlib.observable.collections.list;

import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.collections.ObservableCollections;
import com.stardevllc.starlib.observable.collections.binding.BidirectionalContentBinding;
import com.stardevllc.starlib.observable.collections.binding.ContentBinding;
import com.stardevllc.starlib.observable.property.binding.StringFormatter;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyBooleanProperty;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyObjectProperty;
import com.stardevllc.starlib.observable.ReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;
import com.stardevllc.starlib.observable.value.ObservableIntegerValue;
import com.stardevllc.starlib.observable.value.ObservableObjectValue;
import com.stardevllc.starlib.observable.value.ObservableStringValue;

import java.util.*;

public class ReadOnlyListProperty<E> implements ReadOnlyProperty<ObservableList<E>>, ObservableListValue<E> {
    protected ListExpressionHelper<E> helper = null;
    
    protected final ListChangeListener<E> listChangeListener = this::fireValueChangedEvent;
    
    protected final Object bean;
    protected final String name;

    protected ObservableList<E> value;

    protected ObservableValue<? extends ObservableList<E>> observable = null;

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
    public ObservableValue<Boolean> isEqualTo(ObservableValue<ObservableList<E>> other) {
        return new ReadOnlyBooleanProperty(getValue().equals(other.getValue()));
    }

    @Override
    public ObservableValue<Boolean> isNotEqualTo(ObservableValue<ObservableList<E>> other) {
        return new ReadOnlyBooleanProperty(!getValue().equals(other.getValue()));
    }

    @Override
    public ObservableValue<String> asString(String format) {
        return StringFormatter.format(format, this);
    }

    @Override
    public ObservableValue<ObservableList<E>> orElse(ObservableList<E> constant) {
        return getValue() == null || getValue().isEmpty() ? new ReadOnlyListProperty<>(constant) : this;
    }

    @Override
    public ObservableList<E> getValue() {
        return get();
    }

    public int getSize() {
        return size();
    }

    public ObservableObjectValue<E> valueAt(int index) {
        return new ReadOnlyObjectProperty<>(get(index));
    }

    public ObservableObjectValue<E> valueAt(ObservableIntegerValue index) {
        return new ReadOnlyObjectProperty<>(get(index.get()));
    }

    public ObservableBooleanValue isEqualTo(final ObservableList<?> other) {
        return new ReadOnlyBooleanProperty(get().equals(other));
    }

    public ObservableBooleanValue isNotEqualTo(final ObservableList<?> other) {
        return new ReadOnlyBooleanProperty(get() != null ? other != null : !get().equals(other));
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
        return new HashSet<>(getNonNull()).containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        return getNonNull().addAll(elements);
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> elements) {
        return getNonNull().addAll(i, elements);
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

    @Override
    public E get(int i) {
        return getNonNull().get(i);
    }

    @Override
    public E set(int i, E element) {
        return getNonNull().set(i, element);
    }

    @Override
    public void add(int i, E element) {
        getNonNull().add(i, element);
    }

    @Override
    public E remove(int i) {
        return getNonNull().remove(i);
    }

    @Override
    public int indexOf(Object obj) {
        return getNonNull().indexOf(obj);
    }

    @Override
    public int lastIndexOf(Object obj) {
        return getNonNull().lastIndexOf(obj);
    }

    @Override
    public ListIterator<E> listIterator() {
        return getNonNull().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return getNonNull().listIterator(i);
    }

    @Override
    public List<E> subList(int from, int to) {
        return getNonNull().subList(from, to);
    }

    @Override
    public boolean addAll(E... elements) {
        return getNonNull().addAll(elements);
    }

    @Override
    public boolean setAll(E... elements) {
        return getNonNull().setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends E> elements) {
        return getNonNull().setAll(elements);
    }

    @Override
    public boolean removeAll(E... elements) {
        return getNonNull().removeAll(elements);
    }

    @Override
    public boolean retainAll(E... elements) {
        return getNonNull().retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        getNonNull().remove(from, to);
    }

    private ObservableList<E> getNonNull() {
        ObservableList<E> list = get();
        return list == null ? ObservableCollections.emptyObservableList() : list;
    }

    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        helper = ListExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        helper = ListExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        ListExpressionHelper.fireValueChangedEvent(helper);
    }

    protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> change) {
        ListExpressionHelper.fireValueChangedEvent(helper, change);
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
        value = observable == null ? value : observable.getValue();
        if (value != null) {
            value.addListener(listChangeListener);
        }
        return value;
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(final ObservableValue<? extends ObservableList<E>> newObservable) {
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
}