import java.util.Iterator;
import java.util.Map;

public class Dictionary implements Iterable<Object>{
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

    private int getNextIndex(int index, int hash) {
        return (index * 5 + hash + 1) & mask;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Keys(values, this).iterator();
    }

    public Iterable<Object> reversed() {
        return new Keys(values, this).reversed();
    }

    private void addElement(Entry newEntry) {
        int hash = newEntry.getHash();
        int pseudoKey = hash & mask;
        boolean stay = true;
        Integer temp;
        while(stay) {
            temp = indexes.get(pseudoKey);
            if(temp == UNUSED) {
                if(index == values.capacity()) {
                    resize();
                    addElement(newEntry);
                } else {
                    indexes.put(pseudoKey, index);
                    values.add(newEntry);
                    index++;
                }
                stay = false;
            }else if(temp == DUMMY) {
                indexes.put(pseudoKey, index);
                values.add(newEntry);
                index++;
            }else if(
                    values.get(temp).getHash() == newEntry.getHash() 
                    && values.get(temp).getKey().equals(newEntry.getKey())
                ) {
                values.replace(temp, newEntry);
                stay = false;
            }else {
                hash >>>= 5;
                pseudoKey = getNextIndex(pseudoKey, hash);
            }
        }
    }

    public int length() {
        return values.size();
    }

    private int getElement(Object key) {
        int hash = key.hashCode();
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        Integer tempIndex = indexes.get(pseudoKey);
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                return -1;
            }
            if(
                tempIndex != DUMMY 
                && values.get(tempIndex).getHash() == keyHash
                && values.get(tempIndex).getKey().equals(key)
                ) {
                stay = false;
            } else {
                hash >>>= 5;
                pseudoKey = getNextIndex(pseudoKey, hash);
                tempIndex = indexes.get(pseudoKey);
            }
        }
        return tempIndex;
    }

    private int deleteElement(Object key) {
        int hash = key.hashCode();
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        Integer tempIndex = indexes.get(pseudoKey);
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                return -1;
            }
            if(
                tempIndex != DUMMY 
                && values.get(tempIndex).getHash() == keyHash
                && values.get(tempIndex).getKey().equals(key)
                ) {
                indexes.put(pseudoKey, DUMMY);
                values.delete(tempIndex);
                stay = false;
            } else {
                hash >>>= 5;
                pseudoKey = getNextIndex(pseudoKey, hash);
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
        for(int i = 0; i < index; i++) {
            temp = values.get(i);
            if(temp != null) {
                if(sb.length() != 1) sb.append(", ");
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
            }
        }
        
        sb.append("}");

        return sb.toString();
    }

    public Values values() {
        return new Values(values);
    }

    public Keys keys() {
        return new Keys(values, this);
    }

    public Items items() {
        return new Items(values, this);
    }

    public void clear() {
        setDictionary(initialCapacity);
    }

    public boolean contains(Object key) {
        return getElement(key) == -1 ? false : true;
    }

    private static int getUpperPowerOfTwo(int v) {
        v--;
        v |= v >> 1;
        v |= v >> 2;
        v |= v >> 4;
        v |= v >> 8;
        v |= v >> 16;
        v++;
        return v;
    }

    /**
     * Este método realiza la copia de un diccionario (con claves y valores).
     * 
     * @return La copia del diccionario.
     */
    public Dictionary copy() {
        Dictionary dicTemp = new Dictionary();
        //TODO: Añadir código para decidir si múltiplico getUpperOfTwo
        dicTemp.setDictionary(getUpperPowerOfTwo(values.size()) << 1);
        Entry currentEntry;
        for(int i = 0; i < index; i++) {
            currentEntry = values.get(i);
            if(currentEntry != null) {
                dicTemp.addElement(new Entry(currentEntry.getItem(), currentEntry.getHash()));
            }
        }
        return dicTemp;
    }

    /**
     * El método elimina el último elemento del diccionario y lo devuelve.
     * 
     * @return Devuelve el conjunto (clave, valor).
     */
    public Pair popitem() {
        if (values.size() == 0) {
            throw new KeyError("Dictionary is empty");
        }
        int i = index;
        while (values.get(i) == null) {i--;}
        Entry item = values.get(i);
        deleteElement(item.getKey());
        return item.getItem();
    }

    public void put(Object key, Object value) {
        addElement(new Entry(key, value));
    }

    public void put(Pair p) {
        addElement(new Entry(p));
    }

    public void update(Iterable<Pair> pairs) {
        for(Pair p : pairs) {
            addElement(new Entry(p));
        }
    }

    public void update(Pair[] pairs) {
        for(Pair p : pairs) {
            addElement(new Entry(p));
        }
    }

    public void update(Dictionary dict) {
        for(Pair p : dict.items()) {
            addElement(new Entry(p));
        }
    }

    public void update(Map<?, ?> m) {
        for(Map.Entry<?, ?> e : m.entrySet()) {
            addElement(new Entry(e.getKey(), e.getValue()));
        }
    }

    public Object get(Object key) {
        int valueIndex = getElement(key);
        if(valueIndex == -1) throw new KeyError(key);
        return values.get(valueIndex).getValue();
    }

    public Object get(Object key, Object d) {
        int valueIndex = getElement(key);
        if(valueIndex == -1) return d;
        return values.get(valueIndex).getValue();
    }

    public void del(Object key) {
        if(deleteElement(key) != 0) throw new KeyError(key);
    }

    public Object pop(Object key) {
        int valueIndex = getElement(key);
        if(valueIndex == -1) throw new KeyError(key);
        Object v = values.get(valueIndex).getValue();
        deleteElement(key);
        return v;
    }

    public Object pop(Object key, Object d) {
        int valueIndex = getElement(key);
        if(valueIndex == -1) return d;
        Object v = values.get(valueIndex).getValue(); 
        deleteElement(key);
        return v;
    }

    public Object setdefault(Object key, Object d) {
        int valueIndex = getElement(key);
        if(valueIndex == -1) {
            put(key, d);
            return d;
        }
        return values.get(valueIndex).getValue(); 
    }

    public Object setdefault(Object key) {
        int valueIndex = getElement(key);
        if(valueIndex == -1) {
            put(key, null);
            return null;
        }
        return values.get(valueIndex).getValue(); 
    }

    public static Dictionary fromkeys(Object[] keys, Object value) {
        Dictionary newDict = new Dictionary();
        newDict.setDictionary(getUpperPowerOfTwo(keys.length) << 1);
        for(Object k : keys) {
            newDict.addElement(new Entry(k, value));
        }
        return newDict;
    }

    public static Dictionary fromkeys(Iterable<Object> keys, Object value) {
        Dictionary newDict = new Dictionary();
        for(Object k : keys) {
            newDict.addElement(new Entry(k, value));
        }
        return newDict;
    }

    public static Dictionary fromkeys(Object[] keys) {
        Dictionary newDict = new Dictionary();
        newDict.setDictionary(getUpperPowerOfTwo(keys.length) << 1);
        for(Object k : keys) {
            newDict.addElement(new Entry(k, null));
        }
        return newDict;
    }

    public static Dictionary fromkeys(Iterable<Object> keys) {
        Dictionary newDict = new Dictionary();
        for(Object k : keys) {
            newDict.addElement(new Entry(k, null));
        }
        return newDict;
    }

    public Dictionary merge(Dictionary other) {
        Dictionary newDict = this.copy();
        for(Pair p : other.items()) {
            newDict.addElement(new Entry(p));
        }
        return newDict;
    }
}