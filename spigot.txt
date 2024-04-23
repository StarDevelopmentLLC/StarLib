[COLOR=#ff0000][B]Server Owners, this is not a plugin, it is a developer utility[/B][/COLOR]

This is a general purpose library that is not tied to Minecraft, however it is used within my other projects, including Minecraft Specific projects.

This library needs Java 17 at a minimum.
This library has no other dependencies, just itself.

If you are a plugin developer, I strongly encourage you to depend on [URL='https://www.spigotmc.org/resources/starcore.110550/']StarCore [/URL]as it provides this library already.

[B]Version Information[/B]
This library is very much a work in progress and very unstable. I am constantly adding, changing and removing things within this library. This is based on need in my other projects, of which are also works in progress and constantly changing and evolving. I expect things to stabilize as time goes on.

[B]Installation[/B]
I strongly encourage the use of a build tool like Gradle or Maven. You can find the details needed to fill in what you need in your build tool.
[B]Repository[/B]: [URL]https://www.jitpack.io[/URL]
[B]Group[/B]: com.github.StarDevelopmentLLC
[B]Artifact[/B]: StarLib
[B]Version[/B]: 1.0.0-alpha.14

[B][SIZE=5]Tools, Utilities and Systems[/SIZE][/B]
StarLib provides tools, utilities and systems that have been gathered mostly from my past projects. Links are provided to the source code directly, take a look at the arguments and how things function.

[B]Singleton Classes[/B]
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/CodeGenerator.java']CodeGenerator [/URL]- Allows generating a code made out of letters and numbers. Options are provided in the method parameters
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/Pair.java']Pair [/URL]- A Java Record that holds Key and a Value. This is just like an entry in a Map, but using the Entry class from Map is a bit of a hassle, so this exists. Plans to make it so that you can set the value, but not the key.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/reflection/ReflectionHelper.java']ReflectionHelper [/URL]- A class that makes it easier to search in classes and parent classes for methods and fields. This is recursive and it stops when it finds the method or field, or if the parent class is Object directly. This class also allows retriving a Property from a class using Reflelction. It is planned to add caching to this class.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/helper/FileHelper.java']FileHelper [/URL]- A class that makes working with NIO and paths a bit easier as it silences exceptions and makes it cleaner to do things recursively with directories. Be very careful with the deleteDirectory() method. I do use it for a project that I am working on though.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/helper/StringHelper.java']StringHelper [/URL]- A class that has some utilities to make working with Strings a bit easier. More things will be added as time goes on.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/helper/NumberHelper.java']NumberHelper [/URL]- A class with some number based utilities and helpers to parse numbers, convert to roman numerals, convert member bytes to higher values etc...
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/Operator.java']Operator [/URL]- This is an enum, mainly used for parsing user - input with these kinds of things, used in a couple of other plugins of mine.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/Range.java']Range [/URL]- This is a simple class that represents a value in between two integers (The min and max are both inclusive)


[B]Systems[/B]
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/converter/StringConverter.java']StringConverter [/URL]- This provides an easy way to convert between Strings and Objects. The static getConverter method in StringConverter would be a good way to go here. If you want to add a converter, just add it to the converters Map, providing the Object Class and the Converter Class.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/task/TaskFactory.java']TaskFactory [/URL]- This is an interface that is very similar to Spigots Scheduler, actually, that is what it is based on. There is no default implementation in StarLib, however StarCore provides a Wrapper that uses the Bukkit Scheduler behind the scenes. There are plans to use this within another library, but that is going to be a while.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/task/TaskFactory.java']Registry [/URL]- This is a system that allows you to group similar objects together. Keys MUST implement Comparable. A TreeMap is used as the backing collection. Two functional interfaces are provided, Normalizer allows you to specify how keys are handled before they are put into the map. This is mostly used in other projects with Strings to preprocess them. The Register interface allows you to use the register(Object) instead of the register(Key, Object) method, just tells the registry how to get the key. The registry class implements the Iterable interface, meaning you can use the registry itself in a for loop without having to call the getObjects() method.

