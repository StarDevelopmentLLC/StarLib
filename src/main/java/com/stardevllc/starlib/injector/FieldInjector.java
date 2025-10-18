package com.stardevllc.starlib.injector;

import com.stardevllc.starlib.helper.ObjectProvider;

import java.util.Set;

/**
 * Facilities passing in object instances using reflection and the {@link Inject} annotation
 */
public interface FieldInjector {
    /**
     * Creates a default instance. Implement {@link FieldInjector} for a custom implementation
     *
     * @return A new default injector
     */
    static FieldInjector create() {
        return new FieldInjectorImpl();
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
    <T, I extends T> I setInstance(Class<T> clazz, I instance);
    
    /**
     * Sets the instance to use in injections to the provided instance
     *
     * @param instance The instance
     * @param <I>      The instance type
     * @return The instnace
     */
    <I> I setInstance(I instance);
    
    /**
     * Sets the object provider to use in instance setting
     *
     * @param clazz    The class type
     * @param provider The provider
     * @param <T>      The class type
     * @param <I>      The instance type
     * @return The provider
     */
    <T, I extends T> ObjectProvider<I> setProvider(Class<T> clazz, ObjectProvider<I> provider);
    
    /**
     * Gets the parent injectors to this one
     *
     * @return The parent injectors
     */
    Set<FieldInjector> getParentInjectors();
    
    /**
     * Adds a parent injector. The parent injectors are run after this one is ran
     *
     * @param injector The parent injector
     */
    void addParentInjector(FieldInjector injector);
}