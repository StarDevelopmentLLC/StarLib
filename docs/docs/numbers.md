---
hide:
  - navigation
  - toc
---

# Numbers Class
This is just a simple class with static methods to parse numbers into their primitves.  
It will check to see if the value is a wrapper class and return the `<type>Value()` method for it. Or it will just parse it and silence any exceptions.
Zero (0) is the default return value.  
I will probably be adding other number based utilities as I run into needs for them.  
The main benefit is to just catch the exceptions and return 0.
```java 
int i = Numbers.toInt("10");
```