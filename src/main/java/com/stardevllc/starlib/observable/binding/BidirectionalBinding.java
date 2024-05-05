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

package com.stardevllc.starlib.observable.binding;

import com.stardevllc.starlib.observable.property.writable.*;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableValue;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class BidirectionalBinding<E, T extends Property<E>> implements ChangeListener<E> {

    private WeakReference<T> propertyRef1;
    private WeakReference<T> propertyRef2;
    private E oldValue;
    private boolean updating;
    private final int cachedHashCode;

    protected BidirectionalBinding(Object property1, Object property2) {
        this.cachedHashCode = property1.hashCode() * property2.hashCode();
    }
    
    public BidirectionalBinding(T property1, T property2) {
        this((Object) property1, property2);
        this.propertyRef1 = new WeakReference<>(property1);
        this.propertyRef2 = new WeakReference<>(property2);
        this.oldValue = property1.getValue();
    }

    @Override
    public void changed(ObservableValue<? extends E> sourceProperty, E oldValue, E newValue) {
        if (!updating) {
            final T property1 = propertyRef1.get();
            final T property2 = propertyRef2.get();
            if ((property1 == null) || (property2 == null)) {
                if (property1 != null) {
                    property1.removeListener(this);
                }
                if (property2 != null) {
                    property2.removeListener(this);
                }
            } else {
                try {
                    updating = true;
                    if (property1 == sourceProperty) {
                        property2.setValue(newValue);
                        property2.getValue();
                    } else {
                        property1.setValue(newValue);
                        property1.getValue();
                    }
                    this.oldValue = newValue;
                } catch (RuntimeException e) {
                    try {
                        if (property1 == sourceProperty) {
                            property1.setValue(this.oldValue);
                            property1.getValue();
                        } else {
                            property2.setValue(this.oldValue);
                            property2.getValue();
                        }
                    } catch (Exception e2) {
                        e2.addSuppressed(e);
                        unbind(property1, property2);
                        throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                        + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        property1 + " and " + property2, e2);
                    }
                    throw new RuntimeException(
                            "Bidirectional binding failed, setting to the previous value", e);
                } finally {
                    updating = false;
                }
            }
        }
    }
    
    private static void checkParameters(Object property1, Object property2) {
        Objects.requireNonNull(property1, "Both properties must be specified.");
        Objects.requireNonNull(property2, "Both properties must be specified.");
        if (property1 == property2) {
            throw new IllegalArgumentException("Cannot bind property to itself");
        }
    }

    public static <T> BidirectionalBinding bind(Property<T> property1, Property<T> property2) {
        checkParameters(property1, property2);
        BidirectionalBinding binding = new BidirectionalBinding(property1, property2);
        property1.setValue(property2.getValue());
        property1.getValue();
        property1.addListener(binding);
        property2.addListener(binding);
        return binding;
    }

    public static <T> void unbind(Property<T> property1, Property<T> property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding = new BidirectionalBinding(property1, property2);
        property1.removeListener(binding);
        property2.removeListener(binding);
    }

    public static void unbind(Object property1, Object property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding = new BidirectionalBinding(property1, property2);
        if (property1 instanceof ObservableValue observableValue) {
            observableValue.removeListener(binding);
        }
        if (property2 instanceof ObservableValue observableValue) {
            observableValue.removeListener(binding);
        }
    }

    public static BidirectionalBinding bindNumber(Property<Integer> property1, IntegerProperty property2) {
        return bindNumber(property1, (Property<Number>)property2);
    }

    public static BidirectionalBinding bindNumber(Property<Long> property1, LongProperty property2) {
        return bindNumber(property1, (Property<Number>)property2);
    }

    public static BidirectionalBinding bindNumber(Property<Float> property1, FloatProperty property2) {
        return bindNumber(property1, (Property<Number>)property2);
    }

    public static BidirectionalBinding bindNumber(Property<Double> property1, DoubleProperty property2) {
        return bindNumber(property1, (Property<Number>)property2);
    }

    public static BidirectionalBinding bindNumber(IntegerProperty property1, Property<Integer> property2) {
        return bindNumberObject(property1, property2);
    }

    public static BidirectionalBinding bindNumber(LongProperty property1, Property<Long> property2) {
        return bindNumberObject(property1, property2);
    }

    public static BidirectionalBinding bindNumber(FloatProperty property1, Property<Float> property2) {
        return bindNumberObject(property1, property2);
    }

    public static BidirectionalBinding bindNumber(DoubleProperty property1, Property<Double> property2) {
        return bindNumberObject(property1, property2);
    }

    private static <T extends Number> BidirectionalBinding bindNumberObject(Property<Number> property1, Property<T> property2) {
        checkParameters(property1, property2);

        final BidirectionalBinding binding = new BidirectionalBinding(property2, property1);

        property1.setValue(property2.getValue());
        property1.getValue();
        property1.addListener(binding);
        property2.addListener(binding);
        return binding;
    }

    private static <T extends Number> BidirectionalBinding bindNumber(Property<T> property1, Property<Number> property2) {
        checkParameters(property1, property2);

        final BidirectionalBinding binding = new BidirectionalBinding(property1, property2);

        property1.setValue((T)property2.getValue());
        property1.getValue();
        property1.addListener(binding);
        property2.addListener(binding);
        return binding;
    }

    protected T getProperty1() {
        return propertyRef1.get();
    }

    protected T getProperty2() {
        return propertyRef2.get();
    }

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        final Object propertyA1 = getProperty1();
        final Object propertyA2 = getProperty2();
        if ((propertyA1 == null) || (propertyA2 == null)) {
            return false;
        }

        if (obj instanceof BidirectionalBinding otherBinding) {
            final Object propertyB1 = otherBinding.getProperty1();
            final Object propertyB2 = otherBinding.getProperty2();
            if ((propertyB1 == null) || (propertyB2 == null)) {
                return false;
            }

            if (propertyA1 == propertyB1 && propertyA2 == propertyB2) {
                return true;
            }
            return propertyA1 == propertyB2 && propertyA2 == propertyB1;
        }
        return false;
    }
}