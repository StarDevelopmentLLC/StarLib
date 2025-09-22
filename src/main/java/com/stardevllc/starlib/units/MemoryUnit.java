package com.stardevllc.starlib.units;

import com.stardevllc.starlib.converter.string.EnumStringConverter;
import com.stardevllc.starlib.converter.string.StringConverters;

/**
 * Represents units of computer memory
 */
public enum MemoryUnit {
    
    /**
     * Base unit (8 bits technically)
     */
    BYTE(1, "b", "bytes"),
    
    /**
     * Byte * 1,024
     */
    KILOBYTE(1024, "kb", "kilobytes"),
    
    /**
     * Byte * 1,048,576
     */
    MEGABYTE(1048576, "mb", "megabytes"),
    
    /**
     * Byte * 1,073,741,824
     */
    GIGABYTE(1073741824, "gb", "gigabytes"),
    
    /**
     * Byte * 1,099,511,627,776
     */
    TERABYTE(1099511627776L, "tb", "terabytes"),
    
    /**
     * Byte * 1,125,899,906,842,624
     */
    PETABYTE(1125899906842624L, "pb", "petabytes");
    
    static {
        StringConverters.addConverter(MemoryUnit.class, new EnumStringConverter<>(MemoryUnit.class));
    }
    
    private final long amountInBytes;
    private final String[] aliases;
    
    MemoryUnit(long amountInBytes, String... aliases) {
        this.amountInBytes = amountInBytes;
        this.aliases = aliases;
    }
    
    /**
     * Amount in Bytes (or base unit)
     *
     * @return Amount in bytes
     */
    public long getAmountInBytes() {
        return amountInBytes;
    }
    
    /**
     * Aliases
     *
     * @return Aliases
     */
    public String[] getAliases() {
        return aliases;
    }
    
    /**
     * Converts to bytes
     *
     * @param amount The maount
     * @return The converted value
     */
    public long toBytes(long amount) {
        return amountInBytes * amount;
    }
    
    /**
     * Converts to kilobytes
     *
     * @param amount The maount
     * @return The converted value
     */
    public double toKilobytes(long amount) {
        return amountInBytes * amount * 1.0 / KILOBYTE.getAmountInBytes();
    }
    
    /**
     * Converts to megabytes
     *
     * @param amount The maount
     * @return The converted value
     */
    public double toMegabytes(long amount) {
        return amountInBytes * amount * 1.0 / MEGABYTE.getAmountInBytes();
    }
    
    /**
     * Converts to gigabytes
     *
     * @param amount The maount
     * @return The converted value
     */
    public double toGigabytes(long amount) {
        return amountInBytes * amount * 1.0 / GIGABYTE.getAmountInBytes();
    }
    
    /**
     * Converts to terabytes
     *
     * @param amount The maount
     * @return The converted value
     */
    public double toTerabytes(long amount) {
        return amountInBytes * amount * 1.0 / TERABYTE.getAmountInBytes();
    }
    
    /**
     * Converts to petabytes
     *
     * @param amount The maount
     * @return The converted value
     */
    public double toPetabytes(long amount) {
        return amountInBytes * amount * 1.0 / PETABYTE.getAmountInBytes();
    }
}
