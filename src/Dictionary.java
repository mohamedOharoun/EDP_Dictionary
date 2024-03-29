import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dictionary implements Iterable<Object> {
    private final static Object UNUSED = null;
    private final static Integer DUMMY = -2;
    private final static double GROWTH_RATE = 2.0/3;
    private final static int INITIAL_CAPACITY = 8;
    private final IndexesList indexes = new IndexesList(null);
    private final EntriesList entries = new EntriesList(null);
    private int mask;
    private int index;

    public Dictionary() {
        setDictionary(INITIAL_CAPACITY);   
    }

    public Dictionary(Pair[] ps)  {
        this.setDictionary(calculateProperSize(ps.length));
        this.update(ps);
    }

    public Dictionary(Iterable<Pair> iter)  {
        this.setDictionary(calculateProperSize(calculateIterableSize(iter)));
        this.update(iter);
    }

    public Dictionary(Map<?, ?> m)  {
        this.setDictionary(calculateProperSize(m.size()));
        this.update(m);
    }

    public Dictionary(Dictionary d)  {
        this.setDictionary(calculateProperSize(d.length()));
        this.update(d);
    }

    private Dictionary(int n) {
        setDictionary(n);
    }

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
     * Este método realiza la copia de un diccionario (con claves y valores).
     * 
     * @return La copia del diccionario.
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
     * El método elimina el último elemento del diccionario y lo devuelve.
     * 
     * @return El último elemento del diccionario (clave, valor).
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
     * Este método inserta un nuevo elemento al diccionario.
     * 
     * @param key La clave del nuevo elemento.
     * @param value El valor del nuevo elemento.
     */
    public void put(Object key, Object value) {
        addElement(new Entry(key, value));
    }

    /**
     * Este método inserta al diccionario un nuevo elemento de tipo Pair.
     * @param p Objeto de tipo Pair.
     */
    public void put(Pair p) {
        addElement(new Entry(p));
    }

    /**
     * Este método inserta al diccionario elementos a partir de un Objeto iterable de tipo Pair.
     * 
     * @param pairs El Array de elementos de tipo Pair.
     */
    public void update(Iterable<Pair> pairs) {
        for(Pair p : pairs) {
            addElement(new Entry(p));
        }
    }

    /**
     * Este método inserta al diccionario elementos a partir de un Pair[].
     * 
     * @param pairs Pair[] que integra elementos de tipo Pair.
     */
    public void update(Pair[] pairs) {
        for(Pair p : pairs) {
            addElement(new Entry(p));
        }
    }

    /**
     * Este método inserta al diccionario los elementos de otro diccionario.
     * 
     * @param dict El diccionario del cual insertar los elementos.
     */
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

    /**
     * Este método busca el valor de una clave en el diccionario.
     * 
     * @param key La clave que se quiere buscar.
     * @return El valor de la clave.
     */
    public Object retrieve(Object key) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) {
            if(this instanceof Missing) return ((Missing)this).missing(key);
            throw new KeyError(key);
        }
        return entries.get(valueIndex).getValue();
    }

    public Object get(Object key) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) return null;
        return entries.get(valueIndex).getValue();
    }

    public Object get(Object key, Object d) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) return d;
        return entries.get(valueIndex).getValue();
    }

    /**
     * Este método elimina del diccionario el elemento asociado a una clave.
     * 
     * @param key La clave del elemento que se quiera eliminar.
     */
    public void del(Object key) {
        if(deleteElement(key) != 0) throw new KeyError(key);
    }

    /**
     * Este método elimina el elemento asociado a una clave.
     * 
     * @param key La clave asociada al elemento que se quiera eliminar.
     * @return El valor del elemento eliminado.
     */
    public Object pop(Object key) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) throw new KeyError(key);
        Object v = entries.get(valueIndex).getValue();
        deleteElement(key);
        return v;
    }

    public Object pop(Object key, Object d) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) return d;
        Object v = entries.get(valueIndex).getValue(); 
        deleteElement(key);
        return v;
    }

    public Object setdefault(Object key, Object d) {
        int valueIndex = getElement(key)[0];
        if(valueIndex == -1) {
            put(key, d);
        }
        return entries.get(getElement(key)[0]).getValue(); 
    }

    public Object setdefault(Object key) {
        return setdefault(key, null); 
    }

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
     * 
     * @param keys
     * @return
     */
    public static Dictionary fromkeys(Object[] keys) {
        return fromkeys(keys, null);
    }

    /**
     * aa
     * @param keys
     * @return
     */
    public static Dictionary fromkeys(Iterable<?> keys) {
        return fromkeys(keys, null);
    }

    /**
     * Este método crea un diccionario nuevo a partir de concatenar dos.
     * 
     * @param other Otro diccionario que concatena al que se le aplica.
     * @return El diccionario nuevo compuesto por los dos anteriores.
     */
    public Dictionary merge(Dictionary other) {
        Dictionary newDict = this.copy();
        for(Pair p : other.items()) {
            newDict.addElement(new Entry(p));
        }
        return newDict;
    }

    public List<Object> list() {
        List<Object> keys = new ArrayList<>(entries.size());
        Object temp;
        for(int i = 0; i < index; i++) {
            temp = entries.get(i).getKey();
            if(temp != null) keys.add(temp);
        }
        return keys;
    }

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
     * Este método recorre los elementos del diccionario y los inserta en una StringBuilder().
     * 
     * @return El diccionario en formato String (la StringBuilder()).
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
     * Este método accede a los valores del diccionario.
     * 
     * @return Los valores del diccionario.
     */
    public Values values() {
        return new Values(entries);
    }

    /**
     * Este método accede a las claves del diccionario.
     * 
     * @return Las claves del diccionario.
     */
    public Keys keys() {
        return new Keys(entries, this);
    }

    /**
     * Este método accede a los elementos del diccionario.
     * 
     * @return Los elementos del diccionario (clave, valor).
     */
    public Items items() {
        return new Items(entries, this);
    }

    /**
     * Este método elimina todos los elementos del diccionario.
     */
    public void clear() {
        setDictionary(INITIAL_CAPACITY);
    }

    /**
     * Este método busca si una clave está ya en el diccionario.
     * 
     * @param key La clave que se busca.
     * @return Valor booleano que indica si está en el diccionario.
     */
    public boolean contains(Object key) {
        return getElement(key)[0] == -1 ? false : true;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Keys(entries, this).iterator();
    }

    public Iterable<Object> reversed() {
        return new Keys(entries, this).reversed();
    }
}