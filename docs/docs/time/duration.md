# Duration Class
This is a class that is designed to represent durations.  
This class does use the [TimeUnit](timeunit.md) class for determining the length of units. This is a custom class in StarLib.  

## Creation
To create a `Duration` simply instantiate the constructor with a TimeUnit and a length
```java
Duration duration = new Duration(TimeUnit, length);
```

## Usage
There are a range of arithmetic methods that you can call that acts on the actual value of the duration.  
Just simply call these methods to perform that arithmetic operation. Each of these methods return the Duration instance in a Builder pattern to allow much easier chains.  
Then you can call the `getTime()` method to get the actual time of the duration.