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
        setDictionary(capacity);   
    }

    private void setDictionary(int newCapacity) {
        indexes = new int[newCapacity];
        Arrays.fill(indexes, UNUSED);
        values = new Entry[(int) Math.round(newCapacity * (2.0/3))];
        index = 0;
        mask = newCapacity - 1;
    }

    private void resize() {
        Entry[] tempValues = values;
        setDictionary(values.length << 2);
        for(int i = 0; i < tempValues.length; i++) {
            if(tempValues[i] != null) {
                addElement(tempValues[i]);
            }
        }
    }

    public void addElement(Entry newEntry) {
        int hash = newEntry.getHash();
        int pseudoKey = hash & mask;
        boolean stay = true;
        while(stay) {
            if(indexes[pseudoKey] == UNUSED) {
                if(index == values.length) {
                    resize();
                    addElement(newEntry);
                } else {
                    indexes[pseudoKey] = index;
                    values[index] = newEntry;
                    index++;
                }
                stay = false;
            } else {
                if(values[indexes[pseudoKey]].getHash() == newEntry.getHash()) {
                    values[indexes[pseudoKey]] = newEntry;
                    stay = false;
                }
                hash >>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
            }
        }
    }

    public Object getElement(Object key) {
        int hash = key.hashCode();
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        int tempIndex = indexes[pseudoKey];
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                throw new RuntimeException("Key not found");
            }
            if(values[tempIndex].getHash() == keyHash) {
                stay = false;
            } else {
                hash >>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
                tempIndex = indexes[pseudoKey];            }
        }
        
        return values[tempIndex];
    }

    public void deleteElement(Object key) {
        int hash = key.hashCode();
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        int tempIndex = indexes[pseudoKey];
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                throw new RuntimeException("Key not found");
            }
            if(values[tempIndex].getHash() == keyHash) {
                indexes[pseudoKey] = DUMMY;
                values[tempIndex] = null;
                stay = false;
            } else {
                hash >>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
                tempIndex = indexes[pseudoKey];            
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Entry temp;
        for(int i = 0; i < index - 1; i++) {
            temp = values[i];
            if(temp != null) {
                if(temp.getKey() instanceof Number) {
                    sb.append(temp.getKey() + ": ");
                } else {
                    sb.append(String.format("'%s': ", temp.getKey()));
                }

                if(temp.getValue() instanceof Number) {
                    sb.append(temp.getValue() + ", ");
                } else {
                    sb.append(String.format("'%s', ", temp.getValue()));
                }
            }
        }
        temp = values[index - 1];
        if(temp.getKey() instanceof Number) {
            sb.append(temp.getKey() + ": ");
        } else {
            sb.append(String.format("'%s': ", temp.getKey()));
        }

        if(temp.getValue() instanceof Number) {
            sb.append(temp.getValue() + "");
        } else {
            sb.append(String.format("'%s'", temp.getValue()));
        }
        sb.append("}");

        return sb.toString();
    }
}

