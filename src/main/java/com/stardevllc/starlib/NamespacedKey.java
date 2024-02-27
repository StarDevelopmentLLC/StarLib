package com.stardevllc.starlib;

public record NamespacedKey(String namespace, String key) implements Comparable<NamespacedKey> {
    public String toString() {
        return namespace + ":" + key;
    }

    @Override
    public int compareTo(NamespacedKey namespacedKey) {
        return this.toString().compareTo(namespacedKey.toString());
    }
}