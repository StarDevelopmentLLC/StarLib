package com.stardevllc.observable.constants;

import com.stardevllc.observable.ChangeListener;
import com.stardevllc.observable.value.ObservableCharacterValue;

public class CharacterConstant implements ObservableCharacterValue {
    
    private final char value;

    public CharacterConstant(char value) {
        this.value = value;
    }

    @Override
    public char get() {
        return value;
    }

    @Override
    public void addListener(ChangeListener<? super Character> listener) {
        //no-op
    }

    @Override
    public void removeListener(ChangeListener<? super Character> listener) {
        //no-op
    }

    @Override
    public Character getValue() {
        return value;
    }
}
