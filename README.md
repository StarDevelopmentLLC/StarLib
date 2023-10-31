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
    implementation 'com.github.StarDevelopmentLLC:StarLib:1.0.0-SNAPSHOT'
}
```  
You must shade this library in order to properly use it, or have it already on the class-path. Gradle has the Shadow Plugin you can use for this task.  
If you are using Maven, the Maven-Shade plugin works just fine.
