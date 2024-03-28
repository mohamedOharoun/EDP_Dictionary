import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

/*
 * Prueba de Dictionary con un número de elementos menor al resize(menos de 5 elementos).
 */
public class TestNoResizeDictionary {
    final int SIZE = 3;
    Dictionary d;

    @Before
    public void init() {
        d = new Dictionary();
        for(int i = 0; i < SIZE; i++) {
            d.put("Key " + i, i);
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
        assertEquals(0, d.get("Key 0"));
        assertEquals(1, d.get("Key 1"));
        assertEquals(2, d.get("Key 2"));
        assertNull(d.get("Key 4"));
    }

    /*
     * Prueba de get cuando se le pasa default.
     */
    @Test
    public void testGetDefault() {
        assertEquals(0, d.get("Key 0", 5));
        assertEquals(1, d.get("Key 1", 5));
        assertEquals(2, d.get("Key 2", 5));
        assertEquals(5, d.get("Key 4", 5));
    }

    /*
     * Prueba de put pasando clave y valor como parámetros.
     */
    @Test
    public void testPut() {
        d.put("Key 3", 3);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(3, d.get("Key 3"));
    }

    /*
     * Prueba de put pasando un objeto como Pair como párametro.
     */
    @Test
    public void testPutPair() {
        Pair p = new Pair("Key 3", 3);
        d.put(p);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(3, d.get("Key 3"));
    }

    /*
     * Prueba de update pasando iterable de Pair con una clave nueva y otra clave ya repetida.
     */
    @Test
    public void testUpdateIterable() {
        List<Pair> lps = new ArrayList<>();
        lps.add(new Pair("Key 0", 1));
        lps.add(new Pair("Key 3", 3));
        d.update(lps);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(3, d.get("Key 3"));
        assertEquals(1, d.get("Key 0"));
    }

    /*
     * Prueba de update pasando array de Pair con una clave nueva y otra clave ya repetida.
     */
    @Test
    public void testUpdateArray() {
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair("Key 0", 1);
        pairs[1] = new Pair("Key 3", 3);
        d.update(pairs);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(3, d.get("Key 3"));
        assertEquals(1, d.get("Key 0"));
    }

    /*
     * Prueba de update pasando Map de Pair con una clave nueva y otra clave ya repetida.
     */
    @Test
    public void testUpdateMap() {
        Map<String, Integer> maps = new HashMap<>();
        maps.put("Key 0", 1);
        maps.put("Key 3", 3);
        d.update(maps);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(3, d.get("Key 3"));
        assertEquals(1, d.get("Key 0"));
    }

    /*
     * Prueba de update pasando un Dictionary con una clave nueva y otra clave ya repetida.
     */
    @Test
    public void testUpdateDictionary() {
        Dictionary d2 = new Dictionary();
        d2.put("Key 0", 1);
        d2.put("Key 3", 3);
        d.update(d2);
        assertEquals("Tamaño incorrecto", SIZE + 1, d.length());
        assertEquals(3, d.get("Key 3"));
        assertEquals(1, d.get("Key 0"));
    }

    /*
     * Prueba de retrieve.
     */
    @Test
    public void testRetrieve() {
        assertEquals(1, d.retrieve("Key 1"));
        assertThrows( KeyError.class, () -> {
            d.retrieve("Key 5");
        });
    }

    /*
     * Prueba de popitem.
     */
    @Test
    public void testPopItem() {
        assertEquals(new Pair("Key 2", 2), d.popitem());
        assertEquals(new Pair("Key 1", 1), d.popitem());
        assertEquals(new Pair("Key 0", 0), d.popitem());
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
        assertEquals(0, d2.get("Key 0"));
        assertEquals(1, d2.get("Key 1"));
        assertEquals(2, d2.get("Key 2"));
        assertNotSame(d2, d);
    }

    /*
     * Prueba de del.
     */
    @Test
    public void testDel() {
        d.del("Key 0");
        assertEquals(SIZE - 1, d.length());
        assertThrows( KeyError.class, () -> {
            d.del("Key 5");
        });
    }

    /*
     * Prueba de pop.
     */
    @Test
    public void testPop() {
        assertEquals(0, d.pop("Key 0"));
        assertEquals(SIZE - 1, d.length());
        assertThrows( KeyError.class, () -> {
            d.pop("Key 5");
        });
    }

    /*
     * Prueba de pop con default.
     */
    @Test
    public void testPopDefault() {
        assertEquals(0, d.pop("Key 0", 5));
        assertEquals(SIZE - 1, d.length());
        assertEquals(5, d.pop("Key 5", 5));
        assertEquals(SIZE - 1, d.length());
        assertEquals(5, d.pop("Key 0", 5));
        assertEquals(SIZE - 1, d.length());
    }

    /*
     * Prueba de setdeafult.
     */
    @Test
    public void testSetDefault() {
        String newKey = "Key 5";
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
        String newKey = "Key 5";
        d.setdefault(newKey, 5);
        assertEquals(5, d.get(newKey));
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
        pairs[1] = new Pair("Key 3", 3);
        Dictionary d2 = new Dictionary(pairs);
        Dictionary d3 = d.merge(d2);
        assertNotSame(d2, d3);
        assertNotSame(d, d3);
        assertEquals(SIZE + 1, d3.length());
        assertEquals(1, d3.get("Key 0"));
        assertEquals(1, d3.get("Key 1"));
        assertEquals(2, d3.get("Key 2"));
        assertEquals(3, d3.get("Key 3"));
    }

    /*
     * Prueba de list.
     */
    @Test
    public void testList() {
        List<String> listTest = new ArrayList<>();
        listTest.add("Key 0");
        listTest.add("Key 1");
        listTest.add("Key 2");
        assertTrue(listTest.equals(d.list()));
        listTest.add("Key 3");
        assertFalse(listTest.equals(d.list()));
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
        assertTrue(d.contains("Key 1"));
        assertTrue(d.contains("Key 2"));
        assertFalse(d.contains("Key 3"));
    }

    /*
     * Prueba de iterator.
     */
    @Test
    public void testIterator() {
        List<String> listTest = new ArrayList<>();
        listTest.add("Key 0");
        listTest.add("Key 1");
        listTest.add("Key 2");
        int i = 0;
        for(Object key : d) {
            assertTrue(listTest.get(i).equals(key));
            i++;
        }
    }

    /*
     * Prueba de reversed.
     */
    @Test
    public void testReversed() {
        List<String> listTest = new ArrayList<>();
        listTest.add("Key 0");
        listTest.add("Key 1");
        listTest.add("Key 2");
        Collections.reverse(listTest);
        int i = 0;
        for(Object key : d.reversed()) {
            assertTrue(listTest.get(i).equals(key));
            i++;
        }
    }

    /*
     * Prueba de keys, iterador.
     */
    @Test
    public void testKeysIterator() {
        List<String> listTest = new ArrayList<>();
        listTest.add("Key 0");
        listTest.add("Key 1");
        listTest.add("Key 3");
        int i = 0;
        Keys ks = d.keys();
        d.put("Key 3", 3);
        d.del("Key 2");
        for(Object key : ks) {
            assertTrue(listTest.get(i).equals(key));
            i++;
        }
    }

    /*
     * Prueba de Keys reversed.
     */
    @Test
    public void testKeysReversed() {
        List<String> listTest = new ArrayList<>();
        listTest.add("Key 0");
        listTest.add("Key 1");
        listTest.add("Key 3");
        Collections.reverse(listTest);
        Keys ks = d.keys();
        d.put("Key 3", 3);
        d.del("Key 2");
        int i = 0;
        for(Object key : ks.reversed()) {
            assertTrue(listTest.get(i).equals(key));
            i++;
        }
    }

    /*
     * Prueba de Keys length.
     */
    @Test
    public void testKeysLength() {
        Keys ks = d.keys();
        assertEquals(d.length(), ks.length());
        d.put("Key 3", 3);
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
        d.put("Key 3", 3);
        assertTrue(ks.contains("Key 3"));
    }
}
