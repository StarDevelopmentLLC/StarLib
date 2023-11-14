# StarLib
A general-purpose Java Library that provides simple utilities

[![](https://www.jitpack.io/v/StarDevelopmentLLC/StarLib.svg)](https://www.jitpack.io/#StarDevelopmentLLC/StarLib)
## To use this Library
You must add JitPack as a repo, below is for Gradle
```groovy
repositories {
    maven {
        url = 'https://www.jitpack.io'
    }
}
```  
Then to use this library as a dependency
```goovy
dependencies {
    implementation 'com.github.StarDevelopmentLLC:StarLib:1.0.0-alpha.1'
}
```  
You must shade this library in order to properly use it, or have it already on the class-path. Gradle has the Shadow Plugin you can use for this task.  
If you are using Maven, the Maven-Shade plugin works just fine.

## Official Downloads
This list serves as a way to double check to see if a download/post is officially supported. The list below is subject to change and lists only official releases of this Library
- [GitHub Releases](https://github.com/StarDevelopmentLLC/StarLib/releases)
- [SpigotMC](https://www.spigotmc.org/resources/starlib.106562/)
- [Paper Hangar](https://hangar.papermc.io/Firestar311/StarLib)
- [BuiltByBit (Formerly MCMarket)](https://builtbybit.com/resources/starlib.34903/)
