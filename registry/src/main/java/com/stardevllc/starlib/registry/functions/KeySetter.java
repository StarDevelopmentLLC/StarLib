package com.stardevllc.starlib.registry.functions;

import java.util.function.BiConsumer;

public interface KeySetter<K extends Comparable<K>, V> extends BiConsumer<K, V> {
}
