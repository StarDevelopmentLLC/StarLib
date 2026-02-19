package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.collections.RemoveOnlyArrayList;
import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starlib.eventbus.SubscribeEvent;
import com.stardevllc.starlib.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;

/**
 * A Registry maps values to a special type of Key
 * <p>
 * This takes elements from Map for some of the methods, but has Iterable as a super interface
 * Not all methods in Map are provided and the methods here are also hard restricted to the type parameters
 * A simple way to implement this interface is to have a subclass that uses a Map and just pass-through those methods
 * </p>
 *
 * @param <V> The Value Type
 */
public interface IRegistry<V> extends Iterable<V> {
    enum Flag {
        FREEZING, UNFREEZING;
        
        private final Flag[] parentFlags;
        Flag() {
            parentFlags = null;
        }
        
        Flag(Flag... parentFlags) {
            this.parentFlags = parentFlags;
        }
        
        public Flag[] getParentFlags() {
            return parentFlags;
        }
    }
    
    /**
     * An interface that represents an Event fired from a Registry
     */
    @FunctionalInterface
    interface Event {
        
        IRegistry<?> getRegistry();
        
        /**
         * Checks to see if the event has been cancelled. <br>
         * Not all events support this
         *
         * @return If the event is cancelled, or false if it is not supported
         */
        default boolean isCancelled() {
            return false;
        }
        
        /**
         * Sets the cancelled flag of the event
         *
         * @param cancelled The status to set the cancellation flag to
         * @throws UnsupportedOperationException If the event doesn't support cancellation
         */
        default void setCancelled(boolean cancelled) {
            throw new UnsupportedOperationException("Event does not support cancellation");
        }
    }
    
    /**
     * This registers a cancel handler to an IEventBus for registry events. <br>
     * Much like how the listener is annotated with {@link SubscribeEvent}, this is not required and exists as a convenience method
     *
     * @param bus The bus to support cancellation
     */
    static void registerCancelHandler(IEventBus<?> bus) {
        bus.addCancelHandler(Event.class, Event::isCancelled);
    }
    
    /**
     * The is is the key of the registry itself. This should be used with other systems
     *
     * @return The key, or just a blank registry key
     */
    default @NotNull RegistryKey getKey() {
        return RegistryKey.EMPTY;
    }
    
    /**
     * Checks to see if the registry has a Key. This checks against the Emtpy Key due to how the equals logic works, this will be fine for sub-classes as well
     *
     * @return If the registry has a key
     */
    default boolean hasKey() {
        return !RegistryKey.EMPTY.equals(getKey());
    }
    
    /**
     * This is a human-readable name and should be derived from the key or the key derived from the name
     *
     * @return The human-readable name or an empty string
     */
    default @NotNull String getName() {
        return "";
    }
    
    /**
     * Checks to see if the registry has a human-readable name. If the name isn't an empty string, it has a name
     *
     * @return If this registry has a human readable name
     */
    default boolean hasName() {
        return !getName().isEmpty();
    }
    
    /**
     * Sets the EventDispatcher to use for this registry. This method does nothing unless a sub-class decides to implement it
     *
     * @param dispatcher The dispatcher
     */
    default void setDispatcher(@NotNull EventDispatcher<?> dispatcher) {
        //No-Op, must provide an implementation
    }
    
    /**
     * Gets the EventDispatcher to use for calling events
     *
     * @return The event dispatcher
     */
    @NotNull <E extends Event> EventDispatcher<E> getDispatcher();
    
    /**
     * An interface that defines what a listener needs to be to listen for registry events<br>
     * This interface supports the {@link IEventBus} system by being annotated with the {@link SubscribeEvent} annotation <br>
     * It is not required to use the {@link IEventBus} system, it just exists to support the event bus system itself
     *
     * @param <E> The Event Type
     */
    @FunctionalInterface
    @SubscribeEvent
    interface RegistryListener<E extends Event> {
        /**
         * The functional method that takes in the event <br>
         *
         * @param event The event to handle
         */
        void onEvent(E event);
    }
    
