package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.collections.RemoveOnlyArrayList;
import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.event.bus.IEventBus;
import com.stardevllc.starlib.event.bus.SubscribeEvent;
import com.stardevllc.starlib.objects.Nameable;
import com.stardevllc.starlib.objects.builder.IBuilder;
import com.stardevllc.starlib.objects.key.*;
import com.stardevllc.starlib.objects.key.impl.StringKey;
import com.stardevllc.starlib.tuple.pair.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

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
public interface IRegistry<V> extends Iterable<V>, Nameable, Keyable {
    
    /**
     * Different flags that change the behavior of the registry
     */
    enum Flag {
        
        /**
         * Freezing means that the registry does not accept new values <br>
         * This flag being present doesn't mean that the registry is frozen, just that the freeze() method will work
         */
        FREEZING,
        
        /**
         * Unfreezing means that the registry can be unfrozen by calling the method
         */
        UNFREEZING,
        
        /**
         * This flag being present means that values can be replaced within the registry
         */
        REPLACING,
        
        /**
         * This flag being present means that the bulk clear action can be performed
         */
        CLEARING,
        
        /**
         * This flag being present means that the registry will check partial keys in the get method
         */
        CHECK_PARTIAL_IN_GET,
        
        /**
         * NOTE: This may be renamed when I find a better name/phrasing <br>
         * This being present means that the current registry's key is added to the full path of a registered item to the parent
         */
        ADD_REGISTRY_KEY_TO_PARENT_REGISTER,
        
        /**
         * This being present means that the registration to this registry will fail on parent registration failure
         */
        FAIL_ON_PARENT_REGISTER_FAILURE
    }
    
    /**
     * An interface that represents an Event fired from a Registry
     */
    @FunctionalInterface
    interface Event<V> {
        
        /**
         * The registry associated with the event
         *
         * @return The registry
         */
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
    
    /**
     * A parent abstract event class to make the event definitions a bit smaller and compact
     *
     * @param <V> The value type
     */
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
    
    /**
     * {@inheritDoc}
     */
    @Override
    default @NotNull Key getKey() {
        return StringKey.EMPTY;
    }
    
    /**
     * The class type of the values of this Registry
     *
     * @return The class of the values
     */
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
     * Gets the EventDispatcher to use for calling events <br>
     * By default it is the NOOP dispatcher meaning it does nothing
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
    default @NotNull Key createKey(String key) {
        return Keys.of(key);
    }
    
