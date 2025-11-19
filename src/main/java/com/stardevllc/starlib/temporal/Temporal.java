package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.text.DecimalFormat;

public interface Temporal extends Comparable<Temporal>, Cloneable {

    DecimalFormat format = new DecimalFormat("###,###,###,###,###,###.####");
    
    TimeValue getTime();
    
    default long getYear() {
        return getTime().getYear();
    }
    
    default long getTimeOfYear() {
        return getTime().getTimeOfYear();
    }
    
    Temporal clone();
    
    String toString();
    
    @Override
    default int compareTo(Temporal o) {
        return getTime().compareTo(o.getTime());
    }

    default boolean equalTo(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTime().equals(temporal.getTime());
    }
    
    default boolean lessThan(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTime().lessThan(temporal.getTime());    
    }
    
    default boolean lessThanOrEqualTo(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTime().lessThanOrEqualTo(temporal.getTime());
    }
    
    default boolean greaterThan(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTime().greaterThan(temporal.getTime());
    }
    
    default boolean greaterThanOrEqualTo(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTime().greaterThanOrEqualTo(temporal.getTime());
    }
    
    default Temporal add(long milliseconds) {
        getTime().add(milliseconds);
        return this;
    }
    
    default Temporal add(TimeUnit unit, long amount) {
        getTime().add(unit, amount);
        return this;
    }
    
    default Temporal add(Temporal temporal, Temporal... temporals) {
        getTime().add(temporal.getTime());
        if (temporals != null) {
            for (Temporal t : temporals) {
                getTime().add(t.getTime());
            }
        }
        
        return this;
    }
    
    default Temporal subtract(long seconds) {
        getTime().subtract(seconds);
        return this;
    }
    
    default Temporal subtract(TimeUnit unit, long amount) {
        getTime().subtract(unit, amount);
        return this;
    }
    
    default Temporal subtract(Temporal temporal, Temporal... temporals) {
        getTime().subtract(temporal.getTime());
        if (temporals != null) {
            for (Temporal t : temporals) {
                getTime().subtract(t.getTime());
            }
        }
        
        return this;
    }

    default Temporal addYears(long years) {
        getTime().addYears(years);
        return this;
    }

    default Temporal subtractYears(long years) {
        getTime().subtractYears(years);
        return this;
    }

    default Temporal set(long year, long timeOfYear) {
        getTime().setYear(year);
        getTime().setYear(timeOfYear);
        return this;
    }
    
    default Temporal set(Temporal temporal, Temporal temporals) {
        getTime().setYear(0);
        getTime().setTimeOfYear(0);
        add(temporal, temporals);
        return this;
    }
    
    default Temporal set(Temporal temporal) {
        if (temporal == null) {
            getTime().setYear(0);
            getTime().setTimeOfYear(0);
        } else {
            getTime().setYear(temporal.getTime().getYear());
            getTime().setTimeOfYear(temporal.getTime().getTimeOfYear());
        }
        return this;
    }
    
    default boolean isEmpty() {
        return getTime().getYear() == 0 && getTime().getTimeOfYear() == 0;
    }
    
    default boolean isNotEmpty() {
        return !isEmpty();
    }
}
