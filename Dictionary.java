public class Dictionary {
    private final Object UNUSED = null;
    private final Integer DUMMY = -2;
    private final int initialCapacity = 8;
    private int index;
    private Integer[] indexes;
    private Entry[] values;
    private int mask;
    private int n_entries;

    public Dictionary() {
        setDictionary(initialCapacity);   
    }

    private void setDictionary(int newCapacity) {
        indexes = new Integer[newCapacity];
        values = new Entry[(int) Math.round(newCapacity * (2.0/3))];
        index = 0;
        mask = newCapacity - 1;
        n_entries = 0;
    }

    public void clear() {
        setDictionary(initialCapacity);
    }

    private void resize() {
        Entry[] tempValues = values;
        setDictionary(indexes.length << 1);
        for(int i = 0; i < tempValues.length; i++) {
            if(tempValues[i] != null) {
                addElement(tempValues[i]);
            }
        }
    }

    public void addElement(Entry newEntry) {
        int hash = Math.abs(newEntry.getHash());
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
                    n_entries++;
                }
                stay = false;
            } else {
                if(indexes[pseudoKey] != DUMMY && values[indexes[pseudoKey]].getHash() == newEntry.getHash()) {
                    values[indexes[pseudoKey]] = newEntry;
                    stay = false;
                }
                hash >>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
            }
        }
    }

    public int length() {
        return n_entries;
    }

    public Object getElement(Object key) {
        int hash = Math.abs(key.hashCode());
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        Integer tempIndex = indexes[pseudoKey];
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                throw new RuntimeException("Key not found");
            }
            if(tempIndex != DUMMY && values[tempIndex].getHash() == keyHash) {
                stay = false;
            } else {
                hash >>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
                tempIndex = indexes[pseudoKey];

            }
        }
        return values[tempIndex];
    }

    public void deleteElement(Object key) {
        int hash = key.hashCode();
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        Integer tempIndex = indexes[pseudoKey];
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                throw new RuntimeException("Key not found");
            }
            if(values[tempIndex].getHash() == keyHash) {
                indexes[pseudoKey] = DUMMY;
                values[tempIndex] = null;
                stay = false;
                n_entries--;
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
                    sb.append(temp.getValue() + "");
                } else {
                    sb.append(String.format("'%s'", temp.getValue()));
                }
                if(values[i+1] != null) {
                    sb.append(", ");
                }
            }
        }
        temp = values[index - 1];
        if(temp != null) {
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
        }
        
        sb.append("}");

        return sb.toString();
    }
}

