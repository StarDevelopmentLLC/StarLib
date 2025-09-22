package com.stardevllc.starlib.generator;

import com.stardevllc.starlib.builder.IBuilder;
import com.stardevllc.starlib.factory.IFactory;

import java.util.Deque;
import java.util.function.*;

/**
 * Effectively an infinite Stream of new objects. Much like Stream.generate() but with some more extendibility
 * You can implement this interface for a more customization if needed. Otherwise there is a base implementation
 *
 * @param <T> The object type that this Generator makes
 */
public interface Generator<T> {
    
    /**
     * Constructs a Generator using the Supplier to create new objects
     *
     * @param supplier The supplier that is used to create new objects
     * @param <T>      The object type
     * @return A new generator instance
     */
    static <T> Generator<T> create(Supplier<T> supplier) {
        return new GeneratorImpl<>(supplier);
    }
    
    /**
     * Constructs a Generator using a {@link IBuilder}
     * All this does is delegate to {@link Generator#create(Supplier)} with a method reference on the {@link IBuilder#build()} method
     *
     * @param builder The builder instance
     * @param <T>     The object type
     * @return A new generator instance
     */
    static <T> Generator<T> create(IBuilder<T, ?> builder) {
        return create(builder::build);
    }
    
    /**
     * Constructs a Generator using a {@link IFactory}
     * All this does is delegate to {@link Generator#create(Supplier)} with a method reference on the {@link IFactory#create()} method
     *
     * @param factory The factory instance
     * @param <T>     The object type
     * @return A new generator instance
     */
    static <T> Generator<T> create(IFactory<T, ?> factory) {
        return create((Supplier<T>) factory::create);
    }
    
    /**
     * Applies an action on each of the generated objects
     * Note: The objects are never null. If a Supplier or Builder gives a null value, an Exception is thrown
     *
     * @param action The action to apply
     * @return This generator
     */
    default Generator<T> apply(Consumer<? super T> action) {
        return apply((object, params) -> action.accept(object));
    }
    
    /**
     * Applies an action of each of the generated objects
     * Note: The objects are never null. If a Supplier or Builder gives a null value, an Exception is thrown
     * This one passes through the parameters in {@link Generator#generate(Object[])}. The passed in array is not null if using the default implementation
     *
     * @param action The action to apply with parameters
     * @return This generator
     */
    default Generator<T> apply(BiConsumer<? super T, Object[]> action) {
        return applyIf((object, params) -> true, action);
    }
    
    /**
     * Applies the action if the test passes
     * Note: The objects are never null. If a Supplier or Builder gives a null value, an Exception is thrown
     *
     * @param test   The test
     * @param action The action
     * @return This generator
     */
    default Generator<T> applyIf(Predicate<? super T> test, Consumer<? super T> action) {
        return applyIf((object, params) -> test.test(object), (object, params) -> action.accept(object));
    }
    
    /**
     * Applies the action if the test passes
     * Note: The objects are never null. If a Supplier or Builder gives a null value, an Exception is thrown
     * This one passes through the parameters in {@link Generator#generate(Object[])}. The passed in array is not null if using the default implementation
     *
     * @param test   The test
     * @param action The action
     * @return This generator
     */
    Generator<T> applyIf(BiPredicate<? super T, Object[]> test, BiConsumer<? super T, Object[]> action);
    
    /**
     * Generate a certain amount of objects. Much like doing an indexed for loop
     * Can be used with {@link Generator#whileTrue(Predicate)} or {@link Generator#whileFalse(Predicate)}. Whichever condition is met first is what ends
     *
     * @param limit The amount of objects to generate
     * @return This generator
     */
    Generator<T> limit(long limit);
    
    /**
     * Continues generation until this condition if false
     * Mutually Exclusive with {@link Generator#whileFalse(Predicate)}
     * Can be used with {@link Generator#limit(long)}. Generation ends when the limit is reached, or this condition is false. Whichever first
     * This condition is called after the object is generated and before it is added to the final Deque.
     *
     * @param condition The condition for continued running
     * @return This generator
     * @throws IllegalStateException If a {@link Generator#whileFalse(Predicate)} condition is already set
     */
    default Generator<T> whileTrue(Predicate<? super T> condition) {
        return whileTrue((object, params) -> condition.test(object));
    }
    
