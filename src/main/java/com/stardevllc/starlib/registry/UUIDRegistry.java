package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.*;

import java.util.*;

public class UUIDRegistry<V> extends Registry<UUID, V> {
    public UUIDRegistry(Map<UUID, V> initialObjects, KeyNormalizer<UUID> keyNormalizer, KeyRetriever<V, UUID> keyRetriever, KeyGenerator<V, UUID> keyGenerator, KeySetter<UUID, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }

    public UUIDRegistry() {
    }
    
    @Override
    public Builder<V> toBuilder() {
        Builder<V> builder = new Builder<>();
        builder.keyNormalizer(this.keyNormalizer);
        builder.keyRetriever(this.keyRetriever);
        builder.keyGenerator(this.keyGenerator);
        builder.keySetter(this.keySetter);
        for (RegisterListener<UUID, V> registerListener : this.registerListeners) {
            builder.addRegisterListener(registerListener);
        }
        
        for (UnregisterListener<UUID, V> unregisterListener : this.unregisterListeners) {
            builder.addUnregisterListener(unregisterListener);
        }
        return builder;
    }

    public static class Builder<V> extends Registry.Builder<UUID, V, UUIDRegistry<V>, Builder<V>> {
        @Override
        public UUIDRegistry<V> build() {
            UUIDRegistry<V> registry = new UUIDRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);

            registerListeners.forEach(registry::addRegisterListener);
            unregisterListeners.forEach(registry::addUnregisterListener);
            
            return registry;
        }
    }
}