package com.stardevllc.starlib.objects.key;

@FunctionalInterface
public interface NamespacedKeyable extends Keyable {
    @Override
    NamespacedKey getKey();
}
