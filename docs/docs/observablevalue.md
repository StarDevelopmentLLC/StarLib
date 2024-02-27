---
hide:
  - toc
---

# ObservableValue class

This class is simply a wrapper-type class over another object. This allows listening to when the wrapped object is set to a different value. Note: This does not detect changes to the wrapped object instance values, but the actual value stored in the ObservableValue. It is recommended to use multiple observable values in more complex types to detect state changes.  
Pretty much, it is intended to be used with primitive wrappers, Strings and some other simple types.  
Consider the following:
```java
ObservableValue<Integer> observableInteger = new ObservableValue<>(10);
System.out.println(observableInteger.getValue());
```
Is the same as
```java
int regularInteger = 10;
System.out.println(regularInteger);
```
However, we can add a `ChangeListener` to listen for changes to the observableInteger.
```java
observableInteger.addChangeListener((observable, oldValue, newValue) -> {
    System.out.println("The observableInteger was changed from " + oldValue + " to " + newValue);
});
        
observableInteger.setValue(20);
```
And we get one the following output:
```text
The observableInteger was changed from 10 to 20
```

And that is pretty much it for the ObservableValue. This is expanded upon in the `Property` class as well as the `ObservableList`, `ObservableSet` and `ObservableMap` classes. Take a look at their wiki pages for what you can do with them. 