package com.stardevllc.starlib.observable;

import java.util.Objects;

/**
 * Represents a value with an identity that can be read from and written to
 *
 * @param <T> The value type
 */
public interface WritableProperty<T> extends Property<T>, WritableValue<T> {
    void bindBidirectionally(WritableProperty<T> other);
    void unbindBidirectionally(WritableProperty<T> other);
    
    class BidirectionalBindListener<T> implements ChangeListener<T> {
    
        private WritableProperty<T> property1, property2;
        private boolean updating;
        
        public BidirectionalBindListener(WritableProperty<T> property1, WritableProperty<T> property2) {
            this.property1 = property1;
            this.property2 = property2;
        }
        
        @Override
        public void changed(ChangeEvent<T> event) {
            if (updating) {
                return;
            }
            
            updating = true;
            ObservableValue<? extends T> source = event.observableValue();
            if (property1 == source) {
                property2.setValue(event.newValue());
            } else {
                property1.setValue(event.newValue());
            }
            updating = false;
        }
        
        public WritableProperty<T> getProperty1() {
            return property1;
        }
        
        public WritableProperty<T> getProperty2() {
            return property2;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            
            BidirectionalBindListener<?> that = (BidirectionalBindListener<?>) o;
            return Objects.equals(property1, that.property1) && Objects.equals(property2, that.property2) || Objects.equals(property1, that.property2) && Objects.equals(property2, that.property1);
        }
        
        @Override
        public int hashCode() {
            int result = Objects.hashCode(property1);
            result = 31 * result + Objects.hashCode(property2);
            return result;
        }
    }
}