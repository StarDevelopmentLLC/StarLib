package com.stardevllc.starlib.variable;

import java.util.Objects;

/**
 * This represents a variable within a replacement structure
 */
public class Variable {
    private final String id;
    private final char openingChar;
    private final char closingChar;
    private Object defaultValue;
    private String description;

    /**
     * @param id           This is used for the text between the opening and closing chars, and used for internal identification
     * @param openingChar  The opening character used. This should be a non-alphanumeric charactar
     * @param closingChar  The closing character used. This should be a non-alphanumeric character
     * @param defaultValue The default value of this variable
     * @param description  Just a description for help or documentation purposes (Like for a config)
     */
    public Variable(String id, char openingChar, char closingChar, Object defaultValue, String description) {
        this.id = id;
        this.openingChar = openingChar;
        this.closingChar = closingChar;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    /**
     * @param id          This is used for the text between the opening and closing chars, and used for internal identification
     * @param openingChar The opening character used. This should be a non-alphanumeric charactar
     * @param closingChar The closing character used. This should be a non-alphanumeric character
     * @param description Just a description for help or documentation purposes (Like for a config)
     */
    public Variable(String id, char openingChar, char closingChar, String description) {
        this(id, openingChar, closingChar, null, description);
    }

    /**
     * @param id          This is used for the text between the opening and closing chars, and used for internal identification
     * @param openingChar The opening character used. This should be a non-alphanumeric charactar
     * @param closingChar The closing character used. This should be a non-alphanumeric character
     */
    public Variable(String id, char openingChar, char closingChar) {
        this(id, openingChar, closingChar, "");
    }

    public Variable(String id, VCharSet charSet, String description) {
        this(id, charSet.opening(), charSet.closing(), description);
    }

    public Variable(String id, VCharSet charSet) {
        this(id, charSet.opening(), charSet.closing(), "");
    }

    @Override
    public String toString() {
        return openingChar + id + closingChar;
    }

    public String getId() {
        return id;
    }

    public char getOpeningChar() {
        return openingChar;
    }

    public char getClosingChar() {
        return closingChar;
    }

    public String getDescription() {
        return description;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Variable) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openingChar, closingChar, description);
    }
}