# TimeFormat Class
This class allows you to take the time in milliseconds and convert it to a customizable, human-readable format.  
The inverse of this class is the [TimeParser](timeparser.md) class, if you are looking for that one.
## Creating a Format
The time format class has a special format handling, all time formats must be enclosed between two `%` signs. This will change in the future, but that is what it is right now. This is more because I was handling the units differently and I have a significant amount of projects that are using how this functions and I am kind of lazy for now. I will probably run both of these in the future and deprecate the old format for eventual removal.
The numbers are formatted using a `DecimalFormat`, so you can use the same variables for digits in the formats as you would in DecimalFormat. As a basic example, using a `#` for a number will mean that the number will only exist there if it goes to that digit. Alternatively using a `0` for a digit will mean that a 0 or the number will exist there. Please see the JavaDocs for your version of the JDK on DecimalFormat.
Our custom format uses this for numbers, but there are some other information that you need to put into the `%`. The only actual requirement is the unit for the time. This can be any of the aliases in the `TimeFormat` class from SDLLC and when formatting, it will use that specific alias as the unit in the formatted String. If you don't specify a unit for a higher denomination of time, it will not put it there when formatting. If you do, the format will take the highest one and derive the lower ones from the remainder of the higher ones.  
For example, take this code below:
```java
long time = TimeUnit.MINUTES.toMillis(2) + TimeUnit.SECONDS.toMillis(10);
TimeFormat timeFormat = new TimeFormat("%00s%");
System.out.println(timeFormat.format(time));
```
This prints out `130s` because we didn't specify we wanted minutes.
Now if we change the format to use `%00m%%00s%`, it will now print out `02m10s`.
Notice the leading `0`? That is because we are using zeros for the digits. We can make it so that it doesn't have a leading 0 by doing this `%#0m%%00s%`, and now the output is `2m10s`. We can do this for every single part of the format.
But what if we want to specify a format for a higher unit, but omit it if it doesnt go that high? Well, we can put a `*` after the first `%` to do just that.  
If we change our code to the following:
```java
long longTime = TimeUnit.MINUTES.toMillis(2) + TimeUnit.SECONDS.toMillis(10);
TimeFormat timeFormat = new TimeFormat("%*#0m%%00s%");
System.out.println("Longer Time: " + timeFormat.format(longTime));
System.out.println("Shorter Time: " + timeFormat.format(TimeUnit.SECONDS.toMillis(10)));
```
We get the following output:
```text
Longer Time: 2m10s
Shorter Time: 10s
```
You can reuse the format, and it is thread-safe to do so. The `format` method's calculations and parsing are entirely contained within the method with some handy things being stored in the class. It also operates on a copy of the stored data, so you can change the format from another thread and it will finish without issues on the previous format. Subsequent calls will use the new one. This is different from DecimalFormat as that one is not thread-safe. The internal DecimalFormat is created on every call to `format()`. 