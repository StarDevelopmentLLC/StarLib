---
hide:
  - navigation
  - toc
---

# Registry Classes
This class provides a way to "register" objects with a key. Yes, there is the Java Maps, and it uses a TreeMap. But this class provides a way to "normalize" or convert keys into a specific format to ensure they are all the same. The keys must implement the Comparable interface.
This class does implement the Iterable interface and it does it on the values, so you can use a reference direclty in for-loops without calling intermediate methods to get a collection of values.
There are also default implementations for Java Primitives (wrappers) as well as Strings and UUIDs.
I do plan on extending this framework a bit for the future, but this works for now. This is used with StarData for the DatabaseRegistry class.