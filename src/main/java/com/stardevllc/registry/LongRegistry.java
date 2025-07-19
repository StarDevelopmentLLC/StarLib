package com.stardevllc.registry;

import com.stardevllc.registry.functions.*;

import java.util.Map;

public class LongRegistry<V> extends Registry<Long, V> {
    public LongRegistry(Map<Long, V> initialObjects, KeyNormalizer<Long> keyNormalizer, KeyRetriever<V, Long> keyRetriever, KeyGenerator<V, Long> keyGenerator, KeySetter<Long, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }

    public LongRegistry() {
    }
    
    @Override
    public Builder<V> toBuilder() {
        Builder<V> builder = new Builder<>();
        builder.keyNormalizer(this.keyNormalizer);
        builder.keyRetriever(this.keyRetriever);
        builder.keyGenerator(this.keyGenerator);
        builder.keySetter(this.keySetter);
        for (RegisterListener<Long, V> registerListener : this.registerListeners) {
            builder.addRegisterListener(registerListener);
        }
        
        for (UnregisterListener<Long, V> unregisterListener : this.unregisterListeners) {
            builder.addUnregisterListener(unregisterListener);
        }
        return builder;
    }

    public static class Builder<V> extends Registry.Builder<Long, V, LongRegistry<V>, Builder<V>> {
        @Override
        public LongRegistry<V> build() {
            LongRegistry<V> registry = new LongRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);

            registerListeners.forEach(registry::addRegisterListener);
            unregisterListeners.forEach(registry::addUnregisterListener);
            
            return registry;
        }
    }
}