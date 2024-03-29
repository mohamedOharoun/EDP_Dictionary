import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents an implementation of the Python dict class. Keys that are not suitable for hashing may throw a TypeError exception.
 * 
 * @author Álvaro Rodríguez Miranda
 * @author Guillermo Santana Suárez
 * @author Alejandro De Olózoga Ramírez
 * @author Mohamed O. Haroun Zarkik
 */
public class Dictionary implements Iterable<Object> {
    private final static Object UNUSED = null;
    private final static Integer DUMMY = -2;
    private final static double GROWTH_RATE = 2.0/3;
    private final static int INITIAL_CAPACITY = 8;
    private final IndexesList indexes = new IndexesList(null);
    private final EntriesList entries = new EntriesList(null);
    private int mask;
    private int index;

    /**
     * Default constructor. Creates an empty dictionary with initial capacity.
     */
    public Dictionary() {
        setDictionary(INITIAL_CAPACITY);   
    }

    /**
     * Constructor that initializes the dictionary from an array of Pair objects.
     *
     * @param ps Array of Pair objects.
     */
    public Dictionary(Pair[] ps)  {
        this.setDictionary(calculateProperSize(ps.length));
        this.update(ps);
    }

    /**
     * Constructor that initializes the dictionary from an iterable of Pair objects.
     *
     * @param iter Iterable of Pair objects.
     */
    public Dictionary(Iterable<Pair> iter)  {
        this.setDictionary(calculateProperSize(calculateIterableSize(iter)));
        this.update(iter);
    }

    /**
     * Constructor that initializes the dictionary from a Map object.
     *
     * @param m Map object containing key-value pairs.
     */
    public Dictionary(Map<?, ?> m)  {
        this.setDictionary(calculateProperSize(m.size()));
        this.update(m);
    }

    /**
     * Constructor that initializes the dictionary from another Dictionary object.
     *
     * @param d Another Dictionary object to copy.
     */
    public Dictionary(Dictionary d)  {
        this.setDictionary(calculateProperSize(d.length()));
        this.update(d);
    }

