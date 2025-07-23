package com.stardevllc.starlib.calculator;

import com.stardevllc.starlib.helper.NumberHelper;

@Deprecated
public final class SimpleCalculator {
    @Deprecated
    public static Number min(Number left, Number right) {
        return NumberHelper.min(left, right);
    }
    
    @Deprecated
    public static Number max(Number left, Number right) {
        return NumberHelper.max(left, right);
    }
    
    @Deprecated
    public static Number negate(Number number) {
        return NumberHelper.negate(number);
    }
    
    @Deprecated
    public static Number add(Number left, Number right) {
        return NumberHelper.add(left, right);
    }
    
    @Deprecated
    public static Number subtract(Number left, Number right) {
        return NumberHelper.subtract(left, right);
    }
    
    @Deprecated
    public static Number multiply(Number left, Number right) {
        return NumberHelper.multiply(left, right);
    }
    
    @Deprecated
    public static Number divide(Number left, Number right) {
        return NumberHelper.divide(left, right);
    }
}