[B]Time Utilities[/B]
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/time/TimeUnit.java']TimeUnit [/URL]- This is the base class for the other utilites within the time package. It does provide a tick value for working with Minecraft based times. It also provides values for aliases, which these are used within the TimeParser and TimeFormat classes. From Milliseconds to Days, these are direct calculations without using an average. From Weeks to Years, these are based on the Average for that type based on a 365 day year. This is used for accuracy and consistency at larger values.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/time/Duration.java']Duration [/URL]- This class just allows you to do arithmetic operations using the TimeUnit class without having to convert it yourself all the time. Floating Point precision is lost though as the base value is a long.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/time/TimeParser.java']TimeParser [/URL]- This class uses the TimeUnit values to take in times as "#(unit)" where # is the number and (unit) is the unit or abreviation of the unit. This does support chained units, but the order does matter. For example the String "1m40s" will be parsed into the millisecond value for 1 minute + 40 seconds.
[URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/time/TimeFormat.java']TimeFormat [/URL]- This class allows you to take a time in milliseconds and convert it into a human-readable format. This is NOT a date. The numbers themselves use a [URL='https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/text/DecimalFormat.html']DecimalFormat[/URL]. In order to configure, each unit denomination needs to be enclosed with %%.
%Nu% where "N" is the number format based on DecimalFormat, this can be any number of digits and where "u" is the unit, this can be any of the aliases of the unit. The TimeFormat will use the alias of the unit that was used between the "%" signs in the final format. You can place a "*" within the percent signs. This tells the formatter that if there are no values for that unit, skip that in the final format.
- Consider the format %00m%%00s%. This will format 5000 ms as 00m05s. It will format 1 min and 30 seconds as 01m30s.
- Consider the format %*00m%%00s%. This will format 5000 ms as 05s and 1min and 30 seconds as 01m30s.

[B]Observables and Properties[/B]
This system is take from JavaFX Beans and adapted for a standalone system. I have also made some heavy modifications to things, mainly about removing the large amounts of abstract classes and simplifying the system itself, while maintaining functionality.
The best way to use this system is by using a [URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/observable/property/ReadOnlyProperty.java']ReadOnlyProperty [/URL]and a [URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/observable/property/Property.java']Property[/URL].  There are default implementations for boolean, double, float, int, long, String and UUID. Supported Collections are List, Set and Map.
There is a generic ObjectProperty that wraps an object.
You can add ChangeListeners to detect changes in the value stored in the property (This is from the [URL='https://github.com/StarDevelopmentLLC/StarLib/blob/main/src/main/java/com/stardevllc/starlib/observable/value/ObservableValue.java']ObservableValue [/URL]class). You can also bind properties together, either one directionally, or bidirectionally. ReadOnlyProperties cannot be bound to another value, but another value can be bound to it.
If you want to create your own properties, it is best to extend from one of the concrete properties than otherwise. However you don't have to extend from them, interfaces are there for that reason, the system will work as long as you implement the correct Interfaces.
Lets look at the properties to have an example
ReadOnlyProperty effectively extends from ObservableValue
Property extends ReadOnlyProperty and also extends WritableValue
The concrete property classes effectively extends from WritableXValue and ObservableXValue
Just implement these interfaces and you are good to go, things will work.

[B]HTTP API[/B]
Just a simple set of classes for post and get http requests. 

[I]This Library is provided under the MIT Open-Source License and will always remain that way. You can find the source code [/I][URL='https://github.com/StarDevelopmentLLC/StarLib'][I]here on GitHub[/I][/URL][I]. If you find that this library is posted anywhere else that is not by me (Firestar311), I recommend that you do not download from there. You can always find the most recent, official version on the GitHub, completely free of charge. Please consider supporting development by using GitHub Sponsors.[/I]