package com.stardevllc.starlib.objects.registry;

import com.stardevllc.starlib.eventbus.SubscribeEvent;
import com.stardevllc.starlib.value.WritableBooleanValue;
import com.stardevllc.starlib.value.impl.SimpleBooleanValue;

/**
 * Listener for map change events
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@SubscribeEvent
@FunctionalInterface
public interface RegistryChangeListener<K extends Comparable<K>, V> {
    
    record Change<K extends Comparable<K>, V>(Registry<K, V> registry, K key, V added, V removed, WritableBooleanValue cancelled) {
        public Change(Registry<K, V> registry, K key, V added, V removed, WritableBooleanValue cancelled) {
            this.registry = registry;
            this.key = key;
            this.added = added;
            this.removed = removed;
            if (cancelled != null) {
                this.cancelled = cancelled;
            } else {
                this.cancelled = new SimpleBooleanValue();
            }
        }
        
        public Change(Registry<K, V> registry, K key, V added, V removed) {
            this(registry, key, added, removed, new SimpleBooleanValue());
        }
        
        static <K extends Comparable<K>, V> Change<K, V> full(Registry<K, V> registry, K key, V added, V removed) {
            return new Change<>(registry, key, added, removed);
        }
        
        static <K extends Comparable<K>, V> Change<K, V> added(Registry<K, V> registry, K key, V added) {
            return new Change<>(registry, key, added, null);
        }
        
        static <K extends Comparable<K>, V> Change<K, V> removed(Registry<K, V> registry, K key, V removed) {
            return new Change<>(registry, key, null, removed);
        }
    }
    
    /**
     * Called when changes occur
     *
     * @param change The change information
     */
    void changed(Change<K, V> change);
    
    /**
     * Called when changes occur. This passes the arguments into a {@link Change} and then to the {@link #changed(Change)} method
     *
     * @param registry The Registry
     * @param key      The key
     * @param added    The added value (if any)
     * @param removed  The removed value (if any)
     */
    default void changed(Registry<K, V> registry, K key, V added, V removed) {
        changed(new Change<>(registry, key, added, removed));
    }
}