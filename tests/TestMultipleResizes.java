import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

/*
 * Prueba de Dictionary con un número de elementos menor al resize(menos de 5 elementos).
 */
public class TestMultipleResizes {
    final int SIZE = 50;
    Dictionary d;
    List<Pair> controlList;

    @Before
    public void init() {
        d = new Dictionary();
        for(int i = 0; i < SIZE; i++) {
            d.put("Key " + i, i);
        }
        controlList = new ArrayList<>();
        for(int i = 0; i < SIZE; i++) {
            controlList.add(new Pair("Key " + i, i));
        }
    }

    /**
     * Prueba de length.
     */
   @Test
   public void testLength() {
       assertEquals("Tamaño incorrecto", SIZE, d.length());
   }
   
   /**
     * Prueba de get cuando no se le pasa default.
     */
    @Test
    public void testGet() {
        for(Pair p : controlList) {
            assertEquals(p.get(1), d.get(p.get(0)));
        }
        assertNull(d.get("Key " + (SIZE + 1)));
    }

    /*
     * Prueba de get cuando se le pasa default.
     */
    @Test
    public void testGetDefault() {
        for(Pair p : controlList) {
            assertEquals(p.get(1), d.get(p.get(0), 5));
        }
        assertEquals(5, d.get("Key " + (SIZE + 1), 5));
    }

    /*
     * Prueba de put pasando clave y valor como parámetros.
     */
    @Test
    public void testPut() {
        final int NEW_SIZE = SIZE + 1;
        d.put("Key " + NEW_SIZE, NEW_SIZE);
        assertEquals("Tamaño incorrecto",NEW_SIZE, d.length());
        assertEquals(NEW_SIZE, d.get("Key " + NEW_SIZE));
    }

    /*
     * Prueba de put pasando un objeto como Pair como párametro.
     */
    @Test
    public void testPutPair() {
        final int NEW_SIZE = SIZE + 1;
        Pair p = new Pair("Key " + NEW_SIZE, NEW_SIZE);
        d.put(p);
        assertEquals("Tamaño incorrecto", NEW_SIZE, d.length());
        assertEquals(NEW_SIZE, d.get("Key " + NEW_SIZE));
    }

    /*
     * Prueba de update pasando iterable de Pair con una clave nueva y otra clave ya repetida.
     */
    @Test
    public void testUpdateIterable() {
        List<Pair> lps = new ArrayList<>();
        lps.add(new Pair("Key 0", 1));
        lps.add(new Pair("Key", SIZE));
        d.update(lps);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(SIZE, d.get("Key"));
        assertEquals(1, d.get("Key 0"));
    }

    /*
     * Prueba de update pasando array de Pair con una clave nueva y otra clave ya repetida.
     */
    @Test
    public void testUpdateArray() {
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair("Key 13", 1);
        pairs[1] = new Pair("Key", SIZE);
        d.update(pairs);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(1, d.get("Key 13"));
        assertEquals(SIZE, d.get("Key"));
    }

    /*
     * Prueba de update pasando un Map con una clave nueva y otra clave ya repetida.
     */
    @Test
    public void testUpdateMap() {
        Map<String, Integer> maps = new HashMap<>();
        maps.put("Key 0", 1);
        maps.put("Key", SIZE);
        d.update(maps);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(1, d.get("Key 0"));
        assertEquals(SIZE, d.get("Key"));
    }

    /*
     * Prueba de update pasando un Dictionary con una clave nueva y otra clave ya repetida.
     */
    @Test
    public void testUpdateDictionary() {
        Dictionary d2 = new Dictionary();
        d2.put("Key 0", 1);
        d2.put("Key", SIZE);
        d.update(d2);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(SIZE, d.get("Key"));
        assertEquals(1, d.get("Key 0"));
    }

    /*
     * Prueba de retrieve.
     */
    @Test
    public void testRetrieve() {
        final int testNumber = 15;
        assertEquals(testNumber, d.retrieve("Key " + testNumber));
        assertThrows( KeyError.class, () -> {
            d.retrieve("Key " + SIZE);
        });
    }

    /*
     * Prueba de popitem.
     */
    @Test
    public void testPopItem() {
        Collections.reverse(controlList);
        for(Pair p : controlList) {
            assertEquals(String.format("La pareja %s no fue encontrada", p) ,p, d.popitem());
        }
        assertThrows( KeyError.class, () -> {
            d.popitem();
        });
    }

