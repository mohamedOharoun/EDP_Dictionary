import java.util.Iterator;
import java.util.NoSuchElementException;

public class Values implements Iterable<Object>{
    ValuesList values;

    public Values(ValuesList values) {
        this.values = values;
    }

    @Override
    public Iterator<Object> iterator() {
        return new ValuesIterator(this);
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
        for(int i = 0; i < values.length(); i++) {
            if(values.get(i) == null) continue;
            if(other.equals(values.get(i).getValue())) return true;
        }
        return false;
    }

    public class ValuesIterator implements Iterator<Object> {
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
}
