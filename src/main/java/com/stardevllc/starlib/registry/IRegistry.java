package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.collections.RemoveOnlyArrayList;
import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.event.bus.IEventBus;
import com.stardevllc.starlib.event.bus.SubscribeEvent;
import com.stardevllc.starlib.objects.Nameable;
import com.stardevllc.starlib.objects.id.Identifiable;
import com.stardevllc.starlib.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

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
@SuppressWarnings("DuplicatedCode")
public interface IRegistry<V> extends Iterable<V>, Nameable, Identifiable {
    enum Flag {
        FREEZING, 
        UNFREEZING, 
        REPLACING;
        
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
    interface Event<V> {
        
        IRegistry<V> getRegistry();
        
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
    
    abstract class AbstractEvent<V> implements Event<V> {
        
        private final IRegistry<V> registry;
        private boolean cancelled;
        
        public AbstractEvent(IRegistry<V> registry) {
            this.registry = registry;
        }
        
        @Override
        public IRegistry<V> getRegistry() {
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
    
    @Override
    default @NotNull RegistryKey getId() {
        return RegistryKey.EMPTY;
    }
    
    Class<V> getValueType();
    
    /**
     * This is a human-readable name and should be derived from the key or the key derived from the name
     *
     * @return The human-readable name or an empty string
     */
    default @NotNull String getName() {
        return "";
    }
    
    /**
     * Sets the EventDispatcher to use for this registry. This method does nothing unless a sub-class decides to implement it
     *
     * @param dispatcher The dispatcher
     */
    default void setDispatcher(@NotNull EventDispatcher dispatcher) {
        //No-Op, must provide an implementation
    }
    
    /**
     * Gets the EventDispatcher to use for calling events
     *
     * @return The event dispatcher
     */
    default @NotNull <E extends Event<V>> EventDispatcher getDispatcher() {
        return EventDispatcher.NOOP;
    }
    
    /**
     * An interface that defines what a listener needs to be to listen for registry events<br>
     * This interface supports the {@link IEventBus} system by being annotated with the {@link SubscribeEvent} annotation <br>
     * It is not required to use the {@link IEventBus} system, it just exists to support the event bus system itself
     *
     * @param <E> The Event Type
     */
    @FunctionalInterface
    @SubscribeEvent
    interface Listener<V, E extends Event<V>> {
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
    default <E extends Event<V>> void addListener(Listener<V, E> listener) {
        getDispatcher().addListener(listener);
    }
    
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
     * Creates a key for the object. This returns the empty key by default and must be overridden for functionality
     *
     * @param object The object that the key is for
     * @return The Key Value
     */
    default @NotNull RegistryKey createKey(V object) {
        return RegistryKey.EMPTY;
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
    
    /**
     * Gets if this registry is not empty
     *
     * @return If the size > 0
     */
    default boolean isNotEmpty() {
        return size() > 0;
    }
    
    /**
     * Gets a copy of the flags that this Registry has
     *
     * @return The copy of the flags
     */
    default @NotNull Set<Flag> getFlags() {
        return EnumSet.noneOf(Flag.class);
    }
    
    /**
     * Checks to see if this registry has the given flag
     *
     * @param flag The flag to check
     * @return If the Registry has the flag
     */
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
    final class FreezeEvent<V> extends AbstractEvent<V> {
        public FreezeEvent(IRegistry<V> registry) {
            super(registry);
        }
    }
    
    @FunctionalInterface
    interface FreezeListener<V> extends Listener<V, FreezeEvent<V>> { }
    
    /**
     * Type specific way to add a listener for the FreezeEvent that does not conflict with other methods
     *
     * @param listener The listener
     */
    default void addFreezeListener(FreezeListener<V> listener) {
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
    final class UnfreezeEvent<V> extends AbstractEvent<V> {
        public UnfreezeEvent(IRegistry<V> registry) {
            super(registry);
        }
    }
    
    @FunctionalInterface
    interface UnfreezeListener<V> extends Listener<V, UnfreezeEvent<V>> { }
    
    /**
     * Type specific way to add a listener for the UnfreezeEvent that does not conflict with other methods
     *
     * @param listener The listener
     */
    default void addUnfreezeListener(UnfreezeListener<V> listener) {
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
    final class RegisterEvent<V> extends AbstractEvent<V> {
        private final RegistryKey key;
        private final V value, oldValue;
        
        public RegisterEvent(IRegistry<V> registry, RegistryKey key, V value, V oldValue) {
            super(registry);
            this.key = key;
            this.value = value;
            this.oldValue = oldValue;
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
    }
    
    @FunctionalInterface
    interface RegisterListener<V> extends Listener<V, RegisterEvent<V>> {
    }
    
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
    final class RemoveEvent<V> extends AbstractEvent<V> {
        private final RegistryKey key;
        private final V value;
        
        public RemoveEvent(IRegistry<V> registry, RegistryKey key, V value) {
            super(registry);
            this.key = key;
            this.value = value;
        }
        
        public RegistryKey key() {
            return key;
        }
        
        public V value() {
            return value;
        }
    }
    
    @FunctionalInterface
    interface RemoveListener<V> extends Listener<V, RemoveEvent<V>> {
    }
    
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
        if (m == null) {
            return;
        }
        
        RegisterAllEvent<V> event = getDispatcher().dispatch(new RegisterAllEvent<>(this, m));
        event.getValues().forEach(entry -> register(entry.getLeft(), entry.getRight()));
    }
    
    final class RegisterAllEvent<V> extends AbstractEvent<V> {
        private final List<ImmutablePair<RegistryKey, V>> values;
        public RegisterAllEvent(IRegistry<V> registry, Map<? extends RegistryKey, ? extends V> m) {
            super(registry);
            List<ImmutablePair<RegistryKey, V>> values = new ArrayList<>();
            m.forEach((key, value) -> values.add(ImmutablePair.of(key, value)));
            this.values = new RemoveOnlyArrayList<>(values);
        }
        
        public List<ImmutablePair<RegistryKey, V>> getValues() {
            return values;
        }
    }
    
    @FunctionalInterface
    interface RegisterAllListener<V> extends Listener<V, RegisterAllEvent<V>> {
    }
    
    /**
     * Type specific way to add a listener for the RegisterAllEvent that does not conflict with other methods
     *
     * @param listener The listener
     */
    default void addRegisterALlListener(RegisterAllListener<V> listener) {
        addListener(listener);
    }
    
    /**
     * Clears this registry of the values
     */
    void clear();
    
    /**
     * This event is fired before the clear happens and has a list of Pairings that will be removed <br>
     * This List is modifiable and you can remove elements from it and the clear will only clear elements in the list <br>
     * The list is a {@link RemoveOnlyArrayList} so only removals are allowed. You cannot add elements to the list, you will get an {@link UnsupportedOperationException}
     *
     * @param <V> The Value Type
     */
    final class ClearEvent<V> extends AbstractEvent<V> {
        private final List<ImmutablePair<RegistryKey, V>> values;
        public ClearEvent(IRegistry<V> registry) {
            super(registry);
            List<ImmutablePair<RegistryKey, V>> values = new ArrayList<>();
            registry.forEach((key, value) -> values.add(ImmutablePair.of(key, value)));
            this.values = new RemoveOnlyArrayList<>(values);
        }
        
        public List<ImmutablePair<RegistryKey, V>> getValues() {
            return values;
        }
    }
    
    @FunctionalInterface
    interface ClearListener<V> extends Listener<V, ClearEvent<V>> {
    }
    
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
     * Pretty much just {@link Map#computeIfAbsent(Object, Function)}
     * @param key The Key
     * @param mappingFunction The mapping function
     * @return The value with the key if present, or the new value it wasn't
     */
    default V computeIfAbsent(RegistryKey key, Function<RegistryKey, ? extends V> mappingFunction) {
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                register(key, newValue);
                return newValue;
            }
        }
        
        return v;
    }
    
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
}