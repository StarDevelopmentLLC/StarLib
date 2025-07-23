package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.*;

import java.util.Map;

public class IntegerRegistry<V> extends Registry<Integer, V> {
    public IntegerRegistry(Map<Integer, V> initialObjects, KeyNormalizer<Integer> keyNormalizer, KeyRetriever<V, Integer> keyRetriever, KeyGenerator<V, Integer> keyGenerator, KeySetter<Integer, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }

    public IntegerRegistry() {
    }
    
    @Override
    public Builder<V> toBuilder() {
        Builder<V> builder = new Builder<>();
        builder.keyNormalizer(this.keyNormalizer);
        builder.keyRetriever(this.keyRetriever);
        builder.keyGenerator(this.keyGenerator);
        builder.keySetter(this.keySetter);
        for (RegisterListener<Integer, V> registerListener : this.registerListeners) {
            builder.addRegisterListener(registerListener);
        }
        
        for (UnregisterListener<Integer, V> unregisterListener : this.unregisterListeners) {
            builder.addUnregisterListener(unregisterListener);
        }
        return builder;
    }
    
    public static class Builder<V> extends Registry.Builder<Integer, V, IntegerRegistry<V>, Builder<V>> {
        @Override
        public IntegerRegistry<V> build() {
            IntegerRegistry<V> registry = new IntegerRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
            
            registerListeners.forEach(registry::addRegisterListener);
            unregisterListeners.forEach(registry::addUnregisterListener);
            
            return registry;
        }
    }
}
