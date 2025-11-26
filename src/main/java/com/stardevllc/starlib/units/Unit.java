package com.stardevllc.starlib.units;

public interface Unit {
    
    /**
     * Amount in base
     *
     * @return Amount in base
     */
    double getAmountInBase();
    
    /**
     * Aliases
     *
     * @return aliases
     */
    String[] getAliases();
}