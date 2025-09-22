package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.*;

import java.util.Map;

/**
 * Represents a registry mapping Objects of type {@link V} to Strings
 *
 * @param <V> The object type
 */
public class StringRegistry<V> extends Registry<String, V> {
    
    /**
     * Creates a new registry
     *
     * @param initialObjects The initial objects
     * @param keyNormalizer  The key normalizer
     * @param keyRetriever   The key retriever
     * @param keyGenerator   The key generator
     * @param keySetter      The key setter
     */
    public StringRegistry(Map<String, V> initialObjects, KeyNormalizer<String> keyNormalizer, KeyRetriever<V, String> keyRetriever, KeyGenerator<V, String> keyGenerator, KeySetter<String, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }
    
    /**
     * Creates a blank registry
     */
    public StringRegistry() {
    }
    
    /**
     * {@inheritDoc}
     */
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
    
    /**
     * StringRegistry Builder
     *
     * @param <V> Value type
     */
    public static class Builder<V> extends Registry.Builder<String, V, StringRegistry<V>, Builder<V>> {
        /**
         * Default constructor
         */
        public Builder() {
        }
        
        /**
         * Copy constructor
         *
         * @param builder Builder to copy
         */
        public Builder(Builder<V> builder) {
            super(builder);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public StringRegistry<V> build() {
            StringRegistry<V> registry = new StringRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
            
            registerListeners.forEach(registry::addRegisterListener);
            unregisterListeners.forEach(registry::addUnregisterListener);
            
            return registry;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Builder<V> clone() {
            return new Builder<>(this);
        }
    }
}