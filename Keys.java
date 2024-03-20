import java.util.Iterator;
import java.util.NoSuchElementException;

public class Keys implements Iterable<Object>{
    final private ValuesList values;
    final private Dictionary dic;

    @Override
    public Iterator<Object> iterator() {
        return new KeysIterator(this);
    }

    public Iterable<Object> reversed() {
        return new KeysReversed(this);
    }

    public Keys(ValuesList values, Dictionary dic) {
        this.values = values;
        this.dic = dic;
    }

    public boolean contains(Object k) {
        return dic.contains(k);
    }

    public int length() {
        return values.size();
    }

    private class KeysIterator implements Iterator<Object> {
        private final Keys keys;
        private int index;

        public KeysIterator(Keys keys){
            this.keys = keys;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if(index >= keys.values.capacity()) return false;
            while(index <= keys.values.capacity()) {
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


    private class KeysReversed implements Iterable<Object> {
        private final Keys keys;

        public KeysReversed(Keys keys) {
            this.keys = keys;
        }

        @Override
        public Iterator<Object> iterator() {
           return new KeysReversedIterator(keys);
        }

        private class KeysReversedIterator implements Iterator<Object> {
            private final Keys keys;
            private int index;

            public KeysReversedIterator(Keys keys){
                this.keys = keys;
                this.index = values.capacity() - 1;
            }
    
            @Override
            public boolean hasNext() {
                if(index < 0) return false;
                while(index >= 0) {
                    if(keys.values.get(index) != null) return true;
                    index--;
                }
                return false;
            }
    
            @Override
            public Object next() {
                if (hasNext()) {
                    return keys.values.get(index--).getKey();
                }
                throw new NoSuchElementException();
            }
        }  
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("dict_keys([");
        Entry temp;
        for(int i = 0; i < values.capacity()-1; i++) {
            temp = values.get(i);
            if(temp != null) {
                if((temp.getKey() instanceof String || temp.getKey() instanceof Character)) {
                    sb.append("\'"); sb.append(temp.getKey()); sb.append("\', ");
                } else {sb.append(temp.getKey()); sb.append(", ");}
            }
        } 
        temp = values.get(values.capacity()-1);
        if((temp.getKey() instanceof String || temp.getKey() instanceof Character)) {
            sb.append("\'"); sb.append(temp.getKey()); sb.append("\'");
        } else {sb.append(temp.getKey());}
        sb.append(")]");
        return sb.toString();
    }
}
