package com.stardevllc.starlib.collections.listmap;

public interface SortedListMap<K, V> extends ListMap<K, V> {
    K firstKey();
    
    K lastKey();
}