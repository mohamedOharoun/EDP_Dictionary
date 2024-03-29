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

        
        assertThrows(TypeError.class, () -> dictionary.put(null, "Ochoa"));
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
        newKey3.add(new Pair(new ArrayList<>(1), "value"));
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
        dictionary.setdefault("key0", "value0");
        assertEquals("Tamaño incorrecto", SIZE+1, dictionary.length());
        dictionary.setdefault("key0", "value0");
        assertEquals("Tamaño incorrecto", SIZE+1, dictionary.length());
        assertEquals(dictionary.get("key0"), dictionary.setdefault("key0", "value0"));
    }

    /**
     * Prueba del setdefault(key) con ningún elemento en el diccionario.
     */
    @Test
    public void testSetdefaultTwo() {
        dictionary.setdefault("key0");
        assertEquals("Tamaño incorrecto", SIZE+1, dictionary.length());
        assertNull(dictionary.get("key0"));
        dictionary.setdefault(new Pair("key1.1", "key1.2"));
        assertEquals("Tamaño incorrecto", SIZE+2, dictionary.length());
        assertNull(dictionary.get(new Pair("key1.1", "key1.2")));
    }

    /**
     * Prueba del merge() con ningún elemento en el diccionario.
     */
    @Test
    public void testMerge() {
        Dictionary dict2 = new Dictionary();
        dict2.put("key0", new Pair("Antonio", "Méndez"));
        dict2.put(1, "value1");

        Dictionary dict3 = dictionary.merge(dict2);
        assertEquals("Tamaño incorrecto", SIZE+2, dict3.length());

        Dictionary dict4 = dictionary.merge(dict3);
        dict4.put(new Pair("Mohamed", 90));
        assertEquals("Tamaño incorrecto", SIZE+3, dict4.length());
    }

    /**
     * Prueba del list() con ningún elemento en el diccionario.
     */
    @Test
    public void testList() {
        assertTrue(dictionary.list().equals(new ArrayList<>()));

        dictionary.put(new Pair("Alejandro", 24), "value0");
        assertEquals("Tamaño de la lista incorrecto", SIZE+1, dictionary.list().size());

        ArrayList<Object> array = new ArrayList<>();
        array.add(new Pair("Alejandro", 24));
        assertEquals(array , dictionary.list());
    
        dictionary.put("key1", "value1");
        assertEquals("Tamaño de la lista incorrecto", SIZE+2, dictionary.list().size());
    }

    /**
     * Prueba del equals() con ningún elemento en el diccionario.
     */
    @Test
    public void testEquals() {
        Dictionary dict2 = new Dictionary();
        assertTrue(dictionary.equals(dict2));

        dict2.put(new Pair("Alejandro", "de Olózaga"), 805.3);
        dictionary.put(new Pair("Alejandro", "de Olózaga"), 805.3);
        assertTrue(dictionary.equals(dict2));
    }

    /**
     * Prueba del values() con ningún elemento en el diccionario.
     */
    @Test
    public void testValues() {
        assertEquals("dict_values([])", dictionary.values().toString());

        dictionary.put(new Pair("Álvaro", 300.0), new Pair(new Pair(12, 04), 28));
        assertEquals("dict_values([((12, 4), 28)])", dictionary.values().toString());

        dictionary.put("Key", null);
        assertEquals("dict_values([((12, 4), 28), null])", dictionary.values().toString());   
    }

    /**
     * Prueba del keys() con ningún elemento en el diccionario.
     */
    @Test
    public void testKeys() {
        assertEquals("dict_keys([])", dictionary.keys().toString());

        dictionary.put(new Pair("Álvaro", 300.0), new Pair(new Pair(12, 04), 28));
        assertEquals("dict_keys([('Álvaro', 300.0)])", dictionary.keys().toString());
        dictionary.put("300", null);
        assertEquals("dict_keys([('Álvaro', 300.0), '300'])", dictionary.keys().toString());

    }

    /**
     * Prueba del items() con ningún elemento en el diccionario.
     */
    @Test
    public void testItems() {
        assertEquals("dict_items([])", dictionary.items().toString());

        dictionary.put(new Pair("Álvaro", 300.0), new Pair(new Pair(12, 04), 28));
        assertEquals("dict_items([(('Álvaro', 300.0), ((12, 4), 28))])", dictionary.items().toString());
        dictionary.put("300", null);
        assertEquals("dict_items([(('Álvaro', 300.0), ((12, 4), 28)), ('300', null)])", dictionary.items().toString());
    }

    /**
     * Prueba del clear() con ningún elemento en el diccionario.
     */
    @Test
    public void testClear() {
        Dictionary dict2 = new Dictionary();
        assertEquals(SIZE, dictionary.length());
        dictionary.clear();
        
        assertEquals(SIZE, dictionary.length()); assertEquals(dict2.length(), dictionary.length());
        dictionary.put("Ocho", 8); dictionary.put("Nueve", 9); dictionary.put("Diez", 10);
        assertEquals(SIZE+3, dictionary.length());
    
        dictionary.clear();
        assertEquals(SIZE, dictionary.length());
    }

    /**
     * Prueba del contains() con ningún elemento en el diccionario.
     */
    @Test
    public void testContains() {
        assertFalse(dictionary.contains(""));
        dictionary.put(new Pair("Dos", 2), "value");
        assertTrue(dictionary.contains(new Pair("Dos", 2)));

        assertFalse(dictionary.values().contains("value0"));
        assertTrue(dictionary.values().contains("value"));
    }

    /**
     * Prueba del iterator() con ningún elemento en el diccionario.
     */
    @Test
    public void testIterator() {
        int i = 0;
        for (@SuppressWarnings("unused") Object k : dictionary) {
            i++;
        }
        assertEquals(SIZE, i);

        dictionary.put("Uno", 1); dictionary.put("Dos", 2);
        i = 0;
        for (@SuppressWarnings("unused") Object k : dictionary) {
            i++;
        }
        assertEquals(SIZE+2, i);
    }

    /**
     * Prueba del reversed() con ningún elemento en el diccionario.
     */
    @Test
    public void testReversed() {
        int i = 0;
        for (@SuppressWarnings("unused") Object k : dictionary.reversed()) {
            i++;
        }
        assertEquals(SIZE, i);

        dictionary.put("Uno", 1); dictionary.put("Dos", 2); dictionary.put("Tres", 3);
        
        ArrayList<Pair> array = new ArrayList<>();
        array.add(new Pair("Tres", 3)); array.add(new Pair("Dos", 2)); array.add(new Pair("Uno", 1));

        for (Object k : dictionary.reversed()) {
            assertEquals(array.get(i).get(0), k);
            i++;
        }

    }

}