    private Dictionary(int n) {
        setDictionary(n);
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return The number of entries.
     */
    public int length() {
        return entries.size();
    }

    private void setDictionary(int newCapacity) {
        indexes.setArray(new Integer[newCapacity]);
        entries.setArray(new Entry[(int) Math.round(newCapacity * GROWTH_RATE)]);
        index = 0;
        mask = newCapacity - 1;
    }

    private void resize() {
        Entry[] tempValues = entries.getAll();
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

    private void addElement(Entry newEntry) {
        if(isHashable(newEntry.getKey())) throw new TypeError(newEntry.getKey());
        int hash = newEntry.getHash();
        int pseudoKey = hash & mask;
        boolean stay = true;
        boolean isDelete = false;
        int deletePosition = -1;
        Integer temp;
        while(stay) {
            temp = indexes.get(pseudoKey);
            if(temp == UNUSED) {
                if(index == entries.capacity()) {
                    resize();
                    addElement(newEntry);
                } else {
                    if(isDelete) {
                        indexes.put(deletePosition, index);
                    } else {
                        indexes.put(pseudoKey, index);
                    }
                    entries.add(newEntry);
                    index++;
                }
                stay = false;
            }else if(
                    temp != DUMMY
                    && entries.get(temp).getHash() == newEntry.getHash() 
                    && entries.get(temp).getKey().equals(newEntry.getKey())
                ) {
                entries.replace(temp, newEntry);
                stay = false;
            }else {
                if(temp == DUMMY) {
                    isDelete = true;
                    deletePosition = pseudoKey;
                }
                hash >>>= 5;
                pseudoKey = getNextIndex(pseudoKey, hash);
            }
        }
    }

    private boolean isHashable(Object key) {
        if(
            key == null ||
            key instanceof List ||
            key instanceof Set ||
            key instanceof Map ||
            key instanceof Dictionary ||
            key.getClass().isArray()            
        ) return true;
        return false;
    }

    private int[] getElement(Object key) {
        if(isHashable(key)) throw new TypeError(key);
        int hash = key.hashCode();
        final int keyHash = hash;
        int pseudoKey = hash & mask;
        Integer tempIndex = indexes.get(pseudoKey);
        boolean stay = true;
        while (stay) {
            if(tempIndex == UNUSED) {
                return new int[]{-1, -1};
            }
            if(
                tempIndex != DUMMY 
                && entries.get(tempIndex).getHash() == keyHash
                && entries.get(tempIndex).getKey().equals(key)
                ) {
                stay = false;
            } else {
                hash >>>= 5;
                pseudoKey = getNextIndex(pseudoKey, hash);
                tempIndex = indexes.get(pseudoKey);
            }
        }
        return  new int[]{tempIndex, pseudoKey};
    }

    private int deleteElement(Object key) {
        int[] positions = getElement(key);
        if(positions[0] == -1) return -1;
        indexes.put(positions[1], DUMMY);
        entries.delete(positions[0]);
        return 0;
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

    private static int calculateProperSize(int n) {
        int new_size = getUpperPowerOfTwo(n);
        if(new_size * GROWTH_RATE < n) new_size <<= 1;
        if(new_size <= INITIAL_CAPACITY) return INITIAL_CAPACITY;
        return new_size;
    }

    private static int calculateIterableSize(Iterable<?> iter) {
        if(iter instanceof Collection) {
            return ((Collection<?>) iter).size();
        } else {
            int size = 0;
            for(@SuppressWarnings("unused") Object i : iter) size++;
            return size;
        }
    }

    /**
     * This method creates a copy of the dictionary, original and copy are different objects.
     *
     * @return A copy of the dictionary.
     */
    public Dictionary copy() {
        Dictionary dicTemp = new Dictionary(calculateProperSize(entries.size()));
        Entry currentEntry;
        for(int i = 0; i < index; i++) {
            currentEntry = entries.get(i);
            if(currentEntry != null) {
                dicTemp.addElement(new Entry(currentEntry.getItem(), currentEntry.getHash()));
            }
        }
        return dicTemp;
    }

    /**
     * This method removes and returns the last element of the dictionary.
     *
     * @return The last element of the dictionary (key-value pair).
     */
    public Pair popitem() {
        if (entries.size() == 0) {
            throw new KeyError("Dictionary is empty");
        }
        int i = index;
        while (entries.get(i) == null) {i--;}
        Entry item = entries.get(i);
        deleteElement(item.getKey());
        return item.getItem();
    }

    /**
     * This method inserts a new element into the dictionary.
     *
     * @param key The key of the new element.
     * @param value The value of the new element.
     */
    public void put(Object key, Object value) {
        if(isHashable(key)) throw new TypeError(null);
        addElement(new Entry(key, value));
    }

    /**
     * This method inserts a new element into the dictionary using a Pair object.
     *
     * @param p Pair object representing the key-value pair.
     */
    public void put(Pair p) {
        if(isHashable(p.get(0))) throw new TypeError(null);
        addElement(new Entry(p));
    }

    /**
     * This method inserts elements into the dictionary from an iterable of Pair objects, or other iterable objects (or Array) of strictly size 2.
     *
     * @param pairs Iterable of Pair objects or Iterable objects of size 2.
     * @throws ValueError if any of the elements of the iterable is neither a Pair object nor an Iterable or Array of size 2.
     */
    public void update(Iterable<?> pairs) {
        int i = 0;
        for(Object p : pairs) {
            if(p == null) throw new ValueError(i, 1);
            else if(p instanceof Pair) addElement(new Entry((Pair) p));
            else if(p instanceof Iterable) {
                Iterable<?> temp = (Iterable<?>) p;
                final int lengthSequence = calculateIterableSize(temp);
                if(lengthSequence != 2) throw new ValueError(i, lengthSequence);
                Iterator<?> temp2 = temp.iterator();
                put(temp2.next(), temp2.next());
                System.out.println(this);
            } 
            else if(p.getClass().isArray()) {
                Object[] temp = (Object[]) p;
                if(temp.length != 2) throw new ValueError(i, temp.length);
                put(temp[0], temp[1]);
            }
            else {
                throw new ValueError(i, 1);
            }
            i++;
        }
    }

    /**
     * This method inserts elements into the dictionary from an array of Pair objects.
     *
     * @param pairs Array of Pair objects.
     */
    public void update(Pair[] pairs) {
        for(Pair p : pairs) {
            addElement(new Entry(p));
        }
    }

    /**
     * This method inserts elements into the dictionary from another Dictionary object.
     *
     * @param dict Another Dictionary object.
     */
    public void update(Dictionary dict) {
        for(Pair p : dict.items()) {
            addElement(new Entry(p));
        }
    }

    /**
     * This method inserts elements into the dictionary from a Map object.
     *
     * @param m Map object containing key-value pairs.
     */
    public void update(Map<?, ?> m) {
        for(Map.Entry<?, ?> e : m.entrySet()) {
            addElement(new Entry(e.getKey(), e.getValue()));
        }
    }

    /**
     * This method retrieves the value associated with a given key from the dictionary.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     * @throws KeyError if the key is not found in the dictionary.
     */
    public Object retrieve(Object key) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) {
            if(this instanceof Missing) return ((Missing)this).missing(key);
            throw new KeyError(key);
        }
        return entries.get(valueIndex).getValue();
    }

    /**
     * This method retrieves the value associated with a given key from the dictionary.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key, or null if the key is not found.
     */
    public Object get(Object key) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) return null;
        return entries.get(valueIndex).getValue();
    }

    /**
     * This method retrieves the value associated with a given key from the dictionary, or returns a default value if the key is not found.
     *
     * @param key The key to retrieve the value for.
     * @param d The default value to return if the key is not found.
     * @return The value associated with the key, or the default value if the key is not found.
     */
    public Object get(Object key, Object d) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) return d;
        return entries.get(valueIndex).getValue();
    }

    /**
     * This method deletes the entry with the specified key from the dictionary.
     *
     * @param key The key of the entry to delete.
     * @throws KeyError if the key is not found in the dictionary.
     */
    public void del(Object key) {
        if(deleteElement(key) != 0) throw new KeyError(key);
    }

    /**
     * This method removes the entry with the specified key from the dictionary and returns its value.
     *
     * @param key The key of the entry to remove.
     * @return The value of the removed entry.
     * @throws KeyError if the key is not found in the dictionary.
     */
    public Object pop(Object key) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) throw new KeyError(key);
        Object v = entries.get(valueIndex).getValue();
        deleteElement(key);
        return v;
    }

    /**
     * This method removes the entry with the specified key from the dictionary and returns its value, or returns a default value if the key is not found.
     *
     * @param key The key of the entry to remove.
     * @param d The default value to return if the key is not found.
     * @return The value of the removed entry, or the default value if the key is not found.
     */
    public Object pop(Object key, Object d) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) return d;
        Object v = entries.get(valueIndex).getValue(); 
        deleteElement(key);
        return v;
    }

    /**
     * This method inserts a key-value pair into the dictionary if the key does not exist with the specified value, and returns the corresponding value.
     * If the key already exists, returns its associated value.
     *
     * @param key The key of the entry to insert.
     * @param d The default value to insert if the key does not exist.
     * @return The value associated with the key.
     */
    public Object setdefault(Object key, Object d) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) {
            put(key, d);
        }
        return entries.get(getElement(key)[0]).getValue(); 
    }

    /**
     * This method inserts a key-value pair into the dictionary if the key does not exist with null as a value, and returns the corresponding value.
     * If the key already exists, returns its associated value.
     *
     * @param key The key of the entry to insert.
     * @return The value associated with the key.
     */
    public Object setdefault(Object key) {
        return setdefault(key, null); 
    }

    /**
     * This method creates a new dictionary with the specified keys and a default value.
     *
     * @param keys Array of keys for the new dictionary.
     * @param value The default value to associate with the keys.
     * @return A new dictionary with the specified keys and default value.
     */
    public static Dictionary fromkeys(Object[] keys, Object value) {
        Dictionary newDict = new Dictionary(calculateProperSize(keys.length));
        for(Object k : keys) {
            newDict.addElement(new Entry(k, value));
        }
        return newDict;
    }

    /** Este método crea un diccionario con elementos nuevos.
     * 
     * @param keys Las claves de los nuevos elementos.
     * @param value Los valores de los nuevos elementos.
     * @return El nuevo diccionario.
     */
    public static Dictionary fromkeys(Iterable<?> keys, Object value) {
        Dictionary newDict = new Dictionary(calculateProperSize(calculateIterableSize(keys)));
        for(Object k : keys) {
            newDict.addElement(new Entry(k, value));
        }
        return newDict;
    }

    /**
     * This method creates a new dictionary with the specified keys and null values.
     *
     * @param keys Array of keys for the new dictionary.
     * @return A new dictionary with the specified keys and null values.
     */
    public static Dictionary fromkeys(Object[] keys) {
        return fromkeys(keys, null);
    }

    /**
     * This method creates a new dictionary with the specified keys and null values.
     *
     * @param keys Iterable of keys for the new dictionary.
     * @return A new dictionary with the specified keys and null values.
     */
    public static Dictionary fromkeys(Iterable<?> keys) {
        return fromkeys(keys, null);
    }

    /**
     * This method creates a new dictionary by merging the current dictionary with another dictionary. If a key is present in both dictionaries, the value associated is from the other dictionary.
     *
     * @param other Another dictionary to merge.
     * @return A new dictionary containing the merged key-value pairs.
     */
    public Dictionary merge(Dictionary other) {
        Dictionary newDict = this.copy();
        for(Pair p : other.items()) {
            newDict.addElement(new Entry(p));
        }
        return newDict;
    }

    /**
     * This method returns a list of keys in the dictionary.
     *
     * @return A list containing the keys of the dictionary.
     */
    public List<Object> list() {
        List<Object> keys = new ArrayList<>(entries.size());
        Object temp;
        for(int i = 0; i < index; i++) {
            temp = entries.get(i).getKey();
            if(temp != null) keys.add(temp);
        }
        return keys;
    }

    /**
     * This method compares the current dictionary with another object for equality. Two dictionary are equal if every key-value pair is the same, the order is not important.
     *
     * @param other The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        if(!(other instanceof Dictionary)) return false;
        Dictionary o = (Dictionary) other;
        if(this.length() != o.length()) return false;
        int i;
        for(Object k : this.keys()) {
            i = o.getElement(k)[0];
            if(i == -1) return false;
            if(!this.get(k).equals(o.entries.get(i).getValue())) return false;
        }
        return true;
    }

    /**
     * This method returns a string representation of the dictionary. Elements that are a String or a Character have simple quotes.
     *
     * @return A string representation of the dictionary.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Entry temp;
        for(int i = 0; i < index; i++) {
            temp = entries.get(i);
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

    /**
     * This method returns a Values object for accessing the values of the dictionary, which implements ObjectView.
     *
     * @return A Values object.
     */
    public Values values() {
        return new Values(entries);
    }

    /**
     * This method returns a Keys object for accessing the keys of the dictionary, which implements ObjectView.
     *
     * @return A Keys object.
     */
    public Keys keys() {
        return new Keys(entries, this);
    }

    /**
     * This method returns an Items object for accessing the key-value pairs of the dictionary, which implements ObjectView.
     *
     * @return An Items object.
     */
    public Items items() {
        return new Items(entries, this);
    }

    /**
     * This method removes all entries from the dictionary.
     */
    public void clear() {
        setDictionary(INITIAL_CAPACITY);
    }

    /**
     * This method checks if a key is present in the dictionary.
     *
     * @param key The key to check for.
     * @return true if the key is present, false otherwise.
     */
    public boolean contains(Object key) {
        return getElement(key)[0] == -1 ? false : true;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Keys(entries, this).iterator();
    }

    /**
     * This method returns an iterable object for iterating over the dictionary keys in reverse order.
     *
     * @return An iterable object for iterating over the dictionary in reverse order.
     */
    public Iterable<Object> reversed() {
        return new Keys(entries, this).reversed();
    }
}