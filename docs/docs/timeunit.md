# TimeUnit Enum

_Disclaimer: I know that Java has a built-in enum for this. However, if you read the Motivation, you can probably understand why I created this._
## Motivation
The main motivation behind this enum is to provide a consistent and predictable time for each unit and it has also been tailored to fit a 365 day year as well as add values for Weeks, Months and Years.  
This class also provides an easy way to make aliases for each unit for use in other classes.
This is used in other libraries/projects extensively.
## Usage
This is pretty similar to Java's TimeUnit class, however they are not interchangeable and you may get weird results if you switch between them.  
All Libraries from Star Development LLC will use this TimeUnit enum instead of Java's, however they are both ultimately using the Epoc Milliseconds, calculations will be different. 