# TimeParser Class
This class takes in a time represented as a String and converts it into a value in milliseconds based on [TimeUnit](timeunit.md)s  
The inverse of this class is the [TimeFormat](timeformat.md) class, if you are looking for that one.

## Parse Time
This method takes in what you would call a "duration" amount and converts it. Pretty much you can have any of the TimeUnits and/or their aliases with the value for the number in front of it and it will convert.  
It should _theoretically (Have not done a lot of testing for this)_ work if the arguments are out of order.  
So this class will take something like `5s` and convert it into `5000` milliseconds. Or convert `1m5s` into `65000` milliseconds etc...
This is mainly focused around user input and is for example, used in parsing temporary punishment time arguments in one of my project's punishment handling for players. 

## Parse Date
This method takes in a date represented in the format `yyyy:MM:dd` with optional `hh:mm:ss` in UTC time zone.  
The Year, Month, Day group must always exist and all 3 must be present. When using the Hour, Minute, Second group, all must be present if used.  
The two groups are separated by a `:`