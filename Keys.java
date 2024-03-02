import java.util.Iterator;
import java.util.NoSuchElementException;

public class Keys implements Iterable<Object>{
    private IndexesList indexes;
    private ValuesList values;

    @Override
    public Iterator<Object> iterator() {
        return new KeysIterator(this);
    }

    public Keys(IndexesList indexes, ValuesList values) {
        this.indexes = indexes;
        this.values = values;
    }

    public int length() {
        return values.size();
    }

    public class KeysIterator implements Iterator<Object> {
        Keys keys;
        int index;

        public KeysIterator(Keys keys){
            this.keys = keys;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if(index >= keys.values.length()) return false;
            while(index <= keys.values.length()) {
                if(keys.values.get(index) != null) return true;
                index++;
            }
            return false;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return keys.values.get(index++).getKey();
            }
            throw new NoSuchElementException();
        }
    }
}
