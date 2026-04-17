package com.stardevllc.starlib.table;

import java.util.*;

public class HashTable<R, C, V> implements Table<R, C, V> {
    
    protected final LinkedHashMap<R, LinkedHashMap<C, V>> backingMap = new LinkedHashMap<>();
    
    @Override
    public Set<Cell<R, C, V>> getCellSet() {
        Set<Cell<R, C, V>> set = new HashSet<>();
        this.backingMap.forEach((row, colMap) -> colMap.forEach((column, value) -> set.add(new HashCell(row, column, value))));
        return set;
    }
    
    @Override
    public void clear() {
        this.backingMap.clear();
    }
    
    @Override
    public Map<R, V> column(C columnKey) {
        Map<R, V> column = new LinkedHashMap<>();
        this.backingMap.forEach((row, colMap) -> {
            V value = colMap.get(columnKey);
            if (value != null) {
                column.put(row, value);
            }
        });
        
        return column;
    }
    
    @Override
    public Set<C> columnKeySet() {
        return new ColumnKeySet();
    }
    
    @Override
    public Map<C, Map<R, V>> columnMap() {
        Map<C, Map<R, V>> columnMap = new LinkedHashMap<>();
        this.backingMap.forEach((row, colMap) -> colMap.forEach((column, value) -> columnMap.computeIfAbsent(column, c -> new LinkedHashMap<>()).put(row, value)));
        return columnMap;
    }
    
    @Override
    public boolean contains(R rowKey, C columnKey) {
        Map<C, V> rowMap = this.backingMap.get(rowKey);
        return rowMap != null && rowMap.containsKey(columnKey);
    }
    
    @Override
    public boolean containsColumn(C columnKey) {
        return columnKeySet().contains(columnKey);
    }
    
    @Override
    public boolean containsRow(R rowKey) {
        return this.backingMap.containsKey(rowKey);
    }
    
    @Override
    public boolean containsValue(V value) {
        return values().contains(value);
    }
    
    @Override
    public V get(R rowKey, C columnKey) {
        Map<C, V> rowMap = this.backingMap.get(rowKey);
        if (rowMap == null) {
            return null;
        }
        
        return rowMap.get(columnKey);
    }
    
