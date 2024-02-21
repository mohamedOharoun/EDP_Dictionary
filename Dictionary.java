import java.util.Arrays;

public class Dictionary {
    private final int UNUSED = -1;
    private final int DUMMY = -2;
    private int capacity = 8;
    private int index;
    private int[] indexes;
    private Entry[] values;
    private int mask;

    public Dictionary() {
        indexes = new int[capacity];
        Arrays.fill(indexes, UNUSED);
        values = new Entry[(int) Math.round(capacity * (2.0/3))];
        index = 0;
        mask = capacity - 1;
    }

    public void addElement(Entry newEntry) {
        int hash = newEntry.getHash();
        int pseudoKey = hash & mask;
        boolean stay = true;
        while(stay) {
            if(indexes[pseudoKey] == UNUSED) {
                indexes[pseudoKey] = index;
                values[index] = newEntry;
                index++;
                stay = false;
            } else {
                if(values[indexes[pseudoKey]].getHash() == newEntry.getHash()) {
                    values[indexes[pseudoKey]] = newEntry;
                }
                hash >>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
            }
        }
    }

    public Object getElement(Object key) throws Exception {
        int pseudoKey = key.hashCode() & mask;
        int tempIndex = indexes[pseudoKey];
        if(tempIndex == UNUSED) {
            throw new Exception("Key not found");
        }
        return values[tempIndex];
    }
}

