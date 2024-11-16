package com.stardevllc.observable;

import com.stardevllc.observable.collections.ObservableSet;
import com.stardevllc.observable.collections.ObservableTreeSet;
import com.stardevllc.observable.collections.event.CollectionAddEvent;
import com.stardevllc.observable.collections.event.CollectionRemoveEvent;

public class StarObservable {
    public static void main(String[] args) {
        ObservableSet<String> testList = new ObservableTreeSet<>();
        testList.addListener(event -> {
            if (event instanceof CollectionAddEvent<?> addEvent) {
                System.out.println("\"" + addEvent.added() + "\" was added to the testList");
            } else if (event instanceof CollectionRemoveEvent removeEvent) {
                System.out.println("\"" + removeEvent.removed() + "\" was removed from the testList");
            }
        });
        
        testList.add("Hi");
        testList.add("Hello");
        testList.remove("Hi");
        testList.remove("Hello");
    }
}
