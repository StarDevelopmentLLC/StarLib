package com.stardevllc.helper;

import java.util.HashMap;
import java.util.Map;

public final class MapHelper {
    public static <K, V> Map<K, V> of(K firstKey, V firstValue, Object... rawValues) {
        if (rawValues.length % 2 != 0) {
            throw new IllegalArgumentException("Number of raw values must be even");
        }

        Map<K, V> map = new HashMap<>();
        map.put(firstKey, firstValue);
        for (int i = 0; i < rawValues.length; i += 2) {
            try {
                map.put((K) rawValues[i], (V) rawValues[i + 1]);
            } catch (Throwable t) {}
        }
        return map;
    }
}
