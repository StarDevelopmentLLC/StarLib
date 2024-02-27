---
hide:
  - toc
---

# URLClassLoaderAccess class

This class provides a way to use the `URLClassLoader#add(URL)` method as it is protected by default. This was created for use with Spigot Plugin Class Loaders to add jar files to the class-path at run-time.  
This class will use Reflection as the first way of modification as it is the safest way to it.  
If it cannot use Reflection, it will try to use the Unsafe class to do it.  
If either of these methods do not work, then it just can't do anything and will throw `UnsupportedOperationException`s.  
Use the static `create(URLClassLoader)` method to instantiate the class and it will automatically do what it needs to do.  
In most cases, the reflection method should not fail unless something is using a SecurityManager (Or equivilent).  
Then in those cases, the Unsafe way of doing works pretty well. And I have not yet had it fail. But I am sure it will at some point as these Java Modules are a pain for reflection. 