    /**
     * Creates a key for the object. This returns the empty key by default and must be overridden for functionality
     *
     * @param object The object that the key is for
     * @return The Key Value
     */
    default @NotNull Key createKey(V object) {
        return StringKey.EMPTY;
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
     * Having a parent registry allows for registering things into the parent when one is registered in this registry. <br>
     * Implementations that do not support the parent registry contract above should return null, not overriding this method works as it returns null by default
     *
     * @return The parent registry or null
     */
    default IRegistry<? super V> getParentRegistry() {
        return null;
    }
    
    /**
     * Checks to see if this registry is frozen
     *
     * @return The frozen status
     */
    default boolean isFrozen() {
        return false;
    }
    
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
     * @return If the freeze was successful. True only happens if the event was not cancelled, and if it was not frozen already
     */
    default FreezeResult freeze() {
        return FreezeResult.UNSUPPORTED;
    }
    
    /**
     * This event is fired when the registry is frozen
     */
    final class FreezeEvent<V> extends AbstractEvent<V> {
        public FreezeEvent(IRegistry<V> registry) {
            super(registry);
        }
    }
    
    @FunctionalInterface
    interface FreezeListener<V> extends Listener<V, FreezeEvent<V>> {
    }
    
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
    default UnfreezeResult unfreeze() {
        return UnfreezeResult.UNSUPPORTED;
    }
    
    /**
     * This event is fired when the registry is unfrozen
     */
    final class UnfreezeEvent<V> extends AbstractEvent<V> {
        public UnfreezeEvent(IRegistry<V> registry) {
            super(registry);
        }
    }
    
    @FunctionalInterface
    interface UnfreezeListener<V> extends Listener<V, UnfreezeEvent<V>> {
    }
    
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
    boolean containsKey(Object key);
    
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
    V get(Key key);
    
    /**
     * Gets all keys that partially match the provided keys
     *
     * @param key The key
     * @return The matches
     */
    List<Key> getPartial(Key key);
    
    /**
     * Gets the value associated with the key
     *
     * @param key The key
     * @return The value, or null if no mapping exists
     */
    default V get(String key) {
        return get(createKey(key));
    }
    
    /**
     * Gets all keys that partially match the provided key
     *
     * @param key The key
     * @return The matches
     */
    default List<Key> getPartial(String key) {
        return getPartial(createKey(key));
    }
    
    /**
     * Gets all of the keys associated to the value provided. Providing null will return mappings of Keys to null values
     *
     * @param value The value
     * @return The collection of keys that map to the specified value
     */
    Collection<Key> get(V value);
    
    @SuppressWarnings("NegativelyNamedBooleanVariable")
    record RegisterResult<V>(Key key, V value, V oldValue, boolean success, RegisterResult<? super V> parentResult, boolean frozen, boolean noReplacements, boolean cancelled, boolean cannotCast) {
        public static <V> RegisterResult<V> ofSuccess(Key key, V value, V oldValue, RegisterResult<? super V> parentResult) {
            return new RegisterResult<>(key, value, oldValue, true, parentResult, false, false, false, false);
        }
        
        public static <V> RegisterResult<V> ofParentFailure(Key key, V value, RegisterResult<? super V> parentResult) {
            return new RegisterResult<>(key, value, null, false, parentResult, false, false, false, false);
        }
        
        public static <V> RegisterResult<V> ofFrozenFailure(Key key, V value) {
            return new RegisterResult<>(key, value, null, false, null, true, false, false, false);
        }
        
        public static <V> RegisterResult<V> ofReplacementFailure(Key key, V value) {
            return new RegisterResult<>(key, value, null, false, null, false, true, false, false);
        }
        
        public static <V> RegisterResult<V> ofCancelledFailure(Key key, V value) {
            return new RegisterResult<>(key, value, null, false, null, false, false, true, false);
        }
        
        public static <V> RegisterResult<V> ofCastFailure(Key key, V value) {
            return new RegisterResult<>(key, value, null, false, null, false, false, false, true);
        }
        
        public boolean isPresent() {
            return key != null && success;
        }
    }
    
    /**
     * Registers a mapping of the key to the value
     *
     * @param key   The key
     * @param value The value
     * @return The previous value or null if it did not exist
     */
    RegisterResult<V> register(Key key, V value);
    
    default RegisterResult<V> register(String key, V value) {
        RegisterResult<V> result = register(createKey(key), value);
        if (value instanceof Keyable keyable) {
            if (keyable.supportsSettingKey()) {
                keyable.setKey(result.key());
            }
        }
        return result;
    }
    
    default RegisterResult<V> register(Keyable keyable) {
        try {
            V v = (V) keyable;
            return register(keyable.getKey(), v);
        } catch (ClassCastException e) {}
        
        return RegisterResult.ofCastFailure(keyable.getKey(), null);
    }
    
    /**
     * This is fired before a new Key-Value mapping is registered to the Registry
     *
     * @param <V> The valu type
     */
    final class RegisterEvent<V> extends AbstractEvent<V> {
        private final Key key;
        private final V value, oldValue;
        
        public RegisterEvent(IRegistry<V> registry, Key key, V value, V oldValue) {
            super(registry);
            this.key = key;
            this.value = value;
            this.oldValue = oldValue;
        }
        
        public Key key() {
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
    V remove(Key key);
    
    default V remove(String key) {
        return remove(createKey(key));
    }
    
    /**
     * This is fired before the removal actually happens
     *
     * @param <V>
     */
    final class RemoveEvent<V> extends AbstractEvent<V> {
        private final Key key;
        private final V value;
        
        public RemoveEvent(IRegistry<V> registry, Key key, V value) {
            super(registry);
            this.key = key;
            this.value = value;
        }
        
        public Key key() {
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
    default Set<RegisterResult<V>> registerAll(Map<Key, ? extends V> m) {
        if (m == null) {
            return Set.of();
        }
        
        RegisterAllEvent<V> event = getDispatcher().dispatch(new RegisterAllEvent<>(this, m));
        return event.getValues().stream().map(entry -> register(entry.getLeft(), entry.getRight())).collect(Collectors.toSet());
    }
    
    final class RegisterAllEvent<V> extends AbstractEvent<V> {
        private final List<ImmutablePair<Key, V>> values;
        
        public RegisterAllEvent(IRegistry<V> registry, Map<? extends Key, ? extends V> m) {
            super(registry);
            List<ImmutablePair<Key, V>> values = new ArrayList<>();
            m.forEach((key, value) -> values.add(ImmutablePair.of(key, value)));
            this.values = new RemoveOnlyArrayList<>(values);
        }
        
        public List<ImmutablePair<Key, V>> getValues() {
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
        private final List<ImmutablePair<Key, V>> values;
        
        public ClearEvent(IRegistry<V> registry) {
            super(registry);
            List<ImmutablePair<Key, V>> values = new ArrayList<>();
            registry.forEach((key, value) -> values.add(ImmutablePair.of(key, value)));
            this.values = new RemoveOnlyArrayList<>(values);
        }
        
        public List<ImmutablePair<Key, V>> getValues() {
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
    Set<Key> keySet();
    
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
    default V getOrDefault(Key key, V defaultValue) {
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
    void forEach(BiConsumer<Key, ? super V> action);
    
    /**
     * Pretty much just {@link Map#computeIfAbsent(Object, Function)}
     *
     * @param key             The Key
     * @param mappingFunction The mapping function
     * @return The value with the key if present, or the new value it wasn't
     */
    default V computeIfAbsent(Key key, Function<Key, ? extends V> mappingFunction) {
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
    default void registerIfAbsent(Key key, V value) {
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
    default void registerIfAbsent(Key key, Function<Key, ? extends V> mappingFunction) {
        if (get(key) == null) {
            register(key, mappingFunction.apply(key));
        }
    }
    
    abstract class Builder<V, R extends IRegistry<V>, B extends Builder<V, R, B>> implements IBuilder<R, B> {
        protected final Class<V> valueType;
        protected Supplier<Map<Key, V>> mapSupplier;
        protected IRegistry<? super V> parentRegistry;
        protected Key key;
        protected String name;
        protected EventDispatcher dispatcher;
        protected Set<IRegistry.Flag> flags = EnumSet.noneOf(IRegistry.Flag.class);
        protected boolean global;
        protected final Map<Key, V> initialValues = new HashMap<>(); //We can use a hashmap here as it doesn't matter and it is faster than other maps
        protected final List<Listener<V, ? extends Event<V>>> listeners = new ArrayList<>();
        
        protected Builder(Class<V> valueType) {
            this.valueType = valueType;
        }
        
        protected Builder(B builder) {
            this.valueType = builder.valueType;
            this.mapSupplier = builder.mapSupplier;
            this.parentRegistry = builder.parentRegistry;
            this.key = builder.key;
            this.name = builder.name;
            this.dispatcher = builder.dispatcher;
            this.flags = builder.flags;
            this.global = builder.global;
            this.initialValues.putAll(builder.initialValues);
            this.listeners.addAll(builder.listeners);
        }
        
        public B withSupplier(Supplier<Map<Key, V>> mapSupplier) {
            this.mapSupplier = mapSupplier;
            return self();
        }
        
        public B withKey(Key key) {
            this.key = key;
            return self();
        }
        
        public B withName(String name) {
            this.name = name;
            return self();
        }
        
        public B withDispatcher(EventDispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return self();
        }
        
        public B withFlags(IRegistry.Flag... flags) {
            if (flags != null) {
                this.flags.addAll(List.of(flags));
            }
            
            return self();
        }
        
        public B allowFreezing() {
            this.flags.add(IRegistry.Flag.FREEZING);
            return self();
        }
        
        public B allowUnfreezing() {
            this.flags.add(IRegistry.Flag.UNFREEZING);
            return self();
        }
        
        public B checkPartialInGet() {
            this.flags.add(Flag.CHECK_PARTIAL_IN_GET);
            return self();
        }
        
        public B asGlobal() {
            this.global = true;
            return self();
        }
        
        public B withParent(IRegistry<? super V> parent) {
            this.parentRegistry = parent;
            return self();
        }
        
        public B put(Key key, V value) {
            this.initialValues.put(key, value);
            return self();
        }
        
        public B addRegisterListener(RegisterListener<V> listener) {
            this.listeners.add(listener);
            return self();
        }
        
        public B addRemoveListener(RemoveListener<V> listener) {
            this.listeners.add(listener);
            return self();
        }
        
        public B addClearListener(ClearListener<V> listener) {
            this.listeners.add(listener);
            return self();
        }
        
        protected final B prebuild() {
            if (key == null && name != null) {
                this.key = Keys.of(name);
            } else if (key != null && name == null) {
                this.name = this.key.toString();
            }
            
            if (mapSupplier == null) {
                throw new IllegalStateException("Map Supplier cannot be null");
            }
            
            Map<Key, V> backingMap = mapSupplier.get();
            if (backingMap == null) {
                throw new IllegalStateException("Map Supplier cannot return a null map");
            }
            return self();
        }
        
        protected final R postBuild(R registry) {
            initialValues.forEach(registry::register);
            this.listeners.forEach(registry::addListener);
            
            if (global) {
                if (key != null && key.isNotEmpty()) {
                    Registries.addRegistry(registry);
                }
            }
            return registry;
        }
        
        public abstract B clone();
    }
}