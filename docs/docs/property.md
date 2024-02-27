---
hide:
  - toc
---

# Property class
The property class is an extension of `ObservableValue` and adds the ability to bind a property to another `ObservableValue` to keep one or both in sync with each other.  
There are two types of bindings, single direction and bidireactional.
## Single Direction Bind
Single-Direction means that if you change the value that was passed into the bind method, then the value of the property is also changed. However, this does not work in reverse.  
Lets set up some code to help illustrate this, I will add change listeners to both to allow us to see what happened.
```java
ObservableValue<Integer> observableValue = new ObservableValue<>(10);
observableValue.addChangeListener((observable, oldValue, newValue) -> System.out.println("observableValue was changed from " + oldValue + " to " + newValue));
Property<Integer> property = new Property<>(10);
property.addChangeListener((observable, oldValue, newValue) -> System.out.println("property changed from " + oldValue + " to " + newValue));
```
With this set up, we can now call bind to bind the value of the property to the value of the observableValue. Do note, the change in values only happens on a change. This is a bug and will be fixed in a future update.
```java
property.bind(observableValue);
```
This will now make is to that if we change the `observableValue`, the value of `property` will change as well. This type of bind does not work in reverse though.
```java
observableValue.setValue(100);
```
Will output:
```text
observableValue was changed from 10 to 100
property changed from 10 to 100
```
But if we do this:
```java
property.setValue(50);
```
Then this is our output
```text
property changed from 100 to 50
```
## Bidirectional Bind
If we want to have both of them be in sync whenever the other one changes, we can use a Bidirectional Bind.  
This is created by doing this:
```java
property.bidirectionalBind(observableValue);
```
And if we do this:
```java
observableValue.setValue(100);
property.setValue(50);
```
We get the following output:
```text
observableValue was changed from 10 to 100
property changed from 10 to 100
property changed from 100 to 50
observableValue was changed from 100 to 50
```