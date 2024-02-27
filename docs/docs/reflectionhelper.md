---
hide:
  - toc
---

# ReflectionHelper class
This class is focused on getting fields and methods from classes recursively through their parent classes.
This class does not cache any of the results (yet), so the calls are pretty expensive, it is recommended to store the results of the methods in a variable or field if you want to use it multiple times.
There are both singular and plural ways to retrieve fields and methods. This class is used within the StarData library and is extremely useful for that use case. I will probably be adding more methods to this helper class, and adding a cache at some point in a future update.