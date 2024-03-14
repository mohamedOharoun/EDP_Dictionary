import java.util.Iterator;
import java.util.NoSuchElementException;

public class Values implements Iterable<Object>{
    private final ValuesList values;

    public Values(ValuesList values) {
        this.values = values;
    }

    @Override
    public Iterator<Object> iterator() {
        return new ValuesIterator(this);
    }

    public Iterable<Object> reversed() {
        return new ValuesReversed(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("dict_values([");
        Entry temp;
        for(int i = 0; i < values.length()-1; i++) {
            temp = values.get(i);
            if(temp != null) {
                if (temp.getValue() instanceof String || temp.getValue() instanceof Character) {
                    sb.append("\'"); sb.append(temp.getValue()); sb.append("\'");
                } else {sb.append(temp.getValue());}
                sb.append(", ");
            }
        } 
        temp = values.get(values.length()-1);
        if (temp.getValue() instanceof String || temp.getValue() instanceof Character) {
            sb.append("\'"); sb.append(temp.getValue()); sb.append("\'");
        } else {sb.append(temp.getValue());}
        sb.append("])");
        return sb.toString();
    }

    public boolean contains(Object other) {
        return values.containsValue(other);
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
            if(index >= vals.values.length()) return false;
            while(index <= vals.values.length()) {
                if(vals.values.get(index) != null) return true;
                index++;
            }
            return false;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return vals.values.get(index++).getValue();
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
                this.index = vals.values.length() - 1;
            }
    
            @Override
            public boolean hasNext() {
                if(index < 0) return false;
                while(index >= 0) {
                    if(vals.values.get(index) != null) return true;
                    index--;
                }
                return false;
            }
    
            @Override
            public Object next() {
                if (hasNext()) {
                    return vals.values.get(index--).getValue();
                }
                throw new NoSuchElementException();
            }
        }  
    }
}
