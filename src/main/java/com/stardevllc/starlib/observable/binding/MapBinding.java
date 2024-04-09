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

package com.stardevllc.starlib.observable.binding;

import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.StarCollections;
import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.collections.map.MapChangeListener;
import com.stardevllc.starlib.observable.collections.map.ObservableMap;
import com.stardevllc.starlib.observable.expression.MapExpression;
import com.stardevllc.starlib.observable.expression.MapExpressionHelper;
import com.stardevllc.starlib.observable.property.ReadOnlyBooleanProperty;
import com.stardevllc.starlib.observable.value.ChangeListener;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class MapBinding<K, V> extends MapExpression<K, V> implements Binding<ObservableMap<K, V>> {

    private ObservableMap<K, V> value;
    private boolean valid = false;
    private BindingHelperObserver observer;
    private MapExpressionHelper<K, V> helper = null;
    private Callable<ObservableMap<K, V>> callable;
    private ObservableList<Observable> dependencies;

    private final MapChangeListener<K, V> mapChangeListener = change -> {
        onInvalidating();
        MapExpressionHelper.fireValueChangedEvent(helper, change);
    };

    public MapBinding(Observable... dependencies) {
        if (dependencies == null) {
            this.dependencies = StarCollections.emptyObservableList();
        } else {
            this.dependencies = StarCollections.observableList(Arrays.asList(dependencies));
        }
        bind(dependencies);
    }

    public MapBinding(Callable<ObservableMap<K, V>> callable, Observable... dependencies) {
        this(dependencies);
        this.callable = callable;
    }

    public MapBinding(ObservableMap<K, V> value, Observable... dependencies) {
        this(dependencies);
        this.value = value;
    }

    private class EmptyProperty extends ReadOnlyBooleanProperty {

        @Override
        public boolean get() {
            return isEmpty();
        }

        @Override
        public Object getBean() {
            return MapBinding.this;
        }

        @Override
        public String getName() {
            return "empty";
        }

        @Override
        protected void fireValueChangedEvent() {
            super.fireValueChangedEvent();
        }
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

    protected final void bind(Observable... dependencies) {
        if ((dependencies != null) && (dependencies.length > 0)) {
            if (observer == null) {
                observer = new BindingHelperObserver(this);
            }
            for (final Observable dep : dependencies) {
                if (dep != null) {
                    dep.addListener(observer);
                }
            }
        }
    }

    protected final void unbind(Observable... dependencies) {
        if (observer != null) {
            for (final Observable dep : dependencies) {
                if (dep != null) {
                    dep.removeListener(observer);
                }
            }
            observer = null;
        }
    }

    @Override
    public void dispose() {
        if (!dependencies.isEmpty()) {
            unbind(dependencies);
        }
    }

    protected ObservableMap<K, V> computeValue() {
        if (!valid) {
            return StarCollections.emptyObservableMap();
        }

        if (callable != null){
            try {
                return callable.call();
            } catch (Exception e) {
                return StarCollections.emptyObservableMap();
            }
        }
        return value;
    }

    @Override
    public ObservableList<?> getDependencies() {
        return StarCollections.emptyObservableList();
    }

    @Override
    public final ObservableMap<K, V> get() {
        if (!valid) {
            value = computeValue();
            valid = true;
            if (value != null) {
                value.addListener(mapChangeListener);
            }
        }
        return value;
    }

    protected void onInvalidating() {
        //no-op
    }

    @Override
    public final void invalidate() {
        if (valid) {
            if (value != null) {
                value.removeListener(mapChangeListener);
            }
            valid = false;
            onInvalidating();
            MapExpressionHelper.fireValueChangedEvent(helper);
        }
    }

    @Override
    public final boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return valid ? "MapBinding [value: " + get() + "]"
                : "MapBinding [invalid]";
    }
}