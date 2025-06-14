package com.stardevllc.converter.string;

import com.stardevllc.time.Duration;
import com.stardevllc.time.TimeUnit;

public class DurationStringConverter implements StringConverter<Duration> {
    protected DurationStringConverter() {
        StringConverters.addConverter(Duration.class, this);
    }
    
    @Override
    public String convertFrom(Object fromObject) {
        if (fromObject instanceof Duration duration) {
            return String.valueOf(duration.get());
        }
        return "0";
    }
    
    @Override
    public Duration convertTo(String toObject) {
        try {
            return new Duration(TimeUnit.MILLISECONDS, Long.parseLong(toObject));
        } catch (Exception e) {}
        
        return new Duration();
    }
}
