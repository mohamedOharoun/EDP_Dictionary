public class Dictionary {
    private final Object UNUSED = null;
    private final Integer DUMMY = -2;
    private final int initialCapacity = 8;
    private int index;
    private final IndexesList indexes = new IndexesList(null);
    private final ValuesList values = new ValuesList(null);
    private int mask;

    public Dictionary() {
        setDictionary(initialCapacity);   
    }

    private void setDictionary(int newCapacity) {
        indexes.setArray(new Integer[newCapacity]);
        values.setArray(new Entry[(int) Math.round(newCapacity * (2.0/3))]);
        index = 0;
        mask = newCapacity - 1;
    }

    private void resize() {
        Entry[] tempValues = values.getAll();
        setDictionary(indexes.length() << 1);
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
            if(indexes.get(pseudoKey) == UNUSED) {
                if(index == values.capacity()) {
                    resize();
                    addElement(newEntry);
                } else {
                    indexes.put(pseudoKey, index);
                    values.add(newEntry);
                    index++;
                }
                stay = false;
            } else {
                if(indexes.get(pseudoKey) != DUMMY && values.get(indexes.get(pseudoKey)).getHash() == newEntry.getHash()) {
                    values.add(indexes.get(pseudoKey), newEntry);
                    stay = false;
                }
                hash >>>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
            }
        }
    }

    public int length() {
        return values.size();
    }

    public Object getElement(Object key) {
        int hash = key.hashCode();
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        Integer tempIndex = indexes.get(pseudoKey);
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                throw new RuntimeException("Key not found");
            }
            if(tempIndex != DUMMY && values.get(tempIndex).getHash() == keyHash) {
                stay = false;
            } else {
                hash >>>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
                tempIndex = indexes.get(pseudoKey);
            }
        }
        return values.get(tempIndex);
    }

    public int deleteElement(Object key) {
        int hash = key.hashCode();
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        Integer tempIndex = indexes.get(pseudoKey);
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                return -1;
            }
            if(tempIndex != DUMMY && values.get(tempIndex).getHash() == keyHash) {
                indexes.put(pseudoKey, DUMMY);
                values.delete(tempIndex);
                stay = false;
            } else {
                hash >>>= 5;
                pseudoKey = (pseudoKey * 5 + hash + 1) & mask;
                tempIndex = indexes.get(pseudoKey);            
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Entry temp;
        for(int i = 0; i < index - 1; i++) {
            temp = values.get(i);
            if(temp != null) {
                if(temp.getKey() instanceof String || temp.getKey() instanceof Character) {
                    sb.append(String.format("'%s': ", temp.getKey()));
                } else {
                    sb.append(temp.getKey() + ": ");
                }

                if(temp.getValue() instanceof String || temp.getValue() instanceof Character) {
                    sb.append(String.format("'%s'", temp.getValue()));
                } else {
                    sb.append(temp.getValue() + "");
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

    public Values values() {
        return new Values(values);
    }

    public Keys keys() {
        return new Keys(indexes, values);
    }

    public void clear() {
        setDictionary(initialCapacity);
    }

    private int getUpperPowerOfTwo(int v) {
        v--;
        v |= v >> 1;
        v |= v >> 2;
        v |= v >> 4;
        v |= v >> 8;
        v |= v >> 16;
        v++;
        return v;
    }

    public Dictionary copy() {
        Dictionary dicTemp = new Dictionary();
        dicTemp.setDictionary(getUpperPowerOfTwo(values.size()) << 1);
        Entry currentEntry;
        for(int i = 0; i < index; i++) {
            currentEntry = values.get(i);
            if(currentEntry != null) {
                dicTemp.addElement(new Entry(currentEntry.getKey(), currentEntry.getValue(), currentEntry.getHash()));
            }
        }
        return dicTemp;
    }

    public Entry popitem() {
        if (values.size() == 0) {
            throw new RuntimeException("Dictionary is empty.");
        }
        int i = index;
        while (values.get(i) == null) {i--;}
        Entry item = values.get(i);
        deleteElement(item.getKey());
        return item;
    }
}

