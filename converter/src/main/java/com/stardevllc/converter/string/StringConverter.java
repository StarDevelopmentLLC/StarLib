/*
 * Copyright (c) 2010, 2024, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.converter.string;

import com.stardevllc.converter.Converter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class StringConverter<T> implements Converter<T, String> {

    private static final Map<Class<?>, Class<? extends Converter<?, ?>>> converters = new HashMap<>();

    static {
        converters.put(boolean.class, BooleanStringConverter.class);
        converters.put(Boolean.class, BooleanStringConverter.class);
        converters.put(byte.class, ByteStringConverter.class);
        converters.put(Byte.class, ByteStringConverter.class);
        converters.put(char.class, CharacterStringConverter.class);
        converters.put(Character.class, CharacterStringConverter.class);
        converters.put(double.class, DoubleStringConverter.class);
        converters.put(Double.class, DoubleStringConverter.class);
        converters.put(float.class, FloatStringConverter.class);
        converters.put(Float.class, FloatStringConverter.class);
        converters.put(int.class, IntegerStringConverter.class);
        converters.put(Integer.class, IntegerStringConverter.class);
        converters.put(long.class, LongStringConverter.class);
        converters.put(Long.class, LongStringConverter.class);
        converters.put(short.class, ShortStringConverter.class);
        converters.put(Short.class, ShortStringConverter.class);
        converters.put(UUID.class, UUIDStringConverter.class);
    }

    public static StringConverter<?> getConverter(Class<?> clazz) {
        Class<? extends StringConverter<?>> converterClass = (Class<? extends StringConverter<?>>) converters.get(clazz);
        if (converterClass == null) {
            return null;
        }

        try {
            return converterClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<Class<?>, Class<? extends Converter<?, ?>>> getConverters() {
        return converters;
    }

    @Override
    public String convertFrom(T fromObject) {
        return toString(fromObject);
    }

    @Override
    public T convertTo(String toObject) {
        return fromString(toObject);
    }

    public abstract String toString(T object);

    public abstract T fromString(String string);
}