    /**
     * Continues generation until this condition if false
     * Mutually Exclusive with {@link Generator#whileFalse(Predicate)}
     * Can be used with {@link Generator#limit(long)}. Generation ends when the limit is reached, or this condition is false. Whichever first
     * This one passes through the parameters in {@link Generator#generate(Object[])}. The passed in array is not null if using the default implementation
     * This condition is called after the object is generated and before it is added to the final Deque.
     *
     * @param condition The condition for continued running
     * @return This generator
     * @throws IllegalStateException If a {@link Generator#whileFalse(Predicate)} condition is already set
     */
    Generator<T> whileTrue(BiPredicate<? super T, Object[]> condition);
    
    /**
     * Continues generation until this condition is true
     * Mutually Exclusive with {@link Generator#whileTrue(Predicate)}
     * Can be used with {@link Generator#limit(long)}. Generation ends when the limit is reached, or this condition is true. Whichever first
     * This condition is called after the object is generated and before it is added to the final Deque.
     *
     * @param condition The condition for continued running
     * @return This generator
     * @throws IllegalStateException If a {@link Generator#whileTrue(Predicate)} condition is already set
     */
    default Generator<T> whileFalse(Predicate<? super T> condition) {
        return whileFalse((object, params) -> condition.test(object));
    }
    
    /**
     * Continues generation until this condition is true
     * Mutually Exclusive with {@link Generator#whileTrue(Predicate)}
     * Can be used with {@link Generator#limit(long)}. Generation ends when the limit is reached, or this condition is true. Whichever first
     * This one passes through the parameters in {@link Generator#generate(Object[])}. The passed in array is not null if using the default implementation
     * This condition is called after the object is generated and before it is added to the final Deque.
     *
     * @param condition The condition for continued running
     * @return This generator
     * @throws IllegalStateException If a {@link Generator#whileTrue(Predicate)} condition is already set
     */
    Generator<T> whileFalse(BiPredicate<? super T, Object[]> condition);
    
    /**
     * Performs generation of the objects and applies all things
     *
     * @return The Deque of objects that were generated. A Deque is a collection that supports FIFO and LIFO operations
     */
    default Deque<T> generate() {
        return generate(null);
    }
    
    /**
     * This is similar to the {@link #generate(Object[])} method where it generates things, but it just discards the generated objects immediately. Most useful for things that are done using the apply functions
     *
     * @param args The args for the generation
     */
    void generateAndDiscard(Object[] args);
    
    /**
     * Runs generation and discards results
     */
    default void generateAndDiscard() {
        generateAndDiscard(null);
    }
    
    /**
     * Performs generation of the objects and applies all things. Has paramters to pass things through. It is up to the consumers to handle them
     *
     * @param parameters The parameters
     * @return The Deque of objects that were generated. A Deque is a collection that supports FIFO and LIFO operations
     */
    Deque<T> generate(Object[] parameters);
    
    /**
     * This method is a finalizer like the {@link Generator#generate()} method
     * Instead of returning anything, it just loops through the objects after they are all generated
     * This is relatively similar to the {@link Generator#apply(Consumer)} )} and {@link Generator#applyIf(Predicate, Consumer)}, but is performed AFTER generation not DURING generation
     *
     * @param action The action to perform
     */
    default void forEach(Consumer<? super T> action) {
        forEach((object, params) -> action.accept(object), null);
    }
    
    /**
     * This method is a finalizer like the {@link Generator#generate(Object[])} method
     * Instead of returning anything, it just loops through the objects after they are all generated
     * This is relatively similar to the {@link Generator#apply(BiConsumer)} and {@link Generator#applyIf(BiPredicate, BiConsumer)}, but is performed AFTER generation not DURING generation
     *
     * @param action The action to perform
     * @param params The params for the generation
     */
    void forEach(BiConsumer<? super T, Object[]> action, Object[] params);
}