    @Override
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }
    
    @Override
    public V put(R rowKey, C columnKey, V value) {
        return this.backingMap.computeIfAbsent(rowKey, r -> new LinkedHashMap<>()).put(columnKey, value);
    }
    
    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        for (Cell<? extends R, ? extends C, ? extends V> cell : table.getCellSet()) {
            put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }
    
    @Override
    public V remove(R rowKey, C columnKey) {
        Map<C, V> rowMap = this.backingMap.get(rowKey);
        if (rowMap == null) {
            return null;
        }
        
        V v = rowMap.remove(columnKey);
        if (rowMap.isEmpty()) {
            this.backingMap.remove(rowKey);
        }
        return v;
    }
    
    @Override
    public Map<C, V> row(R rowKey) {
        return this.backingMap.getOrDefault(rowKey, new LinkedHashMap<>());
    }
    
    @Override
    public Set<R> rowKeySet() {
        return this.backingMap.keySet();
    }
    
    @Override
    public Map<C, V> remove(R rowKey) {
        return this.backingMap.remove(rowKey);
    }
    
    @Override
    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> rowMap = new LinkedHashMap<>();
        this.backingMap.forEach((row, colMap) -> rowMap.put(row, new LinkedHashMap<>(colMap)));
        return rowMap;
    }
    
    @Override
    public int size() {
        int size = 0;
        
        for (Map.Entry<R, LinkedHashMap<C, V>> rowEntry : this.backingMap.entrySet()) {
            size += rowEntry.getValue().size();
        }
        
        return size;
    }
    
    @Override
    public String toString() {
        return this.backingMap.toString();
    }
    
    @Override
    public Collection<V> values() {
        return new Values();
    }
    
    @SuppressWarnings("DuplicatedCode")
    protected class ColumnKeyIterator implements Iterator<C> {
        
        //This is the iterator for the Row Keys and is only created once per ValueIterator as it is based on the backing maps keySet()
        private final Iterator<R> rowKeyIterator;
        
        //The current row key, used for convenience and tracking purposes
        private R currentRowKey;
        
        //This is the current row
        private Map<C, V> currentRow;
        
        //this is the iterator for the column keys, this is updated when the previous column key iterator is at it's end
        private Iterator<C> columnKeyIterator;
        
        private C currentColumnKey;
        
        public ColumnKeyIterator() {
            this.rowKeyIterator = backingMap.keySet().iterator();
        }
        
        @Override
        public boolean hasNext() {
            //This really shouldn't be null, but just a safety check
            if (this.rowKeyIterator == null) {
                return false;
            }
            
            //Check to see if the current row has another column, if so, return true here
            if (columnKeyIterator != null && columnKeyIterator.hasNext()) {
                return true;
            }
            
            //If we get here, we are done with the current row, and need to move on
            if (this.rowKeyIterator.hasNext()) {
                //Advance to the next row, I know this is hasNext() but we need to do this
                this.currentRowKey = this.rowKeyIterator.next();
                
                //Get and cache the current row
                this.currentRow = backingMap.get(this.currentRowKey);
                
                //Obtain the iterator for the key set of the row map. 
                this.columnKeyIterator = this.currentRow.keySet().iterator();
                //Recursive call to continue on, this will hopefully cover empty rows in the backing back
                return hasNext();
            }
            
            return false;
        }
        
        @Override
        public C next() {
            //Safety check to see if we can advance to the next value, exception is based on contract defined in the Iterator interface
            //The hasNext method will also initialize the column key iterator that we need and also advance the row if needed, this way we don't need to repeat that logic
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            return this.currentColumnKey = this.columnKeyIterator.next();
        }
        
        @Override
        public void remove() {
            if (this.columnKeyIterator == null) {
                return;
            }
            
            this.columnKeyIterator.remove();
            this.currentColumnKey = null;
            
            if (this.currentRow.isEmpty()) {
                this.rowKeyIterator.remove();
                this.currentRow = null;
            }
        }
    }
    
    protected class ColumnKeySet extends AbstractSet<C> {
        
        @Override
        public Iterator<C> iterator() {
            return new ColumnKeyIterator();
        }
        
        @Override
        public int size() {
            return HashTable.this.size();
        }
    }
    
    protected class ValueIterator implements Iterator<V> {
        
        private final ColumnKeyIterator columnKeyIterator;
        
        public ValueIterator() {
            this.columnKeyIterator = new ColumnKeyIterator();
        }
        
        @Override
        public boolean hasNext() {
            return this.columnKeyIterator.hasNext();
        }
        
        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            this.columnKeyIterator.next();
            return this.columnKeyIterator.currentRow.get(this.columnKeyIterator.currentColumnKey);
        }
        
        @Override
        public void remove() {
            this.columnKeyIterator.remove();
        }
    }
    
    protected class Values extends AbstractCollection<V> {
        
        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
        
        @Override
        public int size() {
            return HashTable.this.size();
        }
    }
    
    protected class HashCell implements Table.Cell<R, C, V> {
        
        private final R row;
        private final C column;
        private final V value;
        
        public HashCell(R row, C column, V value) {
            this.row = row;
            this.column = column;
            this.value = value;
        }
        
        @Override
        public C getColumnKey() {
            return column;
        }
        
        @Override
        public R getRowKey() {
            return row;
        }
        
        @Override
        public V getValue() {
            return value;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.column, this.row, this.value);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Table.Cell<?, ?, ?> cell)) {
                return false;
            }
            
            return Objects.equals(this.row, cell.getRowKey()) && Objects.equals(this.column, cell.getColumnKey()) && Objects.equals(this.value, cell.getValue());
        }
    }
}
