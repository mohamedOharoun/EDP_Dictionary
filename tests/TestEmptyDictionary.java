import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;


public class TestEmptyDictionary {
    Dictionary dictionary;
    final int SIZE = 0;

    @Before
    public void init() {
        dictionary = new Dictionary();
    }

    /**
     * Prueba de len() con ningún elemento en el diccionario.
     */

    @Test
    public void testLen() {
        assertEquals("Tamaño incorrecto", SIZE, dictionary.length());
    }

    /**
     * Prueba de Copy() con ningún elemento en el diccionario.
     */
    @Test
    public void testCopy() {
    Dictionary copia = dictionary.copy();

    assertNotSame(dictionary, copia);

    assertEquals("Tamaño incorrecto", SIZE, copia.length());
    assertEquals(dictionary.length(), copia.length());
    }

    /**
     * prueba de retrieve() con ningún elemento en el diccionario.
     */
    @Test
    public void testRetrieve() {
        assertThrows(KeyError.class, () -> dictionary.retrieve("key0"));
        assertThrows(KeyError.class, () -> dictionary.retrieve("key1"));
    }

    /**
     * Prueba de get(key) con ningún elemento en el diccionario.
     */
    @Test
    public void testGetOne() {
        assertNull("No existe la clave", dictionary.get("key0"));
        assertNull("No existe la clave", dictionary.get("key1"));
    }

    /**
     * Prueba de get(key, default) con ningún elemento en el diccionario.
     */
    @Test
    public void testGetTwo() {
        assertEquals("No existe la clave", "value0", dictionary.get("key0", "value0"));
        assertEquals("No existe la clave", 3, dictionary.get("key0", 3));
    }

    /**
     * Prueba de del() con ningún elemento en el diccionario.
     */
    @Test
    public void testDel() {
        assertThrows(KeyError.class, () -> dictionary.del("key0"));
        assertThrows(KeyError.class, () -> dictionary.del("key1"));
        assertEquals("Tamaño incorrecto", SIZE, dictionary.length());
    }

    /**
     * Prueba del pop(key) con ningún elemento en el diccionario.
     */
    @Test
    public void testPopOne() {
        int len = dictionary.length();
        assertEquals("Tamaño incorrecto", SIZE, len);
        assertThrows(KeyError.class, () -> dictionary.del("key0"));
        assertThrows(KeyError.class, () -> dictionary.del("key1"));
        assertEquals("Tamaño incorrecto", len, dictionary.length());
    }

    /**
     * Prueba del pop(key, value) con ningún elemento en el diccionario.
     */
    @Test
    public void testPopTwo() {
        Pair pair = new Pair("Pepe", "García");
        assertEquals("value0", dictionary.pop("key0", "value0"));
        assertEquals(0, dictionary.pop("key1", 0));
        assertEquals(pair, dictionary.pop(2, pair));
    }

    
    /**
     * Prueba del put(Pair) con ningún elemento en el diccionario.
     */
    @Test
    public void testPutOne() {
        assertEquals("Tamaño incorrecto", SIZE, dictionary.length());
        dictionary.put(new Pair("key0", 0));
        assertEquals("Tamaño incorrecto", SIZE+1, dictionary.length());
        dictionary.put(new Pair(new Pair("Pepe", "García"), "value1"));
        assertEquals("Tamaño incorrecto", SIZE+2, dictionary.length());
    }

    /**
     * Prueba del put(key, value) con ningún elemento en el diccionario.
     */
    @Test
    public void testPutTwo() {
        assertEquals("Tamaño incorrecto", SIZE, dictionary.length());
        dictionary.put(new Pair("key0", 0));
        assertEquals("Tamaño incorrecto", SIZE+1, dictionary.length());
        dictionary.put("key1", new Pair("value1", new Pair("value1.2", "value1.3")));
        assertEquals("Tamaño incorrecto", SIZE+2, dictionary.length());
    }

