import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Values class represents the view of values of the Dictionary, similar to what is returned when invoking dict.values() in Python.
 * It implements both the Iterable and ObjectView interfaces, allowing iteration over the values
 * of a dictionary and providing methods to interact with these values.
 */
public class Values implements Iterable<Object>, ObjectView<Object> {
    private final EntriesList entries;

    Values(EntriesList values) {
        this.entries = values;
    }

    /**
     * Returns an iterator over the values of the dictionary.
     *
     * @return An iterator over the values of the dictionary.
     */
    @Override
    public Iterator<Object> iterator() {
        return new ValuesIterator(this);
    }

    /**
     * Returns an iterable object for reverse iteration over the values of the dictionary.
     *
     * @return An iterable object for reverse iteration over the values of the dictionary.
     */
    public Iterable<Object> reversed() {
        return new ValuesReversed(this);
    }

    /**
     * Returns the number of values in the dictionary.
     *
     * @return The number of values in the dictionary.
     */
    public int length() {
        return entries.size();
    }

    /**
     * Checks if the specified value is contained in the dictionary.
     *
     * @param other The value to check for containment.
     * @return True if the value is contained in the dictionary, false otherwise.
     */
    public boolean contains(Object other) {
        return entries.containsValue(other);
    }

    private class ValuesIterator implements Iterator<Object> {
        Values vals;
        int index;

        public ValuesIterator(Values vals){
            this.vals = vals;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if(index >= vals.entries.capacity()) return false;
            while(index < vals.entries.capacity()) {
                if(vals.entries.get(index) != null) return true;
                index++;
            }
            return false;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return vals.entries.get(index++).getValue();
            }
            throw new NoSuchElementException();
        }
    }

    private class ValuesReversed implements Iterable<Object> {
        private final Values values;

        public ValuesReversed(Values values) {
            this.values = values;
        }

        @Override
        public Iterator<Object> iterator() {
           return new ValuesReversedIterator(values);
        }

        private class ValuesReversedIterator implements Iterator<Object> {
            private final Values vals;
            private int index;

            public ValuesReversedIterator(Values values){
                this.vals = values;
                this.index = vals.entries.capacity() - 1;
            }
    
            @Override
            public boolean hasNext() {
                if(index < 0) return false;
                while(index >= 0) {
                    if(vals.entries.get(index) != null) return true;
                    index--;
                }
                return false;
            }
    
            @Override
            public Object next() {
                if (hasNext()) {
                    return vals.entries.get(index--).getValue();
                }
                throw new NoSuchElementException();
            }
        }  
    }

    /**
     * Returns a string representation of the values view, formatted as dict_values([...]), if a value is a String or a Character, it will be sorrounded with simple quotes.
     *
     * @return A string representation of the values view.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final String opening = "dict_values([";
        sb.append(opening);
        Entry temp;
        for(int i = 0; i < entries.capacity()-1; i++) {
            temp = entries.get(i);
            if(temp != null) {
                if(sb.length() != opening.length()) sb.append(", ");
                if((temp.getValue() instanceof String || temp.getValue() instanceof Character)) {
                    sb.append("\'"); sb.append(temp.getValue()); sb.append("\'");
                } else sb.append(temp.getValue());
            }
        } 
        
        sb.append("])");
        return sb.toString();
    }
}