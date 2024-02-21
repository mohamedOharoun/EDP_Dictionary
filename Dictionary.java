import java.util.Arrays;

public class Dictionary {
    
    private int capacity = 8;
    private int index;
    private int[] indexes;
    private Entry[] values;
    private int mask;

    public Dictionary() {
        indexes = new int[capacity];
        values = new Entry[(int) Math.round(capacity * (2.0/3))];
        index = 0;
        mask = capacity - 1;
    }

    public void addElement(Entry value) {
        int pseudoKey = value.getHash() & mask;
        if(indexes[pseudoKey] == 0) {
            indexes[pseudoKey] = ++index;
            values[index - 1] = value;
        } else {
            int tempIndex = indexes[pseudoKey];
            if(values[tempIndex - 1].getHash() == value.getHash()) {
                values[tempIndex - 1] = value;
            } 
        }
    }

    public Object getElement(Object key) throws Exception {
        int pseudoKey = key.hashCode() & mask;
        int tempIndex = indexes[pseudoKey];
        if(tempIndex == 0) {
            throw new Exception("Key not found");
        }
        return values[tempIndex - 1];
    }
}

