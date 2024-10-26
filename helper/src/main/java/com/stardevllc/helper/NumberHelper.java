package com.stardevllc.helper;

import java.util.Random;

public final class NumberHelper {
    private NumberHelper() {}

    public static String getNumberSuffix(long number) {
        if (number == 1) {
            return "1st";
        } else if (number == 2) {
            return "2nd";
        } else if (number == 3) {
            return "3rd";
        } else {
            return number + "th";
        }
    }
    
    public static int randomInRange(int min, int max) {
        return randomInRange(new Random(), min, max);
    }

    public static int randomInRange(Random random, int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static int toInt(Object object) {
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }

        try {
            return Integer.parseInt(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
        }
        return 0;
    }

    public static float toFloat(Object object) {
        if (object instanceof Number) {
            return ((Number) object).floatValue();
        }

        try {
            return Float.parseFloat(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
        }
        return 0;
    }

    public static double toDouble(Object object) {
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }

        try {
            return Double.parseDouble(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
        }
        return 0;
    }

    public static long toLong(Object object) {
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }

        try {
            return Long.parseLong(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
        }
        return 0;
    }

    public static short toShort(Object object) {
        if (object instanceof Number) {
            return ((Number) object).shortValue();
        }

        try {
            return Short.parseShort(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
        }
        return 0;
    }

    public static byte toByte(Object object) {
        if (object instanceof Number) {
            return ((Number) object).byteValue();
        }

        try {
            return Byte.parseByte(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
        }
        return 0;
    }
}