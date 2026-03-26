package com.stardevllc.starlib.table;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TreeTable<R extends Comparable<R>, C extends Comparable<C>, V> implements Table<R, C, V> {
    
    protected final TreeMap<R, TreeMap<C, V>> backingMap = new TreeMap<>();
    
    @Override
    public Set<? extends Cell<R, C, V>> getCellSet() {
        Set<TreeCell> set = new TreeSet<>();
        this.backingMap.forEach((row, colMap) -> colMap.forEach((column, value) -> set.add(new TreeCell(row, column, value))));
        return set;
    }
    
    @Override
    public void clear() {
        this.backingMap.clear();
    }
    
    @Override
    public Map<R, V> column(C columnKey) {
        Map<R, V> column = new TreeMap<>();
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
        Map<C, Map<R, V>> columnMap = new TreeMap<>();
        this.backingMap.forEach((row, colMap) -> colMap.forEach((column, value) -> columnMap.computeIfAbsent(column, c -> new TreeMap<>()).put(row, value)));
        return columnMap;
    }
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean contains(Object rowKey, Object columnKey) {
        TreeMap<C, V> rowMap = this.backingMap.get(rowKey);
        return rowMap != null && rowMap.containsKey(columnKey);
    }
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean containsColumn(Object columnKey) {
        return columnKeySet().contains(columnKey);
    }
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean containsRow(Object rowKey) {
        return this.backingMap.containsKey(rowKey);
    }
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public V get(Object rowKey, Object columnKey) {
        TreeMap<C, V> rowMap = this.backingMap.get(rowKey);
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
        return this.backingMap.computeIfAbsent(rowKey, r -> new TreeMap<>()).put(columnKey, value);
    }
    
    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        for (Cell<? extends R, ? extends C, ? extends V> cell : table.getCellSet()) {
            put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public V remove(Object rowKey, Object columnKey) {
        TreeMap<C, V> rowMap = this.backingMap.get(rowKey);
        if (rowMap == null) {
            return null;
        }
        return rowMap.remove(columnKey);
    }
    
    @Override
    public Map<C, V> row(R rowKey) {
        return this.backingMap.getOrDefault(rowKey, new TreeMap<>());
    }
    
    @Override
    public Set<R> rowKeySet() {
        return this.backingMap.keySet();
    }
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public Map<C, V> remove(Object rowKey) {
        return this.backingMap.remove(rowKey);
    }
    
    @Override
    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> rowMap = new TreeMap<>();
        this.backingMap.forEach((row, colMap) -> rowMap.put(row, new TreeMap<>(colMap)));
        return rowMap;
    }
    
    @Override
    public int size() {
        int size = 0;
        
        for (Map.Entry<R, TreeMap<C, V>> rowEntry : this.backingMap.entrySet()) {
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
    private class ColumnKeyIterator implements Iterator<C> {
        
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
    
    private class ColumnKeySet extends AbstractSet<C> {
        
        @Override
        public Iterator<C> iterator() {
            return new ColumnKeyIterator();
        }
        
        @Override
        public int size() {
            return TreeTable.this.size();
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
            return TreeTable.this.size();
        }
    }
    
    protected class TreeCell implements Cell<R, C, V>, Comparable<Cell<R, C, V>> {
        
        private final R row;
        private final C column;
        private final V value;
        
        public TreeCell(R row, C column, V value) {
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
        
        @Override
        public int compareTo(@NotNull Table.Cell<R, C, V> o) {
            int rowCompare = row.compareTo(o.getRowKey());
            if (rowCompare != 0) {
                return rowCompare;
            }
            
            return column.compareTo(o.getColumnKey());
        }
    }
}