    /*
     * Prueba de copy.
     */
    @Test
    public void testCopy() {
        Dictionary d2 = d.copy();
        assertEquals(d2, d);
        assertNotSame(d2, d);
        //Se comprueba que esté en orden, y con todos sus valores.
        int i = 0;
        for(Pair p : d2.items()) {
            assertEquals(String.format("La clave-valor número %s no se encuentra en su orden", p), controlList.get(i), p);
            i++;
        }
        assertEquals("La copia no tiene el mismo tamaño que el original", i, SIZE);
    }

    /*
     * Prueba de del.
     */
    @Test
    public void testDel() {
        final int testNumber = 25;
        d.del("Key " + testNumber);
        assertEquals("Ha fallado con Key " + testNumber, SIZE - 1, d.length());
        assertThrows( KeyError.class, () -> {
            d.del(SIZE);
        });
    }

    /*
     * Prueba de pop.
     */
    @Test
    public void testPop() {
        final int testNumber = 38;
        assertEquals("Ha fallado con Key " + testNumber, testNumber, d.pop("Key " + testNumber));
        assertEquals(SIZE - 1, d.length());
        assertThrows( KeyError.class, () -> {
            d.pop("Key " + (SIZE + 5));
        });
    }

    /*
     * Prueba de pop con default.
     */
    @Test
    public void testPopDefault() {
        final int testNumber = SIZE + 1;
        assertEquals(testNumber, d.pop("Key " + (testNumber + 1), testNumber));
        assertEquals(SIZE, d.length());
        assertEquals(0, d.pop("Key 0", testNumber));
        assertEquals(SIZE - 1, d.length());
        assertEquals(testNumber, d.pop("Key " + testNumber, testNumber));
        assertEquals(SIZE - 1, d.length());
        assertEquals(testNumber, d.pop("Key 0", testNumber));
        assertEquals(SIZE - 1, d.length());
    }

    /*
     * Prueba de setdeafult.
     */
    @Test
    public void testSetDefault() {
        String newKey = "Key " + SIZE;
        d.setdefault(newKey);
        assertNull(d.get(newKey));
        List<Object> newKey2 = new ArrayList<>();
        assertThrows(TypeError.class, () -> {
            d.setdefault(newKey2);
        });
    }

    /*
     * Prueba de setdeafult con valor.
     */
    @Test
    public void testSetDefaultWitValue() {
        String newKey = "Key " + SIZE;
        d.setdefault(newKey, SIZE);
        assertEquals(SIZE, d.get(newKey));
        Map<Object, Object> newKey2 = new HashMap<>();
        assertThrows(TypeError.class, () -> {
            d.setdefault(newKey2, 2);
        });
    }

    /*
     * Prueba de merge.
     */
    @Test
    public void testMerge() {
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair("Key 0", 1);
        pairs[1] = new Pair("Key " + SIZE, SIZE);
        Dictionary d2 = new Dictionary(pairs);
        Dictionary d3 = d.merge(d2);
        assertNotSame(d2, d3);
        assertNotSame(d, d3);
        assertEquals(SIZE + 1, d3.length());
        assertEquals(1, d3.get("Key 0"));
        assertEquals(1, d3.get("Key 1"));
        assertEquals(43, d3.get("Key 43"));
        assertEquals(25, d3.get("Key 25"));
        assertEquals(SIZE, d3.get("Key " + SIZE));
    }

    /*
     * Prueba de list.
     */
    @Test
    public void testList() {
        List<String> listTest = new ArrayList<>();
        for(Pair p : controlList) {
            listTest.add((String) p.get(0));
        }
        assertEquals(listTest, d.list());
        listTest.add(SIZE + "");
        assertNotEquals(listTest, d.list());
    }

    /*
     * Prueba de clear.
     */
    @Test
    public void testClear() {
        d.clear();
        assertEquals(0, d.length());
    }

    /*
     * Prueba de contains.
     */
    @Test
    public void testContains() {
        assertTrue(d.contains("Key 0"));
        assertTrue(d.contains("Key 25"));
        assertTrue(d.contains("Key 49"));
        assertTrue(d.contains("Key 13"));
        assertFalse(d.contains("Key " + SIZE));
    }

