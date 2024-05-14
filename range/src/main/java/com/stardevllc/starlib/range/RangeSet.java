package com.stardevllc.starlib.range;

import java.util.Set;
import java.util.TreeSet;

public class RangeSet<V> {
    protected Set<Range<V>> ranges = new TreeSet<>();

    public RangeSet() {
    }
    
    public boolean add(Range<V> range) {
        if (this.ranges.isEmpty()) {
            this.ranges.add(range);
        }

        for (Range<V> existing : this.ranges) {
            if (existing.contains(range.min()) || existing.contains(range.max())) {
                return false;
            }
        }
        
        ranges.add(range);
        return true;
    }
    
    public Range<V> remove(int index) {
        for (Range<V> range : this.ranges) {
            if (range.contains(index)) {
                ranges.remove(range);
                return range;
            }
        }
        
        return null;
    }
    
    public Range<V> remove(V value) {
        for (Range<V> range : this.ranges) {
            if (range.value().equals(value)) {
                ranges.remove(range);
                return range;
            }
        }
        
        return null;
    }
    
    public V get(int index) {
        for (Range<V> range : this.ranges) {
            if (range.contains(index)) {
                return range.value();
            }
        }
        
        return null;
    }

    @Override
    public String toString() {
        return "RangeSet{" +
                "ranges=" + ranges +
                '}';
    }
}