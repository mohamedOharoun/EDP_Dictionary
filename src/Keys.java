import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Keys class represents the view of keys returned when invoking dict.keys() in Python.
 * It implements both the Iterable and ObjectView interfaces, allowing iteration over the keys
 * of a dictionary and providing methods to interact with these keys.
 */
public class Keys implements Iterable<Object>, ObjectView<Object> {
    final private EntriesList entries;
    final private Dictionary dic;

    Keys(EntriesList values, Dictionary dic) {
        this.entries = values;
        this.dic = dic;
    }

    /**
     * Returns an iterator over the keys of the dictionary.
     *
     * @return An iterator over the keys of the dictionary.
     */
    @Override
    public Iterator<Object> iterator() {
        return new KeysIterator(this);
    }

    /**
     * Returns an iterable object for reverse iteration over the keys of the dictionary.
     *
     * @return An iterable object for reverse iteration over the keys of the dictionary.
     */
    public Iterable<Object> reversed() {
        return new KeysReversed(this);
    }

    /**
     * Checks if the specified key is contained in the dictionary.
     *
     * @param k The key to check for containment.
     * @return True if the key is contained in the dictionary, false otherwise.
     */
    public boolean contains(Object k) {
        return dic.contains(k);
    }

    /**
     * Returns the number of keys in the dictionary.
     *
     * @return The number of keys in the dictionary.
     */
    public int length() {
        return entries.size();
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
            if(index >= keys.entries.capacity()) return false;
            while(index < keys.entries.capacity()) {
                if(keys.entries.get(index) != null) return true;
                index++;
            }
            return false;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return keys.entries.get(index++).getKey();
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
                this.index = entries.capacity() - 1;
            }
    
            @Override
            public boolean hasNext() {
                if(index < 0) return false;
                while(index >= 0) {
                    if(keys.entries.get(index) != null) return true;
                    index--;
                }
                return false;
            }
    
            @Override
            public Object next() {
                if (hasNext()) {
                    return keys.entries.get(index--).getKey();
                }
                throw new NoSuchElementException();
            }
        }  
    }

    /**
     * Returns a string representation of the keys view, formatted as dict_keys([...]), if a key is a String or a Character, it will be sorrounded with simple quotes.
     *
     * @return A string representation of the keys view.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final String opening = "dict_keys([";
        sb.append(opening);
        Entry temp;
        for(int i = 0; i < entries.capacity()-1; i++) {
            temp = entries.get(i);
            if(temp != null) {
                if(sb.length() != opening.length()) sb.append(", ");
                if((temp.getKey() instanceof String || temp.getKey() instanceof Character)) {
                    sb.append("\'"); sb.append(temp.getKey()); sb.append("\'");
                } else sb.append(temp.getKey());
            }
        } 
        
        sb.append("])");
        return sb.toString();
    }
}
