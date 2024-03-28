import java.util.Iterator;
import java.util.NoSuchElementException;

public class Values implements Iterable<Object>{
    private final EntriesList entries;

    public Values(EntriesList values) {
        this.entries = values;
    }

    @Override
    public Iterator<Object> iterator() {
        return new ValuesIterator(this);
    }

    public Iterable<Object> reversed() {
        return new ValuesReversed(this);
    }

    public int length() {
        return entries.size();
    }

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
}
