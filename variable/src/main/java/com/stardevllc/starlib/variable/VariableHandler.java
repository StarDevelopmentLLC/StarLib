package com.stardevllc.starlib.variable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class defines the variables for some representation of a text or format. Up to the user of this class to determine what that is for.
 */
public class VariableHandler {
    
    private final Map<String, Variable> variables = new HashMap<>();
    
    public VariableHandler add(Variable variable) {
        this.variables.put(variable.id(), variable);
        return this;
    }
    
    public VariableHandler add(String id, char openingChar, char closingChar, String description) {
        add(new Variable(id, openingChar, closingChar, description));
        return this;
    }

    public VariableHandler add(String id, char openingChar, char closingChar) {
        add(new Variable(id, openingChar, closingChar, ""));
        return this;
    }
    
    public Variable getVariable(String id) {
        return variables.get(id);
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }
    
    public String replaceVariables(String text, VariableReplacements replacements) {
        return replacements.replaceVariables(text, this);
    }
}