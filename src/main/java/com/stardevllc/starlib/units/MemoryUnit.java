package com.stardevllc.starlib.units;

import com.stardevllc.starlib.converter.string.EnumStringConverter;
import com.stardevllc.starlib.converter.string.StringConverters;

public enum MemoryUnit {
    BYTE(1L),
    
    // Binary (IEC - base 1024)
    KIBIBYTE(1024L),
    MEBIBYTE(1048576L),
    GIBIBYTE(1073741824L),
    TEBIBYTE(1099511627776L),
    PEBIBYTE(1125899906842624L),
    EXBIBYTE(1152921504606846976L),
    
    // Decimal (SI - base 1000)
    KILOBYTE(1_000L),
    MEGABYTE(1_000_000L),
    GIGABYTE(1_000_000_000L),
    TERABYTE(1_000_000_000_000L),
    PETABYTE(1_000_000_000_000_000L),
    EXABYTE(1_000_000_000_000_000_000L);
    
    static {
        StringConverters.addConverter(MemoryUnit.class, new EnumStringConverter<>(MemoryUnit.class));
    }
    
    private final long amountInBase;
    private final String[] aliases;
    
    MemoryUnit(long amountInBase, String... aliases) {
        this.amountInBase = amountInBase;
        this.aliases = aliases;
    }
    
    public long toBytes(long amount) {
        return Math.multiplyExact(amount, amountInBase);
    }
    
    public long toBytes(double amount) {
        return Math.round(amount * amountInBase);
    }
    
    public double fromBytes(long bytes) {
        return (double) bytes / amountInBase;
    }
    
    public long fromBytesExact(long bytes) {
        return bytes / amountInBase;
    }
    
    public long getAmountInBase() {
        return amountInBase;
    }
    
    public String[] getAliases() {
        return aliases;
    }
}
