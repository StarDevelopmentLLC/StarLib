package com.stardevllc.starlib.observable;

import java.util.Objects;

/**
 * Represents a value with an identity that can be read from and written to
 *
 * @param <T> The value type
 */
public interface WritableProperty<T> extends Property<T>, WritableObservableValue<T> {
    
    /**
     * Binds this property to another property bidirectionally
     *
     * @param other The other property
     */
    void bindBidirectionally(WritableProperty<T> other);
    
    /**
     * Unbinds this property from the other one
     *
     * @param other The other property
     */
    void unbindBidirectionally(WritableProperty<T> other);
    
    /**
     * This change listener for the binding
     *
     * @param <T> The type
     */
    class BidirectionalBindListener<T> implements ChangeListener<T> {
        
        private WritableProperty<T> property1, property2;
        private boolean updating;
        
        /**
         * Constructs a new listener
         *
         * @param property1 The first property
         * @param property2 The second property
         */
        public BidirectionalBindListener(WritableProperty<T> property1, WritableProperty<T> property2) {
            this.property1 = property1;
            this.property2 = property2;
        }
        
        @Override
        public void changed(ObservableValue<T> observableValue, T oldValue, T newValue) {
            if (updating) {
                return;
            }
            
            updating = true;
            if (property1 == observableValue) {
                property2.setValue(newValue);
            } else {
                property1.setValue(newValue);
            }
            updating = false;
        }
        
        /**
         * Gets the first property
         *
         * @return The first property
         */
        public WritableProperty<T> getProperty1() {
            return property1;
        }
        
        /**
         * Gets the second property
         *
         * @return The second property
         */
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