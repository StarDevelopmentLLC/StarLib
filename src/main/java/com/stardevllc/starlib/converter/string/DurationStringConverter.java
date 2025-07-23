package com.stardevllc.starlib.converter.string;

import com.stardevllc.starlib.time.Duration;
import com.stardevllc.starlib.time.TimeUnit;

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
