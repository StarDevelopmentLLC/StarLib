package com.stardevllc.starlib.generator;

import com.stardevllc.starlib.helper.Pair;

import java.util.*;
import java.util.function.*;

class GeneratorImpl<T> implements Generator<T> {
    private final Supplier<T> supplier;
    
    private List<Action> actions = new LinkedList<>();
    
    private long limit = Long.MAX_VALUE;
    
    private BiPredicate<? super T, Object[]> whileTrueCondition, whileFalseCondition;
    
    GeneratorImpl(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    @Override
    public Generator<T> applyIf(BiPredicate<? super T, Object[]> test, BiConsumer<? super T, Object[]> action) {
        this.actions.add(new Action(test, action));
        return this;
    }
    
    @Override
    public Generator<T> limit(long limit) {
        this.limit = limit;
        return this;
    }
    
    @Override
    public Generator<T> whileTrue(BiPredicate<? super T, Object[]> condition) {
        if (this.whileFalseCondition != null) {
            throw new IllegalStateException("Cannot have both a whileTrue and whileFalse condition");
        }
        
        this.whileTrueCondition = condition;
        return this;
    }
    
    @Override
    public Generator<T> whileFalse(BiPredicate<? super T, Object[]> condition) {
        if (this.whileTrueCondition != null) {
            throw new IllegalStateException("Cannot have both a whileFalse and whileTrue condition");
        }
        
        this.whileFalseCondition = condition;
        return this;
    }
    
    private Pair<T, Boolean> generateObject(Object[] params) {
        T object = this.supplier.get();
        
        if (object == null) {
            throw new IllegalStateException("The supplier returned a null value. This is not allowed");
        }
        
        Object[] paramsCopy;
        if (params != null) {
            paramsCopy = new Object[params.length];
        } else {
            paramsCopy = new Object[]{};
        }
        
        for (Action action : this.actions) {
            if (params != null) {
                System.arraycopy(params, 0, paramsCopy, 0, params.length);
            }
            if (action.getTest().test(object, paramsCopy)) {
                action.getAction().accept(object, paramsCopy);
            }
        }
        
        if (whileTrueCondition != null && !whileTrueCondition.test(object, paramsCopy)) {
            return new Pair<>(object, false);
        }
        
        if (whileFalseCondition != null && whileFalseCondition.test(object, paramsCopy)) {
            return new Pair<>(object, false);
        }
        
        return new Pair<>(object, true);
    }
    
    @Override
    public void generateAndDiscard(Object[] params) {
        if (this.supplier == null) {
            throw new IllegalStateException("The supplier cannot be null");
        }
        
        long totalObjects = 0;
        while (true) {
            if (totalObjects >= this.limit) {
                return;
            }
            
            Pair<T, Boolean> result = generateObject(params);
            if (!result.second()) {
                return;
            }
            
            totalObjects++;
        }
    }
    
    @Override
    public Deque<T> generate(Object[] params) {
        if (this.supplier == null) {
            throw new IllegalStateException("The supplier cannot be null");
        }
        
        LinkedList<T> objects = new LinkedList<>();
        
        while (true) {
            if (objects.size() >= this.limit) {
                return objects;
            }
            
            Pair<T, Boolean> result = generateObject(params);
            
            if (!result.second()) {
                return objects;
            }
            
            objects.add(result.first());
        }
    }
    
    @Override
    public void forEach(BiConsumer<? super T, Object[]> action, Object[] params) {
        Object[] paramsCopy;
        if (params != null) {
            paramsCopy = new Object[params.length];
            System.arraycopy(params, 0, paramsCopy, 0, params.length);
        } else {
            paramsCopy = new Object[]{};
        }
        
        this.generate(params).forEach(object -> {
            if (params != null) {
                System.arraycopy(params, 0, paramsCopy, 0, params.length);
            }
            action.accept(object, paramsCopy);
        });
    }
    
    private class Action {
        protected final BiPredicate<? super T, Object[]> test;
        protected final BiConsumer<? super T, Object[]> action;
        
        public Action(BiPredicate<? super T, Object[]> test, BiConsumer<? super T, Object[]> action) {
            this.test = test;
            this.action = action;
        }
        
        public BiPredicate<? super T, Object[]> getTest() {
            return test;
        }
        
        public BiConsumer<? super T, Object[]> getAction() {
            return action;
        }
    }
}
