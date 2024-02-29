public class Dictionary {
    private final Object UNUSED = null;
    private final Integer DUMMY = -2;
    private final int initialCapacity = 8;
    private int index;
    private Integer[] indexes;
    private ValuesList values;
    private int mask;
    private int n_entries;

    public Dictionary() {
        setDictionary(initialCapacity);   
    }

    private void setDictionary(int newCapacity) {
        indexes = new Integer[newCapacity];
        values = new ValuesList(new Entry[(int) Math.round(newCapacity * (2.0/3))]);
        System.out.println(values.len());
        index = 0;
        mask = newCapacity - 1;
        n_entries = 0;
    }

    public void clear() {
        setDictionary(initialCapacity);
    }

    private void resize() {
        Entry[] tempValues = values.getAll();
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
                if(index == values.len()) {
                    resize();
                    addElement(newEntry);
                } else {
                    indexes[pseudoKey] = index;
                    values.add(index, newEntry);
                    index++;
                    n_entries++;
                }
                stay = false;
            } else {
                if(indexes[pseudoKey] != DUMMY && values.get(indexes[pseudoKey]).getHash() == newEntry.getHash()) {
                    values.add(indexes[pseudoKey], newEntry);
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
            if(tempIndex != DUMMY && values.get(tempIndex).getHash() == keyHash) {
                stay = false;
            } else {
                hash >>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
                tempIndex = indexes[pseudoKey];
            }
        }
        return values.get(tempIndex);
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
            if(values.get(tempIndex).getHash() == keyHash) {
                indexes[pseudoKey] = DUMMY;
                values.delete(tempIndex);
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
            temp = values.get(i);
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
                if(values.get(i + 1) != null) {
                    sb.append(", ");
                }
            }
        }
        temp = values.get(index - 1);
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

    public Entry popitem() {
        if (n_entries == 0) {
            throw new RuntimeException("Dictionary is empty.");
        }
        int i = index;
        while (values.get(i) == null) {i--;}
        Entry item = values.get(i);
        deleteElement(item.getKey());
        return item;
    }
}

