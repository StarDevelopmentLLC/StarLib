package com.stardevllc.starlib.variable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This class represents a set of replacements for variables within a variable handler.
 */
public class VariableReplacements {
    private final Map<String, Supplier<String>> replacements = new HashMap<>();
    
    public VariableReplacements set(Object... array) {
        if (array == null) {
            return this;
        }
        
        if (array.length % 2 != 0) {
            throw new IllegalArgumentException("Array length must be even");
        }
        
        for (int i = 0; i < array.length; i += 2) {
            if (array[i] == null) {
                throw new IllegalArgumentException("Array[" + i + "] is null");
            }

            if (array[i + 1] == null) {
                throw new IllegalArgumentException("Array[" + (i + 1) + "] is null");
            }
            
            if (!(array[i] instanceof String varId)) {
                throw new IllegalArgumentException("Array[" + i + "] is not a String");
            }
            
            if (array[i + 1] instanceof String replacement) {
                set(varId, replacement);
            } else if (Supplier.class.isAssignableFrom(array[i + 1].getClass())) {
                Supplier<String> replacement = (Supplier<String>) array[i + 1];
                set(varId, replacement);
            } else {
                throw new IllegalArgumentException("Array[" + (i + 1) + "] is not a Supplier or String");
            }
        }
        
        return this;
    }

    public VariableReplacements set(String varId, String replacement) {
        return set(varId, () -> replacement);
    }
    
    public VariableReplacements set(String varId, Supplier<String> replacement) {
        replacements.put(varId, replacement);
        return this;
    }
    
    public String replaceVariables(String text, VariableHandler handler) {
        for (Map.Entry<String, Supplier<String>> entry : replacements.entrySet()) {
            Variable variable = handler.getVariable(entry.getKey());
            if (variable == null) {
                continue;
            }
            
            text = text.replace(variable.toString(), entry.getValue().get());
        }
        
        return text;
    }
}