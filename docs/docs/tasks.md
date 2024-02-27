---
hide: 
  - toc
  - navigation
---

# Tasks and TaskScheduler
This is an abstraction to allow libraries to use a Bukkit-Like scheduler/task factory and allow support for this as well to prevent issues within Bukkit/Spigot/Paper plugins. StarMCLib has a default implementation of this class.
To create a default Java Implementation, just use Threads and the Java Timer classes.
See the implementation(s) of these classes for how you should use it. The methods hava JavaDocs to expain what they do