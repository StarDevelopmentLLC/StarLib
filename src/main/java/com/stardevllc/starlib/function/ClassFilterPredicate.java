package com.stardevllc.starlib.function;

import java.util.*;
import java.util.function.Predicate;

public class ClassFilterPredicate<T> implements Predicate<T> {
    
    private final Set<Class<? extends T>> filter = new HashSet<>();
    
    public ClassFilterPredicate() {
    }
    
    @SafeVarargs
    public ClassFilterPredicate(Class<? extends T> clazz, Class<? extends T>... classes) {
        addToFilter(clazz, classes);
    }
    
    @SafeVarargs
    public final void addToFilter(Class<? extends T> clazz, Class<? extends T>... classes) {
        this.filter.add(clazz);
        if (classes != null) {
            this.filter.addAll(Arrays.asList(classes));
        }
    }
    
    @Override
    public boolean test(T t) {
        if (filter.contains(t.getClass())) {
            return true;
        }
        
        for (Class<? extends T> filterClass : filter) {
            if (filterClass.isAssignableFrom(t.getClass())) {
                return true;
            }
        }
        
        return false;
    }
}