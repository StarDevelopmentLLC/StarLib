package com.stardevllc.starlib.objects.factory;

import com.stardevllc.starlib.objects.builder.IBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This is an interface that defines a common contract for Factory types in StarDevLLC projects.
 * Much like the {@link IBuilder} class, this is not a required interface to use if using StarLib
 * This is meant to be flexible in the implementation and can use multiple different ideas or design principles to achieve things.
 * This does not mean you have to follow the "Factory Design Pattern".
 * For example, in a personal project, it uses Builders to manage the actual creation of the object, then a Factory to randomize values for that builder without having to bloat the Builder instance, and a Generator to manage generating multiple at the same time
 *
 * @param <T> The object type
 * @param <F> The factory type
 */
@FunctionalInterface
public interface IFactory<T, F extends IFactory<T, F>> {
    
    /**
     * Creates an object with some parameters passed in.
     * This is useful if it is variable number of parameters or of different types.
     * It is up to the {@link IFactory} implementation to hande that.
     * By default, it delegates to {@link IFactory#create()} ignoring the parameters
     *
     * @param parameters The parameters for the object
     * @return The created object
     */
    T create(Object[] parameters);
    
    /**
     * Creates a new empty object
     *
     * @return The created object
     */
    default T create() {
        return create(null);
    }
    
    /**
     * Called before creation is finalized
     *
     * @param consumer The consumer
     * @return This factory
     */
    default F preCreate(BiConsumer<F, Object[]> consumer) {
        //No-Op - Up to factory implementation to support
        return self();
    }
    
    /**
     * Called before creation is finalized
     *
     * @param consumer The consumer
     * @return This factory
     */
    default F preCreate(Consumer<F> consumer) {
        return preCreate((factory, args) -> consumer.accept(factory));
    }
    
    /**
     * Called after creation is finalized
     *
     * @param consumer The consumer
     * @return This factory
     */
    default F postCreate(BiConsumer<T, Object[]> consumer) {
        //No-Op - Up to factory implementation to support
        return self();
    }
    
    /**
     * Called after creation is finalized
     *
     * @param consumer The consumer
     * @return This factory
     */
    default F postCreate(Consumer<T> consumer) {
        return postCreate((object, args) -> consumer.accept(object));
    }
    
    /**
     * Returns an instance of this builder without having to cast every time.
     * Note when creating methods, use either generic types or the final concrete type if it is a final class
     *
     * @return this builder
     */
    default F self() {
        return (F) this;
    }
}