# Home

Welcome to the StarLib Wiki.  
This is your guide in using the StarLib library in your projects.

## Overview
StarLib is built from a collection of utilities and classes from past projects. There is no specific theme to the library except for having some lightweight utilities and features for ease of development. 

## Installation
It is best to use a build tool like Maven or Gradle, however you can add it however you wish. This section will only cover Maven and Gradle though.  
When using Maven or Gradle, you will need to use a plugin to shade this library into your final Jar file.  
Maven uses the `maven-shade` plugin  
Gradle uses the `shadow` plugin  
Once you install it using the method of your choice, click the tabs above to continue. There is not a single entry point to this library and it depends on what you want to do with it.

### Maven
In your repositories tag in you pom.xml add this:  
```xml
<repository>
    <id>jitpack.io</id>
	<url>https://www.jitpack.io</url>
</repository>
```
Then in your dependencies tag add this:  
```xml
<dependency>
    <groupId>com.github.StarDevelopmentLLC</groupId>
    <artifactId>StarLib</artifactId>
    <version>Tag</version>
</dependency>
```
### Gradle
In your repositories block in your Gradle Build Script, you need to add this: 
```groovy
maven { url 'https://www.jitpack.io' }
```
Then in your dependencies block you need to add this:
```groovy
implementation 'com.github.StarDevelopmentLLC:StarLib:1.0.0-alpha.1'
```
You can change `implemenation` to whatever you need to based on your needs.