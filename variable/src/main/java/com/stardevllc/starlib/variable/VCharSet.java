package com.stardevllc.starlib.variable;

public record VCharSet(String name, char opening, char closing) {
    public static final VCharSet BRACES = new VCharSet("braces", '{', '}');
    public static final VCharSet SQUARE_BRACKETS = new VCharSet("square_brackets", '[', ']');
    public static final VCharSet ANGLE_BRACKETS = new VCharSet("angle_brackets", '<', '>');
    public static final VCharSet PARENTHESES = new VCharSet("parentheses", '(', ')');
    public static final VCharSet PERCENT = new VCharSet("percent", '%', '%');
}
