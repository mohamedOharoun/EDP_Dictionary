import java.util.Iterator;
import java.util.NoSuchElementException;

public class Items implements Iterable<Object>{
    final private ValuesList values;
    final private Dictionary dict;

    @Override
    public Iterator<Object> iterator() {
        return new ItemsIterator(this);
    }

    public Items(ValuesList values, Dictionary dict) {
        this.values = values;
        this.dict = dict;
    }

    public boolean contains(Pair p) {
        Object obj = dict.getElement(p.get(0));
        if(obj.equals(-1)) return false;
        if(((Entry) obj).getValue().equals(p.get(1))) return true;
        return false;
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
