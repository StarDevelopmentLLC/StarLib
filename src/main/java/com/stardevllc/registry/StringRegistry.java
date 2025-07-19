package com.stardevllc.registry;

import com.stardevllc.registry.functions.*;

import java.util.Map;

public class StringRegistry<V> extends Registry<String, V> {
    public StringRegistry(Map<String, V> initialObjects, KeyNormalizer<String> keyNormalizer, KeyRetriever<V, String> keyRetriever, KeyGenerator<V, String> keyGenerator, KeySetter<String, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }

    public StringRegistry() {
    }
    
    @Override
    public Builder<V> toBuilder() {
        Builder<V> builder = new Builder<>();
        builder.keyNormalizer(this.keyNormalizer);
        builder.keyRetriever(this.keyRetriever);
        builder.keyGenerator(this.keyGenerator);
        builder.keySetter(this.keySetter);
        for (RegisterListener<String, V> registerListener : this.registerListeners) {
            builder.addRegisterListener(registerListener);
        }
        
        for (UnregisterListener<String, V> unregisterListener : this.unregisterListeners) {
            builder.addUnregisterListener(unregisterListener);
        }
        return builder;
    }

    public static class Builder<V> extends Registry.Builder<String, V, StringRegistry<V>, Builder<V>> {
        @Override
        public StringRegistry<V> build() {
            StringRegistry<V> registry = new StringRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);

            registerListeners.forEach(registry::addRegisterListener);
            unregisterListeners.forEach(registry::addUnregisterListener);
            
            return registry;
        }
    }
}