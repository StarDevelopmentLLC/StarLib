package com.stardevllc.starlib.converter.string;

import com.stardevllc.starlib.converter.Converter;

/**
 * Defines the contract to convert from {@link F} to a String and back again
 * @param <F> The main type
 */
public interface StringConverter<F> extends Converter<F, String> {
}