    /**
     * Adds a listener to this registry
     *
     * @param listener The listener instance
     * @param <E>      The event type
     */
    <E extends Event> void addListener(RegistryListener<E> listener);
    
    /**
     * Method to create a Key from a String. Used in the String convenience methods, or can be used outside of them
     *
     * @param key The String key
     * @return The Key value
     */
    default @NotNull RegistryKey createKey(String key) {
        return RegistryKey.of(key);
    }
    
    /**
     * Gets the total size of the registry
     *
     * @return The registry size
     */
    int size();
    
    /**
     * Gets if this registry is empty
     *
     * @return If the size == 0
     */
    default boolean isEmpty() {
        return size() == 0;
    }
    
    default @NotNull Set<Flag> getFlags() {
        return EnumSet.noneOf(Flag.class);
    }
    
    default boolean hasFlag(Flag flag) {
        return getFlags().contains(flag);
    }
    
    /**
     * Checks to see if this registry is frozen
     *
     * @return The frozen status
     */
    boolean isFrozen();
    
    /**
     * Returns if this Registry supports being frozen, false by default
     *
     * @return If this registry supports freezing
     */
    default boolean supportsFreezing() {
        return hasFlag(Flag.FREEZING);
    }
    
    enum FreezeResult {
        UNSUPPORTED, ALREADY_FROZEN, EVENT_CANCELLED, SUCCESS
    }
    
    /**
     * Freezes the registry so that no new objects can be registered
     *
     * @return If the change was successful. True only happens if the event was not cancelled, and if it was not frozen already
     */
    FreezeResult freeze();
    
    /**
     * This event is fired when the registry is frozen
     */
    final class FreezeEvent implements Event {
        private IRegistry<?> registry;
        private boolean cancelled;
        
        public FreezeEvent(IRegistry<?> registry) {
            this.registry = registry;
        }
        
        @Override
        public IRegistry<?> getRegistry() {
            return registry;
        }
        
        @Override
        public boolean isCancelled() {
            return cancelled;
        }
        
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
    
    @FunctionalInterface
    interface FreezeListener extends RegistryListener<FreezeEvent> {}
    
    /**
     * Type specific way to add a listener for the FreezeEvent that does not conflict with other methods
     *
     * @param listener The listener
     */
    default void addFreezeListener(FreezeListener listener) {
        addListener(listener);
    }
    
    /**
     * Returns if this registry supports unfreezing, false by default
     *
     * @return If this registry can be unfrozen
     */
    default boolean supportsUnfreezing() {
        return hasFlag(Flag.UNFREEZING);
    }
    
    enum UnfreezeResult {
        UNSUPPORTED, NOT_FROZEN, EVENT_CANCELLED, SUCCESS
    }
    
    /**
     * Unfreezes the registry to allow new registrations
     *
     * @return If the change was successful. This only happens if the event is not cancelled, and if the registry was frozen already
     */
    UnfreezeResult unfreeze();
    
    /**
     * This event is fired when the registry is unfrozen
     */
    final class UnfreezeEvent implements Event {
        private IRegistry<?> registry;
        private boolean cancelled;
        
        public UnfreezeEvent(IRegistry<?> registry) {
            this.registry = registry;
        }
        
        @Override
        public IRegistry<?> getRegistry() {
            return registry;
        }
        
        @Override
        public boolean isCancelled() {
            return cancelled;
        }
        
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
    
    @FunctionalInterface
    interface UnfreezeListener extends RegistryListener<UnfreezeEvent> {}
    
    /**
     * Type specific way to add a listener for the UnfreezeEvent that does not conflict with other methods
     *
     * @param listener The listener
     */
    default void addUnfreezeListener(UnfreezeListener listener) {
        addListener(listener);
    }
    
    /**
     * Checks to see if this registry contains the specified key
     *
     * @param key The key to check
     * @return If the registry has the key
     */
    boolean containsKey(RegistryKey key);
    
