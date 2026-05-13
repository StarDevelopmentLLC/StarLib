package com.stardevllc.starlib.helper;

import java.lang.reflect.Array;
import java.util.*;

public final class ArrayHelper {
    private ArrayHelper() {}
    
    public static <E> Object[] toArray(Collection<E> collection) {
        Object[] r = new Object[collection.size()];
        Iterator<E> it = collection.iterator();
        for (int i = 0; i < r.length; i++) {
            if (!it.hasNext()) {
                return Arrays.copyOf(r, i);
            }
            r[i] = it.next();
        }
        return it.hasNext() ? ArrayHelper.finishToArray(r, it) : r;
    }
    
    public static <E, T> T[] toArray(Collection<E> collection, T[] a) {
        int size = collection.size();
        T[] r = a.length >= size ? a :
                (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        Iterator<E> it = collection.iterator();
        
        for (int i = 0; i < r.length; i++) {
            if (!it.hasNext()) {
                if (a == r) {
                    r[i] = null;
                } else if (a.length < i) {
                    return Arrays.copyOf(r, i);
                } else {
                    System.arraycopy(r, 0, a, 0, i);
                    if (a.length > i) {
                        a[i] = null;
                    }
                }
                return a;
            }
            r[i] = (T) it.next();
        }
        return it.hasNext() ? ArrayHelper.finishToArray(r, it) : r;
    }
    
    public static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int len = r.length;
        int i = len;
        while (it.hasNext()) {
            if (i == len) {
                len = newLength(len,
                        1,             /* minimum growth */
                        (len >> 1) + 1 /* preferred growth */);
                r = Arrays.copyOf(r, len);
            }
            r[i++] = (T) it.next();
        }
        // trim if overallocated
        return i == len ? r : Arrays.copyOf(r, i);
    }
    
    public static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        // preconditions not checked because of inlining
        // assert oldLength >= 0
        // assert minGrowth > 0
        
        int prefLength = oldLength + Math.max(minGrowth, prefGrowth); // might overflow
        if (0 < prefLength && prefLength <= Integer.MAX_VALUE - 8) {
            return prefLength;
        } else {
            // put code cold in a separate method
            return hugeLength(oldLength, minGrowth);
        }
    }
    
    public static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) { // overflow
            throw new OutOfMemoryError(
                    "Required array length " + oldLength + " + " + minGrowth + " is too large");
        } else {
            return Math.max(minLength, Integer.MAX_VALUE - 8);
        }
    }
}