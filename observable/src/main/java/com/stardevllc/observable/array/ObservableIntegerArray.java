package com.stardevllc.observable.array;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.ArrayChangeListener;
import com.stardevllc.observable.ObservableArray;
import com.stardevllc.observable.WritableArray;

public class ObservableIntegerArray implements ObservableArray<Integer>, WritableArray<Integer> {
    
    protected int[] array;
    
    protected final EventBus eventBus = new SimpleEventBus();

    public ObservableIntegerArray(int[] array) {
        this.array = array;
    }
    
    public ObservableIntegerArray() {
        this.array = new int[10];
    }

    @Override
    public void set(int index, Integer value) {
        if (array.length <= index) {
            int[] expanededArray = new int[index * 2];
            System.arraycopy(array, 0, expanededArray, 0, array.length);
            this.array = expanededArray;
        }
        
        int oldValue = array[index];
        array[index] = value;
        if (oldValue != value) {
            eventBus.post(new ArrayChangeListener.ArrayChangeEvent(this, index, oldValue, value));
        }
    }

    @Override
    public Integer get(int index) {
        return array[index];
    }

    @Override
    public int length() {
        return array.length;
    }

    @Override
    public void addListener(ArrayChangeListener<Integer> listener) {
        eventBus.subscribe(listener);
    }

    @Override
    public void removeListener(ArrayChangeListener<Integer> listener) {
        eventBus.unsubscribe(listener);
    }
}