    /*
     * Prueba de iterator.
     */
    @Test
    public void testIterator() {
        List<String> listTest = new ArrayList<>();
        for(Pair p : controlList) {
            listTest.add((String) p.get(0));
        }
        int i = 0;
        for(Object key : d) {
            assertTrue(listTest.get(i).equals(key));
            i++;
        }
        assertEquals("Number of iterations must be equal to size of list",listTest.size(), i);
    }

    /*
     * Prueba de reversed.
     */
    @Test
    public void testReversed() {
        List<String> listTest = new ArrayList<>();
        for(Pair p : controlList) {
            listTest.add((String) p.get(0));
        }
        Collections.reverse(listTest);
        int i = 0;
        for(Object key : d.reversed()) {
            assertTrue(listTest.get(i).equals(key));
            i++;
        }
        assertEquals("Number of iterations must be equal to size of list", controlList.size(), i);
    }

    /*
     * Prueba de keys, iterador.
     */
    @Test
    public void testKeysIterator() {
        List<String> listTest = new ArrayList<>();
        for(Pair p : controlList) {
           if(!p.get(0).equals("Key 2")) listTest.add((String) p.get(0));
        }
        int i = 0;
        Keys ks = d.keys();
        d.put("Key " + SIZE, SIZE);
        listTest.add("Key " + SIZE);
        d.del("Key 2");
        for(Object key : ks) {
            assertTrue(listTest.get(i).equals(key));
            i++;
        }
        assertEquals("Number of iterations must be equal to size of list", controlList.size(), i);
    }

    /*
     * Prueba de Keys reversed.
     */
    @Test
    public void testKeysReversed() {
        List<String> listTest = new ArrayList<>();
        for(Pair p : controlList) {
            if(!p.get(0).equals("Key 2")) listTest.add((String) p.get(0));
         }
        Keys ks = d.keys();
        d.put("Key " + SIZE, SIZE);
        listTest.add("Key " + SIZE);
        d.del("Key 2");
        Collections.reverse(listTest);
        int i = 0;
        for(Object key : ks.reversed()) {
            assertTrue(listTest.get(i).equals(key));
            i++;
        }
        assertEquals("Number of iterations must be equal to size of list", controlList.size(), i);
    }

    /*
     * Prueba de Keys length.
     */
    @Test
    public void testKeysLength() {
        Keys ks = d.keys();
        assertEquals(d.length(), ks.length());
        d.put("Key", null);
        assertEquals(d.length(), ks.length());
        d.del("Key 1");
        d.del("Key 2");
        assertEquals(d.length(), ks.length());
    }

    /*
     * Prueba de Keys contains.
     */
    @Test
    public void testKeysContains() {
        Keys ks = d.keys();
        for(Object k : d) {
            assertTrue(ks.contains(k));
        }
        String deletedKey = "Key 0";
        d.del(deletedKey);
        assertFalse(ks.contains(deletedKey));
        d.put("Key", null);
        assertTrue(ks.contains("Key"));
    }

    /*
     * Prueba de Values, iterador.
     */
    @Test
    public void testValuesIterator() {
        List<Integer> listTest = new ArrayList<>();
        for(Pair p : controlList) {
            if(!p.get(0).equals("Key 2")) listTest.add((Integer) p.get(1));
        }
        int i = 0;
        Values vs = d.values();
        d.put("Key", SIZE);
        listTest.add(SIZE);
        d.del("Key 2");
        for(Object v : vs) {
            assertTrue(listTest.get(i).equals(v));
            i++;
        }
        assertEquals("Number of iterations must be equal to size of list", controlList.size(), i);
    }

    /*
     * Prueba de Values reversed.
     */
    @Test
    public void testValuesReversed() {
        List<Integer> listTest = new ArrayList<>();
        for(Pair p : controlList) {
            if(!p.get(0).equals("Key 2")) listTest.add((Integer) p.get(1));
        }
        int i = 0;
        Values vs = d.values();
        d.put("Key", SIZE);
        listTest.add(SIZE);
        d.del("Key 2");
        Collections.reverse(listTest);
        for(Object v : vs.reversed()) {
            assertTrue(listTest.get(i).equals(v));
            i++;
        }
        assertEquals("Number of iterations must be equal to size of list", controlList.size(), i);
    }