    /**
     * Convenience method to use a String directly as a Key
     *
     * @param key The key
     * @return If it contains the key
     */
    default boolean containsKey(String key) {
        return containsKey(createKey(key));
    }
    
    /**
     * Checks to see if this registry contains the specified value
     *
     * @param value The value to check
     * @return If the registry has the value
     */
    boolean containsValue(V value);
    
    /**
     * Gets the value associated with the key
     *
     * @param key The key
     * @return The value, or null if no mapping exists
     */
    V get(RegistryKey key);
    
    /**
     * Convenience method to use a String directly as that is what the key is
     *
     * @param key The key
     * @return The value
     */
    default V get(String key) {
        return get(createKey(key));
    }
    
    /**
     * Gets all of the keys associated to the value provided. Providing null will return mappings of Keys to null values
     *
     * @param value The value
     * @return The collection of keys that map to the specified value
     */
    Collection<RegistryKey> get(V value);
    
    /**
     * Registers a mapping of the key to the value
     *
     * @param key   The key
     * @param value The value
     * @return The previous value or null if it did not exist
     */
    V register(RegistryKey key, V value);
    
    /**
     * Convenience method to use a String as they key directly
     *
     * @param key   The key
     * @param value The value
     * @return The previous value or null if it did not exist
     */
    default V register(String key, V value) {
        return register(createKey(key), value);
    }
    
    /**
     * This is fired before a new Key-Value mapping is registered to the Registry
     *
     * @param <V> The valu type
     */
    final class RegisterEvent<V> implements Event {
        private final IRegistry<V> registry;
        private final RegistryKey key;
        private final V value, oldValue;
        private boolean cancelled;
        
        public RegisterEvent(IRegistry<V> registry, RegistryKey key, V value, V oldValue) {
            this.registry = registry;
            this.key = key;
            this.value = value;
            this.oldValue = oldValue;
        }
        
        @Override
        public IRegistry<V> getRegistry() {
            return registry;
        }
        
        public RegistryKey key() {
            return key;
        }
        
        public V oldValue() {
            return oldValue;
        }
        
        public V value() {
            return value;
        }
        
        @Override
        public boolean isCancelled() {
            return cancelled;
        }
        
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
    
    @FunctionalInterface
    interface RegisterListener<V> extends RegistryListener<RegisterEvent<V>> {}
    
    /**
     * Type specific way to add a listener for the RegisterEvent that does not conflict with other methods
     *
     * @param listener The listener
     */
    default void addRegisterListener(RegisterListener<V> listener) {
        addListener(listener);
    }
    
    /**
     * Removes a Key-Value mapping from the registry
     *
     * @param key The key to remove the mapping
     * @return The value previously associated with the key, or null if the mapping did not exist
     */
    V remove(RegistryKey key);
    
    /**
     * Convenience method to use String as the key directly
     *
     * @param key The key
     * @return The value
     */
    default V remove(String key) {
        return remove(createKey(key));
    }
    
    /**
     * This is fired before the removal actually happens
     *
     * @param <V>
     */
    final class RemoveEvent<V> implements Event {
        private final IRegistry<V> registry;
        private final RegistryKey key;
        private final V value;
        private boolean cancelled;
        
        public RemoveEvent(IRegistry<V> registry, RegistryKey key, V value) {
            this.registry = registry;
            this.key = key;
            this.value = value;
        }
        
        @Override
        public IRegistry<V> getRegistry() {
            return registry;
        }
        
        public RegistryKey key() {
            return key;
        }
        
        public V value() {
            return value;
        }
        
        @Override
        public boolean isCancelled() {
            return cancelled;
        }
        
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
    
    @FunctionalInterface
    interface RemoveListener<V> extends RegistryListener<RemoveEvent<V>> {}
    
    /**
     * Type specific way to add a listener for the RemoveEvent that does not conflict with other methods
     *
     * @param listener The listener
     */
    default void addRemoveListener(RemoveListener<V> listener) {
        addListener(listener);
    }
    
