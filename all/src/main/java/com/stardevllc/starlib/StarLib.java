package com.stardevllc.starlib;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class StarLib {
    public static Logger createLogger(Class<?> loggerBaseClass) {
        Logger logger = Logger.getLogger(loggerBaseClass.getName());
        logger.setUseParentHandlers(false);
        logger.addHandler(new StreamHandler(System.out, new Formatter(loggerBaseClass.getName())));
        return logger;
    }

    public static void main(String[] args) {
        
    }

    public static class Formatter extends SimpleFormatter {

        private String name;

        public Formatter(String name) {
            this.name = name;
        }

        @Override
        public String format(LogRecord record) {
            Instant instant = record.getInstant();
            LocalDateTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return time.getMonthValue() + "/" + time.getDayOfMonth() + "/" + time.getYear() + " " +
                    time.getHour() + ":" + time.getMinute() + ":" + time.getSecond()
                    + " " + record.getLevel().getName() +
                    " [" + name + "] " + record.getMessage() + "\n";
        }
    }
}