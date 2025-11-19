package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.math.BigInteger;

/**
 * This class represents a time value and has all of the math associated with it. 
 */
public final class TimeValue implements Comparable<TimeValue>, Cloneable {
    
    public static final long MILLISECONDS_IN_YEAR = TimeUnit.YEARS.getMsPerUnit();
    public static final BigInteger MILLISECONDS_IN_YEAR_BIG = BigInteger.valueOf(MILLISECONDS_IN_YEAR);
    
    private long year;
    private long timeOfYear;

    public TimeValue() {}

    public TimeValue(long year, long timeOfYear) {
        this.year = year;
        this.timeOfYear = timeOfYear;
    }
    
    public TimeValue(BigInteger value) {
        BigInteger[] result = value.divideAndRemainder(MILLISECONDS_IN_YEAR_BIG);
        this.year = result[0].longValue();
        this.timeOfYear = result[1].longValue();
    }
    
    public TimeValue divide(long divisor) {
        long year = this.year / divisor;
        long timeOfYear = this.timeOfYear / divisor;
        
        return new TimeValue(year, timeOfYear);
    }
    
    public TimeValue divide(TimeValue divisor) {
        BigInteger currentValue = toBigInteger();
        BigInteger divisorValue = divisor.toBigInteger();
        
        BigInteger quotient = currentValue.divide(divisorValue);
        return new TimeValue(quotient);
    }
    
    public TimeValue multiply(double factor) {
        double rawYear = this.year * factor;
        long wholeYears = (long) rawYear;
        double factionalYears = rawYear - wholeYears;
        double rawTimeOfYear = this.timeOfYear * factor;
        
        wholeYears += (long) rawTimeOfYear / MILLISECONDS_IN_YEAR;
        rawTimeOfYear %= MILLISECONDS_IN_YEAR;
        
        return new TimeValue(wholeYears, (long) (rawTimeOfYear + MILLISECONDS_IN_YEAR * factionalYears));
    }
    
    public BigInteger toBigInteger() {
        return BigInteger.valueOf(this.year).multiply(MILLISECONDS_IN_YEAR_BIG).add(BigInteger.valueOf(this.timeOfYear));
    }
    
    public void addYears(long years) {
        this.year += years;
    }
    
    public void subtractYears(long years) {
        this.year -= years;
    }

    public long getYear() {
        return year;
    }

    public long getTimeOfYear() {
        return timeOfYear;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public void setTimeOfYear(long timeOfYear) {
        this.timeOfYear = timeOfYear;
    }

    public boolean equals(Object other) {
        if (other instanceof TimeValue instant) {
            return this.year == instant.year && this.timeOfYear == instant.timeOfYear;
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = Long.hashCode(year);
        result = 31 * result + Long.hashCode(timeOfYear);
        return result;
    }
    
    @Override
    public int compareTo(TimeValue other) {
        if (other == null) {
            return 1;
        }
        
        int yearCompare = Long.compare(this.year, other.year);
        if (yearCompare != 0) {
            return yearCompare;
        }
        
        return Long.compare(this.timeOfYear, other.timeOfYear);
    }
    
    @Override
    public TimeValue clone() {
        return new TimeValue(this.year, this.timeOfYear);
    }

    public void add(TimeValue other) {
        this.year += other.year;
        this.add(other.timeOfYear);
    }
    
    public void subtract(TimeValue other) {
        this.year -= other.year;
        this.subtract(other.timeOfYear);
    }
    
    public void add(TimeUnit unit, long amount) {
        this.add((long) unit.toSeconds(amount));
    }
    
    public void subtract(TimeUnit unit, long amount) {
        this.subtract((long) unit.toSeconds(amount));
    }
    
    public void add(long milliseconds) {
        this.timeOfYear += milliseconds;
        
        if (this.timeOfYear < MILLISECONDS_IN_YEAR) {
            return;
        }
        
        this.year += this.timeOfYear / MILLISECONDS_IN_YEAR;
        this.timeOfYear = this.timeOfYear % MILLISECONDS_IN_YEAR;
    }
    
    public void subtract(long milliseconds) {
        if (this.timeOfYear >= milliseconds) {
            this.timeOfYear -= milliseconds;
            return;
        }

        this.year -= milliseconds / MILLISECONDS_IN_YEAR; 
        milliseconds -= milliseconds / MILLISECONDS_IN_YEAR;
        
        if (this.timeOfYear >= milliseconds) {
            this.timeOfYear -= milliseconds;
            return;
        }
        
        this.year--;
        this.timeOfYear += MILLISECONDS_IN_YEAR;
        this.timeOfYear -= milliseconds;
        
        //This ensures that nothing goes to the negative
        this.year = Math.max(0, this.year);
        this.timeOfYear = Math.max(0, this.timeOfYear);
    }
    
    public boolean lessThan(TimeValue other) {
        if (other == null) {
            return false;
        }
        
        if (this.year < other.year) {
            return true;
        } else if (this.year > other.year) {
            return false;
        }
        
        return this.timeOfYear < other.timeOfYear;
    }
    
    public boolean lessThanOrEqualTo(TimeValue other) {
        if (other == null) {
            return false;
        }
        
        if (equals(other)) {
            return true;
        }

        return lessThan(other);
    }

    public boolean greaterThan(TimeValue other) {
        if (other == null) {
            return false;
        }

        if (this.year > other.year) {
            return true;
        } else if (this.year < other.year) {
            return false;
        }

        return this.timeOfYear > other.timeOfYear;
    }
    
    public boolean greaterThanOrEqualTo(TimeValue other) {
        if (other == null) {
            return false;
        }

        if (equals(other)) {
            return true;
        }

        return greaterThan(other);
    }

    @Override
    public String toString() {
        return "TimeValue{" + "year=" + year +
                ", timeOfYear=" + timeOfYear +
                '}';
    }
}