    /**
     * Registers all values in the map to this registry
     *
     * @param m The map
     */
    default void registerAll(Map<RegistryKey, ? extends V> m) {
        if (m != null) {
            m.forEach(this::register);
        }
    }
    
    /**
     * Clears this registry of the values
     */
    void clear();
    
    /**
     * This event is fired before the clear happens and has a list of Pairings that will be removed <br>
     * This List is modifiable and you can remove elements from it and the clear will only clear elements in the lisl <br>
     * The list is a {@link RemoveOnlyArrayList} so only removals are allowed. You cannot add elements to the list, you will get an {@link UnsupportedOperationException}
     *
     * @param <V> The Value Type
     */
    final class ClearEvent<V> implements Event {
        private final IRegistry<V> registry;
        private final List<ImmutablePair<RegistryKey, V>> values;
        private boolean cancelled;
        
        public ClearEvent(IRegistry<V> registry) {
            this.registry = registry;
            List<ImmutablePair<RegistryKey, V>> values = new ArrayList<>();
            registry.forEach((key, value) -> values.add(ImmutablePair.of(key, value)));
            this.values = new RemoveOnlyArrayList<>(values);
        }
        
        @Override
        public IRegistry<V> getRegistry() {
            return registry;
        }
        
        public List<ImmutablePair<RegistryKey, V>> getValues() {
            return values;
        }
        
        @Override
        public boolean isCancelled() {
            return cancelled;
        }
        
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
    
    @FunctionalInterface
    interface ClearListener<V> extends RegistryListener<ClearEvent<V>> {}
    
    /**
     * Type specific way to add a listener for the ClearEvent that does not conflict with other methods
     *
     * @param listener The listener
     */
    default void addClearListener(ClearListener<V> listener) {
        addListener(listener);
    }
    
    /**
     * This is the same as the {@link Map#keySet()}
     *
     * @return The key set
     */
    Set<RegistryKey> keySet();
    
    /**
     * This is the same as the {@link Map#values()}
     *
     * @return The values
     */
    Collection<V> values();
    
    /**
     * This gets the value associated with the key if it exists, or returns the default value if it doesn't without modifying the registry
     *
     * @param key          The Key
     * @param defaultValue The default value
     * @return The value of the mapping if exists, otherwise the default value
     */
    default V getOrDefault(RegistryKey key, V defaultValue) {
        V existing = get(key);
        if (existing != null) {
            return existing;
        }
        
        return defaultValue;
    }
    
    /**
     * Convenience method to use String as a key directly
     *
     * @param key          The key
     * @param defaultValue The default value
     * @return The value
     */
    default V getOrDefault(String key, V defaultValue) {
        return getOrDefault(createKey(key), defaultValue);
    }
    
    /**
     * Iterate over the Key-Value mappinngs
     *
     * @param action The action to perform
     */
    void forEach(BiConsumer<RegistryKey, ? super V> action);
    
    /**
     * Registers the mapping if it doesn't already exist
     *
     * @param key   The key
     * @param value The value
     */
    default void registerIfAbsent(RegistryKey key, V value) {
        if (get(key) == null) {
            register(key, value);
        }
    }
    
    /**
     * Convenience method ot use String directly has a key
     *
     * @param key   the key
     * @param value The value
     */
    default void registerIfAbsent(String key, V value) {
        registerIfAbsent(createKey(key), value);
    }
    
    /**
     * Registers the mapping with the function provided to get a value
     *
     * @param key             The key
     * @param mappingFunction The function
     */
    default void registerIfAbsent(RegistryKey key, Function<RegistryKey, ? extends V> mappingFunction) {
        if (get(key) == null) {
            register(key, mappingFunction.apply(key));
        }
    }
    
    /**
     * Make a view in the form of a Map of the registry that is not backed by the registry
     *
     * @return The Map structure of the Registry
     */
    Map<RegistryKey, V> toMapCopy();
}