    /**
     * Prueba del update(Iterable<Pair>) con ningún elemento en el diccionario.
     */
    @Test
    public void testUpdateOne() {
        ArrayList<Pair> pair = new ArrayList<Pair>();
        pair.add(new Pair("key0", "value0"));
        pair.add(new Pair(1, new Pair("value1.1", "value1.2")));
        pair.add(new Pair(new Pair("key2.1", 2.2), 2));

        dictionary.update(pair);
        assertEquals("Tamaño incorrecto", SIZE+3, dictionary.length());
        assertEquals(2, dictionary.get(new Pair("key2.1", 2.2)));
        dictionary.del("key0");
        assertEquals("Tamaño incorrecto", SIZE+2, dictionary.length());
    }

    /**
     * Prueba del update(Pairs[]) con ningún elemento en el diccionario.
     */
    @Test
    public void testUpdateTwo() {
        Pair[] pair = new Pair[3];
        pair[0] = new Pair("key0", "value0");
        pair[1] = new Pair(1, new Pair("value1.1", "value1.2"));
        pair[2] = new Pair(new Pair("key2.1", 2.2), 2);

        dictionary.update(pair);
        assertEquals("Tamaño incorrecto", SIZE+3, dictionary.length());
        assertEquals(2, dictionary.get(new Pair("key2.1", 2.2)));
        dictionary.del("key0");
        assertEquals("Tamaño incorrecto", SIZE+2, dictionary.length());
    }

    /**
     * Prueba del update(Dictionary) con ningún elemento en el diccionario.
     */
    @Test
    public void testUpdateThree() {
        Dictionary dict2 = new Dictionary();
        dict2.put(new Pair("key0", "value0"));
        dict2.put("key1", 1);
        dict2.put(2,"value2");

        dictionary.update(dict2);
        assertEquals("Tamaño incorrecto", SIZE+3, dictionary.length());
        assertEquals("value2", dictionary.get(2));

        List<Pair> newKey3 = new ArrayList<>();
        newKey3.add(new Pair(new ArrayList(1), "value"));
        assertThrows(TypeError.class, () -> dictionary.update(newKey3));
    }

    /**
     * Prueba del update(Map) con ningún elemento en el diccionario.
     */
    @Test
    public void testUpdateFour() {
        Map<Object, Object> keys = new HashMap<>();
        keys.put("key0", "value0");
        keys.put(1, new Pair(80, "Mendoza"));
        keys.put(new Pair("Valido", "Estupiñán"), 20);
        Dictionary dict2 = new Dictionary();

        dictionary.update(keys);
        assertEquals("Tamaño incorrecto", SIZE+3, dictionary.length());
        assertEquals(20, dictionary.get(new Pair("Valido", "Estupiñán")));
        assertNull(dictionary.get(2));

        Map<Object, Object> newKey3 = new HashMap<>();
        newKey3.put(new ArrayList<>(), 3);
        assertThrows(TypeError.class, () -> dictionary.update(newKey3));
    }

    /**
     * Prueba del setdefault(key, default) con ningún elemento en el diccionario.
     */
    @Test
    public void testSetdefaultOne() {

    }

    /**
     * Prueba del setdefault(key) con ningún elemento en el diccionario.
     */
    @Test
    public void testSetdefaultTwo() {

    }

    /**
     * Prueba del merge() con ningún elemento en el diccionario.
     */
    @Test
    public void testMerge() {

    }

    /**
     * Prueba del list() con ningún elemento en el diccionario.
     */
    @Test
    public void testList() {

    }

    /**
     * Prueba del equals() con ningún elemento en el diccionario.
     */
    @Test
    public void testEquals() {

    }

    /**
     * Prueba del values() con ningún elemento en el diccionario.
     */
    @Test
    public void testValues() {

    }

    /**
     * Prueba del keys() con ningún elemento en el diccionario.
     */
    @Test
    public void testKeys() {

    }

    /**
     * Prueba del items() con ningún elemento en el diccionario.
     */
    @Test
    public void testItems() {

    }

    /**
     * Prueba del clear() con ningún elemento en el diccionario.
     */
    @Test
    public void testClear() {

    }

    /**
     * Prueba del contains() con ningún elemento en el diccionario.
     */
    @Test
    public void testContains() {

    }

    /**
     * Prueba del iterator() con ningún elemento en el diccionario.
     */
    @Test
    public void testIterator() {

    }

    /**
     * Prueba del reversed() con ningún elemento en el diccionario.
     */
    @Test
    public void testReversed() {

    }

}
