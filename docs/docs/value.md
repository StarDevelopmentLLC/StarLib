---
hide:
  - navigation
  - toc
---

# Value Class

_Disclaimer: There is probably a better way to design the need for this class, but I can't think of one. It is here for you to use if you wish. Suggestions are welcome._

## Motivation
This class came out of a need for storing a variety of possible types in a MySQL Database without creating a table for each type as they were all stored in the same class anyways. This is the Stats System from The Nexus Reborn (You can find the Organization on my GitHub profile). I could have probably done it differently, but then I wouldn't get the full benefit from using JDBC if I just store things as text/varchars and waste the database and also have to parse the values from strings anyways. If you have a better way of doing this without parsing to and from Strings/Varchars, please create an Issue or Pull-Request (Issue preferable).

## Usage
This class is pretty simple as the main support is in the StarData integration, but even then, it isn't too bad. You just instantiate this class providing the `Type` enum and a value. There is only supported types though, and they revolve around Java Primitives.
```java
Value value = new Value(Type.Integer, 20);
```
There are methods to parse the value into different ones if there is a way to do it. For example, you can convert a number to a String, and a String to a number if it is a valid number. So there is a some utility to it. I may add support for more types and maybe even custom types. But we will see. 