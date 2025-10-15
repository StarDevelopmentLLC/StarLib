package com.stardevllc.starlib.converter.string;

import com.stardevllc.starlib.time.Duration;
import com.stardevllc.starlib.time.TimeUnit;

/**
 * Converts between Durations and Strings
 */
public class DurationStringConverter implements StringConverter<Duration> {
    
    /**
     * Constructs a new DurationStringConverter
     */
    protected DurationStringConverter() {
        StringConverters.addConverter(Duration.class, this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String convertFrom(Duration fromObject) {
        if (fromObject instanceof Duration duration) {
            return String.valueOf(duration.get());
        }
        return "0";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Duration convertTo(String toObject) {
        try {
            return new Duration(TimeUnit.MILLISECONDS, Long.parseLong(toObject));
        } catch (Exception e) {}
        
        return new Duration();
    }
}