    /*
     * Prueba de Values length.
     */
    @Test
    public void testValuesLength() {
        Values vs = d.values();
        assertEquals(d.length(), vs.length());
        d.put("Key", null);
        assertEquals(d.length(), vs.length());
        d.del("Key 1");
        d.del("Key 2");
        assertEquals(d.length(), vs.length());
    }

    /*
     * Prueba de Values contains.
     */
    @Test
    public void testValuesContains() {
        Values vs = d.values();
        d.put("Key",null);
        for(Object k : d) {
            assertTrue(vs.contains(d.get(k)));
        }
        String deletedKey = "Key 0";
        d.del(deletedKey);
        assertFalse(vs.contains(0));
        d.put("Key", SIZE);
        assertTrue(vs.contains(SIZE));
    }

    /*
     * Prueba de Values contains con null value.
     */
    @Test
    public void testValuesContainsNull() {
        Values vs = d.values();
        assertFalse(vs.contains(null));
        d.setdefault("setdefault");
        assertTrue(vs.contains(null));
    }

    /*
     * Prueba de Items, iterador.
     */
    @Test
    public void testItemsIterator() {
        int i = 0;
        Items items = d.items();
        d.put("Key", SIZE);
        controlList.add(new Pair("Key", SIZE));
        d.del("Key 2");
        controlList.remove(new Pair("Key 2", 2));
        for(Object item : items) {
            assertTrue(controlList.get(i).equals(item));
            i++;
        }
        assertEquals("Number of iterations must be equal to size of list", controlList.size(), i);
    }

    /*
     * Prueba de Items reversed.
     */
    @Test
    public void testItemsReversed() {
        int i = 0;
        Items items = d.items();
        d.put("Key", SIZE);
        controlList.add(new Pair("Key", SIZE));
        d.del("Key 2");
        controlList.remove(new Pair("Key 2", 2));
        Collections.reverse(controlList);
        for(Object item : items.reversed()) {
            assertTrue(controlList.get(i).equals(item));
            i++;
        }
        assertEquals("Number of iterations must be equal to size of list", controlList.size(), i);
    }

    /*
     * Prueba de Items length.
     */
    @Test
    public void testItemsLength() {
        Items items = d.items();
        assertEquals(d.length(), items.length());
        d.put("Key", SIZE);
        assertEquals(d.length(), items.length());
        d.del("Key 1");
        d.del("Key 2");
        assertEquals(d.length(), items.length());
    }

    /*
     * Prueba de Items contains.
     */
    @Test
    public void testItemsContains() {
        Items items = d.items();
        d.put("Key", SIZE);
        int i = 0;
        for(Object k : d) {
            assertTrue(items.contains(new Pair(k, i)));
            i++;
        }
        String deletedKey = "Key 0";
        d.del(deletedKey);
        assertFalse(items.contains(new Pair("Key 0", 0)));
        d.put("Key " + SIZE, SIZE);
        assertTrue(items.contains(new Pair("Key",SIZE)));
        assertFalse(items.contains(new Pair("Key" + SIZE, SIZE)));
    }

    /*
     * Prueba de equals con un diccionario con claves-valor iguales.
     */
    @Test
    public void TestEqualsSamePairs() {
        Dictionary other = new Dictionary();
        for(int i = 0; i < SIZE; i++) {
            other.put("Key " + i, i);
        }
        assertEquals(d, other);
    }

    /*
     * Prueba de equals con un diccionario con alguna clave diferente pero igual valores.
     */
    @Test
    public void TestEqualsDifferentKeys() {
        Dictionary other = new Dictionary();
        other.put("Key" + 0, 0);
        for(int i = 1; i < SIZE; i++) {
            other.put("Key " + i, i);
        }
        assertNotEquals(d, other);
    }

    /*
     * Prueba de equals con un diccionario con iguales claves, pero algún valor diferente.
     */
    @Test
    public void TestEqualsDifferentValues() {
        Dictionary other = new Dictionary();
        other.put("Key " + 0, 5);
        for(int i = 1; i < SIZE; i++) {
            other.put("Key " + i, i);
        }
        assertNotEquals(d, other);
    }

    /*
     * Prueba de equals con un diccionario con una entrada más.
     */
    @Test
    public void TestEqualsDifferentLength() {
        Dictionary other = new Dictionary();
        for(int i = 0; i < SIZE + 1; i++) {
            other.put("Key " + i, i);
        }
        assertEquals(d.length() + 1, other.length());
        assertNotEquals(d, other);
    }
}
