import java.util.Iterator;
import java.util.NoSuchElementException;

public class Items implements Iterable<Object>{
    //private IndexesList indexes;
    private ValuesList values;

    @Override
    public Iterator<Object> iterator() {
        return new ItemsIterator(this);
    }

    public Items(IndexesList indexes, ValuesList values) {
        //this.indexes = indexes;
        this.values = values;
    }

    public int length() {
        return values.size();
    }

    public class ItemsIterator implements Iterator<Object> {
        Items items;
        int index;

        public ItemsIterator(Items items){
            this.items = items;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if(index >= items.values.length()) return false;
            while(index <= items.values.length()) {
                if(items.values.get(index) != null) return true;
                index++;
            }
            return false;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return (Pair) items.values.get(index++);
            }
            throw new NoSuchElementException();
        }
    }
}
