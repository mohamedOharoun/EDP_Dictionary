import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Items class represents the view of items (key-value pairs) returned when invoking dict.items() in Python.
 * It implements both the Iterable and ObjectView interfaces, allowing iteration over the items
 * of a dictionary and providing methods to interact with these items.
 */
public class Items implements Iterable<Pair>, ObjectView<Pair> {
    private final EntriesList entries;
    private final Dictionary dict;

    Items(EntriesList values, Dictionary dict) {
        this.entries = values;
        this.dict = dict;
    }

    /**
     * Returns an iterator over the items (key-value pairs) of the dictionary.
     *
     * @return An iterator over the items (key-value pairs) of the dictionary.
     */
    @Override
    public Iterator<Pair> iterator() {
        return new ItemsIterator(this);
    }

    /**
     * Returns an iterable object for reverse iteration over the items of the dictionary.
     *
     * @return An iterable object for reverse iteration over the items of the dictionary.
     */
    public Iterable<Pair> reversed() {
        return new ItemsReversed(this);
    }

    /**
     * Checks if the specified item (key-value pair) is contained in the dictionary.
     *
     * @param p The item (key-value pair) to check for containment.
     * @return True if the item is contained in the dictionary, false otherwise.
     */
    public boolean contains(Pair p) {
        if(!dict.contains(p.get(0))) return false;
        if(dict.get(p.get(0)).equals(p.get(1))) return true;
        return false;
    }

    /**
     * Returns the number of items (key-value pairs) in the dictionary.
     *
     * @return The number of items (key-value pairs) in the dictionary.
     */
    public int length() {
        return entries.size();
    }

    private class ItemsIterator implements Iterator<Pair> {
        Items items;
        int index;

        public ItemsIterator(Items items){
            this.items = items;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if(index >= items.entries.capacity()) return false;
            while(index < items.entries.capacity()) {
                if(items.entries.get(index) != null) return true;
                index++;
            }
            return false;
        }

        @Override
        public Pair next() {
            if (hasNext()) {
                return items.entries.get(index++).getItem();
            }
            throw new NoSuchElementException();
        }
    }

    private class ItemsReversed implements Iterable<Pair> {
        private final Items items;

        public ItemsReversed(Items items) {
            this.items = items;
        }

        @Override
        public Iterator<Pair> iterator() {
           return new ItemsReversedIterator(items);
        }

        private class ItemsReversedIterator implements Iterator<Pair> {
            private final Items items;
            private int index;

            public ItemsReversedIterator(Items items){
                this.items = items;
                this.index = entries.capacity() - 1;
            }
    
            @Override
            public boolean hasNext() {
                if(index < 0) return false;
                while(index >= 0) {
                    if(items.entries.get(index) != null) return true;
                    index--;
                }
                return false;
            }
    
            @Override
            public Pair next() {
                if (hasNext()) {
                    return items.entries.get(index--).getItem();
                }
                throw new NoSuchElementException();
            }
        }  
    }

    /**
     * Returns a string representation of the items view, formatted as dict_items([...]).
     *
     * @return A string representation of the items view.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final String opening = "dict_items([";
        sb.append(opening);
        Entry temp;
        for(int i = 0; i < entries.capacity()-1; i++) {
            temp = entries.get(i);
            if(temp != null) {
                if(sb.length() != opening.length()) sb.append(", ");
                sb.append(temp.toString());
            }
        }
        sb.append("])");
        return sb.toString();
    }
}
