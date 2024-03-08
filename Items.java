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
                return items.values.get(index++).getItem();
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("dict_items([");
        Entry temp;
        for(int i = 0; i < values.length()-1; i++) {
            temp = values.get(i);
            if(temp != null) {
                sb.append(temp.toString()); sb.append(", ");
            }
        } 
        temp = values.get(values.length()-1);
        sb.append(temp.toString());
        sb.append("])");
        return sb.toString();
    }
}
