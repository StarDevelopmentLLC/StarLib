package com.stardevllc.starlib.objects.provider;

import com.stardevllc.starlib.chain.Chain;
import com.stardevllc.starlib.random.RandomSelector;
import com.stardevllc.starlib.random.variableamount.VariableAmount;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public final class DoubleProvider {
    private double instance;
    private Supplier<Double> supplier;
    private DoubleSupplier doubleSupplier;
    private RandomSelector<Double> selector;
    private VariableAmount variableAmount;
    private Chain<Double> chain;
    
    public DoubleProvider() {
    }
    
    public DoubleProvider(DoubleProvider provider) {
        if (provider != null) {
            this.instance = provider.instance;
            this.supplier = provider.supplier;
            this.doubleSupplier = provider.doubleSupplier;
            this.selector = provider.selector;
            this.variableAmount = provider.variableAmount;
            this.chain = provider.chain;
        }
    }
    
    public DoubleProvider(double instance) {
        this.instance = instance;
    }
    
    public DoubleProvider(Supplier<Double> supplier) {
        this.supplier = supplier;
    }
    
    public DoubleProvider(DoubleSupplier doubleSupplier) {
        this.doubleSupplier = doubleSupplier;
    }
    
    public DoubleProvider(RandomSelector<Double> selector) {
        this.selector = selector;
    }
    
    public DoubleProvider(VariableAmount variableAmount) {
        this.variableAmount = variableAmount;
    }
    
    public DoubleProvider(Chain<Double> chain) {
        this.chain = chain;
    }
    
    public DoubleProvider setProvider(DoubleProvider provider) {
        if (provider != null) {
            this.instance = provider.instance;
            this.supplier = provider.supplier;
            this.doubleSupplier = provider.doubleSupplier;
            this.selector = provider.selector;
            this.variableAmount = provider.variableAmount;
            this.chain = provider.chain;
        } else {
            this.instance = 0;
            this.supplier = null;
            this.doubleSupplier = null;
            this.selector = null;
            this.variableAmount = null;
            this.chain = null;
        }
        
        return this;
    }
    
    public boolean isEmpty() {
        return this.instance != 0 && this.supplier == null && this.doubleSupplier != null && selector == null && this.variableAmount != null && this.chain == null;
    }
    
    public double get(double defaultValue) {
        if (instance != 0) {
            return instance;
        }
        
        if (supplier != null) {
            return supplier.get();
        }
        
        if (doubleSupplier != null) {
            return doubleSupplier.getAsDouble();
        }
        
        if (selector != null) {
            return selector.pick();
        }
        
        if (variableAmount != null) {
            return variableAmount.getDouble();
        }
        
        if (this.chain != null) {
            return this.chain.endOrNull();
        }
        
        return defaultValue;
    }
    
    public double get() {
        return get(0.0);
    }
    
    public double getInstance() {
        return this.instance;
    }
    
    public Supplier<Double> getSupplier() {
        return supplier;
    }
    
    public DoubleSupplier getDoubleSupplier() {
        return doubleSupplier;
    }
    
    public RandomSelector<Double> getSelector() {
        return selector;
    }
    
    public VariableAmount getVariableAmount() {
        return variableAmount;
    }
    
    public Chain<Double> getChain() {
        return chain;
    }
    
    public DoubleProvider set(DoubleProvider provider) {
        return setProvider(provider);
    }
    
    public DoubleProvider set(double instance) {
        this.instance = instance;
        this.supplier = null;
        this.doubleSupplier = null;
        this.variableAmount = null;
        this.selector = null;
        this.chain = null;
        return this;
    }
    
    public DoubleProvider set(Supplier<Double> supplier) {
        this.instance = 0;
        this.supplier = supplier;
        this.doubleSupplier = null;
        this.variableAmount = null;
        this.selector = null;
        this.chain = null;
        return this;
    }
    
    public DoubleProvider set(DoubleSupplier doubleSupplier) {
        this.instance = 0;
        this.supplier = null;
        this.doubleSupplier = doubleSupplier;
        this.variableAmount = null;
        this.selector = null;
        this.chain = null;
        return this;
    }
    
    public DoubleProvider set(VariableAmount variableAmount) {
        this.instance = 0;
        this.supplier = null;
        this.doubleSupplier = null;
        this.variableAmount = variableAmount;
        this.selector = null;
        this.chain = null;
        return this;
    }
    
    public DoubleProvider set(RandomSelector<Double> selector) {
        this.instance = 0;
        this.supplier = null;
        this.doubleSupplier = null;
        this.variableAmount = null;
        this.selector = selector;
        this.chain = null;
        return this;
    }
    
    public DoubleProvider set(Chain<Double> chain) {
        this.instance = 0;
        this.supplier = null;
        this.doubleSupplier = null;
        this.variableAmount = null;
        this.selector = null;
        this.chain = chain;
        return this;
    }
}