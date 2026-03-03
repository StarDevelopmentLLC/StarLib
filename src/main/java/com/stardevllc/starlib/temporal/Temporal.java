package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.serialization.StarSerializable;
import com.stardevllc.starlib.time.TimeUnit;

import java.text.DecimalFormat;
import java.util.Map;

public interface Temporal extends Comparable<Temporal>, Cloneable, StarSerializable {

    DecimalFormat format = new DecimalFormat("###,###,###,###,###,###.####");
    
    TimeValue getTime();
    
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

    default Temporal set(long time) {
        getTime().set(time);
        return this;
    }
    
    default Temporal set(Temporal temporal, Temporal temporals) {
        getTime().set(0);
        add(temporal, temporals);
        return this;
    }
    
    default Temporal set(Temporal temporal) {
        if (temporal == null) {
            getTime().set(0);
        } else {
            getTime().set(temporal.getTime().getTime());
        }
        return this;
    }
    
    default boolean isEmpty() {
        return getTime().getTime() == 0;
    }
    
    default boolean isNotEmpty() {
        return !isEmpty();
    }
    
    @Override
    default Map<String, Object> serialize() {
        return Map.of("timevalue", getTime());
    }
}
