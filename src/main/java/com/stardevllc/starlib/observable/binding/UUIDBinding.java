/*
 * Copyright (c) 2010, 2023, Oracle and/or its affiliates. All rights reserved.
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

import com.stardevllc.starlib.observable.collections.StarCollections;
import com.stardevllc.starlib.observable.expression.ExpressionHelper;
import com.stardevllc.starlib.observable.expression.UUIDExpression;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.list.ObservableList;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Callable;

public class UUIDBinding extends UUIDExpression implements Binding<UUID> {

    private UUID value;
    private boolean valid = false;
    
    private BindingHelperObserver observer;

    private Callable<UUID> callable;
    private ObservableList<Observable> dependencies;

    public UUIDBinding(Observable... dependencies) {
        if (dependencies == null) {
            this.dependencies = StarCollections.emptyObservableList();
        } else {
            this.dependencies = StarCollections.observableList(Arrays.asList(dependencies));
        }
        bind(dependencies);
    }

    public UUIDBinding(Callable<UUID> callable, Observable... dependencies) {
        this(dependencies);
        this.callable = callable;
    }

    public UUIDBinding(UUID value, Observable... dependencies) {
        this(dependencies);
        this.value = value;
    }
    
    protected final void bind(Observable... dependencies) {
        if ((dependencies != null) && (dependencies.length > 0)) {
            if (observer == null) {
                observer = new BindingHelperObserver(this);
            }
            for (final Observable dep : dependencies) {
                dep.addListener(observer);
            }
        }
    }
    
    protected final void unbind(Observable... dependencies) {
        if (observer != null) {
            for (final Observable dep : dependencies) {
                dep.removeListener(observer);
            }
        }
    }
    
    @Override
    public void dispose() {
        if (!dependencies.isEmpty()) {
            unbind(dependencies);
        }
    }
    
    @Override
    public ObservableList<?> getDependencies() {
        return StarCollections.emptyObservableList();
    }
    
    @Override
    public final UUID get() {
        if (!valid) {
            value = computeValue();
            valid = true;
        }
        return value;
    }
    
    protected void onInvalidating() {
        //no-op
    }

    @Override
    public final void invalidate() {
        if (valid) {
            valid = false;
            onInvalidating();
            ExpressionHelper.fireValueChangedEvent(helper);
        }
    }

    @Override
    public final boolean isValid() {
        return valid;
    }
    
    protected UUID computeValue(){
        if (!valid) {
            return null;
        }

        if (callable != null){
            try {
                return callable.call();
            } catch (Exception e) {
                return null;
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return valid ? "UUIDBinding [value: " + get() + "]"
                : "UUIDBinding [invalid]";
    }
}