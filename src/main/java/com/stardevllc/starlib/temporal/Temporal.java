package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.serialization.StarSerializable;
import com.stardevllc.starlib.time.TimeUnit;

import java.text.DecimalFormat;
import java.util.Map;

public interface Temporal extends Comparable<Temporal>, Cloneable, StarSerializable {

    DecimalFormat format = new DecimalFormat("###,###,###,###,###,#00.####");
    
    TimeValue getTimeValue();
    
    Temporal clone();
    
    String toString();
    
    default long getMillis() {
        return getTimeValue().getTime();
    }
    
    @Override
    default int compareTo(Temporal o) {
        return getTimeValue().compareTo(o.getTimeValue());
    }

    default boolean equalTo(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTimeValue().equals(temporal.getTimeValue());
    }
    
    default boolean lessThan(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTimeValue().lessThan(temporal.getTimeValue());    
    }
    
    default boolean lessThanOrEqualTo(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTimeValue().lessThanOrEqualTo(temporal.getTimeValue());
    }
    
    default boolean greaterThan(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTimeValue().greaterThan(temporal.getTimeValue());
    }
    
    default boolean greaterThanOrEqualTo(Temporal temporal) {
        if (temporal == null) {
            return false;
        }
        return getTimeValue().greaterThanOrEqualTo(temporal.getTimeValue());
    }
    
    default Temporal add(long milliseconds) {
        getTimeValue().add(milliseconds);
        return this;
    }
    
    default Temporal add(TimeUnit unit, long amount) {
        getTimeValue().add(unit, amount);
        return this;
    }
    
    default Temporal add(Temporal temporal, Temporal... temporals) {
        getTimeValue().add(temporal.getTimeValue());
        if (temporals != null) {
            for (Temporal t : temporals) {
                getTimeValue().add(t.getTimeValue());
            }
        }
        
        return this;
    }
    
    default Temporal subtract(long seconds) {
        getTimeValue().subtract(seconds);
        return this;
    }
    
    default Temporal subtract(TimeUnit unit, long amount) {
        getTimeValue().subtract(unit, amount);
        return this;
    }
    
    default Temporal subtract(Temporal temporal, Temporal... temporals) {
        getTimeValue().subtract(temporal.getTimeValue());
        if (temporals != null) {
            for (Temporal t : temporals) {
                getTimeValue().subtract(t.getTimeValue());
            }
        }
        
        return this;
    }

    default Temporal set(long time) {
        getTimeValue().set(time);
        return this;
    }
    
    default Temporal set(Temporal temporal, Temporal temporals) {
        getTimeValue().set(0);
        add(temporal, temporals);
        return this;
    }
    
    default Temporal set(Temporal temporal) {
        if (temporal == null) {
            getTimeValue().set(0);
        } else {
            getTimeValue().set(temporal.getTimeValue().getTime());
        }
        return this;
    }
    
    default boolean isEmpty() {
        return getTimeValue().getTime() == 0;
    }
    
    default boolean isNotEmpty() {
        return !isEmpty();
    }
    
    @Override
    default Map<String, Object> serialize() {
        return Map.of("timevalue", getTimeValue());
    }
}
