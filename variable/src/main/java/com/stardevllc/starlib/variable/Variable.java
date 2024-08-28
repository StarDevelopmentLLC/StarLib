package com.stardevllc.starlib.variable;

/**
 * This represents a variable within a replacement structure
 * 
 * @param id This is used for the text between the opening and closing chars, and used for internal identification
 * @param openingChar The opening character used. This should be a non-alphanumeric charactar
 * @param closingChar The closing character used. This should be a non-alphanumeric character
 * @param description Just a description for help or documentation purposes (Like for a config)
 */
public record Variable(String id, char openingChar, char closingChar, String description) {

    @Override
    public String toString() {
        return openingChar + id + closingChar;
    }
}