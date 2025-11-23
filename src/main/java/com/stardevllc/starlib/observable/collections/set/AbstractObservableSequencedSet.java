package com.stardevllc.starlib.observable.collections.set;

import com.stardevllc.starlib.observable.collections.listener.SetChangeListener;

import java.lang.ref.WeakReference;
import java.util.*;

public abstract class AbstractObservableSequencedSet<E> extends AbstractObservableSet<E> implements SequencedSet<E> {
    public AbstractObservableSequencedSet() {
    }
    
    public AbstractObservableSequencedSet(Set<E> set) {
        super(set);
    }
    
    protected abstract SequencedSet<E> getBackingSequencedSet();
    
    @Override
    protected Set<E> getBackingSet() {
        return getBackingSequencedSet();
    }
    
    protected class SubSetListener implements SetChangeListener<E> {
        
        private final WeakReference<AbstractObservableSequencedSet<E>> backingSetRef;
        private final WeakReference<AbstractObservableSequencedSet<E>> subSet;
        
        private E fromElement, toElement;
        private boolean fromElementInclusive, toElementInclusive;
        
        public SubSetListener(AbstractObservableSequencedSet<E> backingSet, AbstractObservableSequencedSet<E> subSet) {
            this.backingSetRef = new WeakReference<>(backingSet);
            this.subSet = new WeakReference<>(subSet);
        }
        
        public SubSetListener(AbstractObservableSequencedSet<E> backingSet, AbstractObservableSequencedSet<E> subSet, E fromElement, boolean fromElementInclusive, E toElement, boolean toElementInclusive) {
            this(backingSet, subSet);
            this.fromElement = fromElement;
            this.fromElementInclusive = fromElementInclusive;
            this.toElement = toElement;
            this.toElementInclusive = toElementInclusive;
        }
        
        @Override
        public void changed(Change<E> change) {
            AbstractObservableSequencedSet<E> backingSet = this.backingSetRef.get();
            AbstractObservableSequencedSet<E> subSet = this.subSet.get();
            if (backingSet == null && subSet == null) {
                return;
            } else if (backingSet == null) {
                subSet.removeListener(this);
                return;
            } else if (subSet == null) {
                backingSet.removeListener(this);
                return;
            }
            
            AbstractObservableSequencedSet<E> setToChange;
            if (change.collection() == backingSet) {
                setToChange = subSet;
            } else if (change.collection() == subSet) {
                setToChange = backingSet;
            } else {
                return;
            }
            
            if (change.added() != null && change.removed() != null) {
                setToChange.getBackingSequencedSet().remove(change.removed());
                setToChange.getBackingSequencedSet().add(change.added());
            } else if (change.added() != null) {
                setToChange.getBackingSequencedSet().add(change.added());
            } else if (change.removed() != null) {
                setToChange.getBackingSequencedSet().remove(change.removed());
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> reversed() {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.getBackingSequencedSet().reversed());
        SubSetListener listener = new SubSetListener(this, set);
        set.addListener(listener);
        this.addListener(listener);
        return set;
    }
}