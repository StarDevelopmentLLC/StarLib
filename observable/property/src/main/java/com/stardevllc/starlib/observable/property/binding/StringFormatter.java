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

package com.stardevllc.starlib.observable.property.binding;

import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.constants.StringConstant;
import com.stardevllc.starlib.observable.property.readonly.ReadOnlyStringProperty;
import com.stardevllc.starlib.observable.value.ObservableStringValue;

import java.util.ArrayList;
import java.util.List;

public abstract class StringFormatter extends ReadOnlyStringProperty {

    public StringFormatter() {
        super(null);
    }

    private static Object extractValue(Object obj) {
        return obj instanceof ObservableValue ? ((ObservableValue<?>)obj).getValue() : obj;
    }

    private static Object[] extractValues(Object[] objs) {
        final int n = objs.length;
        final Object[] values = new Object[n];
        for (int i = 0; i < n; i++) {
            values[i] = extractValue(objs[i]);
        }
        return values;
    }

    private static ObservableValue<?>[] extractDependencies(Object... args) {
        final List<ObservableValue<?>> dependencies = new ArrayList<>();
        for (final Object obj : args) {
            if (obj instanceof ObservableValue) {
                dependencies.add((ObservableValue<?>) obj);
            }
        }
        return dependencies.toArray(new ObservableValue[0]);
    }

    public static ObservableStringValue convert(final ObservableValue<?> observableValue) {
        if (observableValue == null) {
            throw new NullPointerException("ObservableValue must be specified");
        }
        if (observableValue instanceof ObservableStringValue) {
            return (ObservableStringValue) observableValue;
        } else {
            return new ReadOnlyStringProperty((observableValue.getValue() == null) ? "null" : observableValue.getValue().toString());
        }
    }

    public static ObservableStringValue concat(final Object... args) {
        if ((args == null) || (args.length == 0)) {
            return StringConstant.valueOf("");
        }
        if (args.length == 1) {
            final Object cur = args[0];
            return cur instanceof ObservableValue ? convert((ObservableValue<?>) cur) : StringConstant.valueOf(cur.toString());
        }
        if (extractDependencies(args).length == 0) {
            final StringBuilder builder = new StringBuilder();
            for (final Object obj : args) {
                builder.append(obj);
            }
            return StringConstant.valueOf(builder.toString());
        }

        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            builder.append(extractValue(arg));
        }
        
        return new ReadOnlyStringProperty(builder.toString());
    }

    public static ObservableStringValue format(final String format, final Object... args) {
        if (format == null) {
            throw new NullPointerException("Format cannot be null.");
        }
        if (extractDependencies(args).length == 0) {
            return StringConstant.valueOf(String.format(format, args));
        }

        ReadOnlyStringProperty stringBinding = new ReadOnlyStringProperty(String.format(format, extractValues(args)));
        stringBinding.get();
        return stringBinding;
    }
}