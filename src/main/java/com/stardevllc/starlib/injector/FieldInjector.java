package com.stardevllc.starlib.injector;

import com.stardevllc.starlib.objects.builder.IBuilder;
import com.stardevllc.starlib.factory.IFactory;
import com.stardevllc.starlib.helper.ObjectProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Facilitates passing in object instances using reflection and the {@link Inject} annotation
 */
public interface FieldInjector {
    /**
     * Creates a default instance. Implement {@link FieldInjector} for a custom implementation
     *
     * @return A new default injector
     */
    static FieldInjector create() {
        return new SimpleFieldInjector();
    }
    
    /**
     * Injects the values in this injector into the object
     *
     * @param object The object to inject into
     * @param <I>    The type
     * @return The object (For chaining or setting)
     */
    <I> I inject(I object);
    
    /**
     * Sets the instance to use in injections to the provided instance
     *
     * @param clazz    The class type
     * @param instance The instance
     * @param <T>      The class type
     * @param <I>      The instance type
     * @return The instance
     */
    @Deprecated(forRemoval = true)
    default <T, I extends T> I setInstance(Class<T> clazz, I instance) {
        return set(clazz, instance);
    }
    
    /**
     * Sets the instance to use in injections to the provided instance
     *
     * @param instance The instance
     * @param <I>      The instance type
     * @return The instnace
     */
    @Deprecated(forRemoval = true)
    default <I> I setInstance(I instance) {
        return set(instance);
    }
    
    /**
     * Sets the object provider to use in instance setting
     *
     * @param clazz    The class type
     * @param provider The provider
     * @param <T>      The class type
     * @param <I>      The instance type
     * @return The provider
     */
    @Deprecated(forRemoval = true)
    default <T, I extends T> ObjectProvider<I> setProvider(Class<T> clazz, ObjectProvider<I> provider) {
        return set(clazz, provider);
    }
    
    <I> ObjectProvider<I> getProvider(Class<? super I> clazz);
    
    default <T, I extends T> ObjectProvider<I> set(Class<T> clazz, ObjectProvider<I> provider) {
        ObjectProvider<I> p = getProvider(clazz);
        p.setProvider(provider);
        return provider;
    }
    
    default <T, I extends T> I set(Class<T> clazz, I instance) {
        ObjectProvider<I> provider = getProvider(clazz);
        provider.set(instance);
        return instance;
    }
    
    default <I> I set(I instance) {
        return set((Class<I>) instance.getClass(), instance);
    }
    
    default <T, I extends T> Supplier<I> set(Class<T> clazz, Supplier<I> supplier) {
        ObjectProvider<I> provider = getProvider(clazz);
        provider.set(supplier);
        return supplier;
    }
    
    default <T, I extends T> IFactory<I, ?> set(Class<T> clazz, IFactory<I, ?> factory) {
        ObjectProvider<I> provider = getProvider(clazz);
        provider.set(factory);
        return factory;
    }
    
    default <T, I extends T> IBuilder<I, ?> set(Class<T> clazz, IBuilder<I, ?> builder) {
        ObjectProvider<I> provider = getProvider(clazz);
        provider.set(builder);
        return builder;
    }
    
    /**
     * Gets the parent injectors to this one
     *
     * @return The parent injectors
     */
    default Set<FieldInjector> getParentInjectors() {
        return new HashSet<>();
    }
    
    /**
     * Adds a parent injector. The parent injectors are run after this one is ran
     *
     * @param injector The parent injector
     */
    default void addParentInjector(FieldInjector injector) {
        
    }
}