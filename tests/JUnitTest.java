import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class JUnitTest {
    @Test
    public void testPopItem() {
        Dictionary dictionary = new Dictionary();

        // Verificar que popitem() lance una excepción cuando el diccionario está vacío
        assertThrows(RuntimeException.class, () -> {
            dictionary.popitem();
        });

        dictionary.put(new Pair(300.123, "12"), "value1");
        dictionary.put("key2", new Pair(65, new Pair("Man", new Pair(23, 32))));
        dictionary.put("key3", "value3");

        int sizeBefore = dictionary.length();

        // Obtener un elemento del diccionario usando popitem()
        Pair poppedItem = dictionary.popitem();

        // Verificar que el tamaño del diccionario haya disminuido
        assertEquals(sizeBefore - 1, dictionary.length());

        // Verificar que el elemento obtenido no sea nulo
        assertNotNull(poppedItem);

        // Verificar que el elemento obtenido tenga una clave y un valor no nulos
        assertNotNull(poppedItem.get(0));
        assertNotNull(poppedItem.get(1));

        // Verificar que el elemento obtenido no esté presente en el diccionario original
        assertFalse(dictionary.contains(poppedItem.get(0)));
    }

    @Test
    public void testCopy() {
        Dictionary dictionary = new Dictionary();

        dictionary.put("key1", "124972103586");
        dictionary.put(new Pair("Key2.1", 2.2222), "value2");
        dictionary.put("key3", new Pair(20, 20));

        Dictionary copia = dictionary.copy();

        assertNotSame(dictionary, copia);

        assertEquals(dictionary.length(), copia.length());
        assertTrue(copia.contains("key1"));
        assertTrue(copia.contains(new Pair("Key2.1", 2.2222)));
        assertTrue(copia.contains("key3"));
        assertEquals("124972103586", copia.get("key1"));
        assertEquals("value2", copia.get(new Pair("Key2.1", 2.2222)));
        assertEquals(new Pair(20, 20), copia.get("key3"));
    }

    @Test
    public void testContains() {
        Dictionary dictionary = new Dictionary();

        for(int i = 0; i < 20; i++) {
            dictionary.put(i, "value" + i);
        }

        assertEquals(true, dictionary.contains(17));
        assertEquals(false, dictionary.contains(1000));
    }

    @Test
    public void testClear() {
        Dictionary dictionary = new Dictionary();

        for(int i = 0; i < 100; i++) {
            dictionary.put(i, "value" + i);
        }

        dictionary.clear();

        assertEquals(0, dictionary.length());
    }

    @Test
    public void testKeys() {
        Dictionary dictionary = new Dictionary();

        for(int i = 0; i < 100; i++) {
            dictionary.put(i, "value" + i);
        }

        List<Object> expectedKeys = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            expectedKeys.add(i);
        }

        Keys keys = dictionary.keys();

        List<Object> actualKeys = new ArrayList<>();
        for(Object key : keys) {
            actualKeys.add(key);
        }

        assertEquals(expectedKeys.size(), actualKeys.size());
        assertTrue(actualKeys.containsAll(expectedKeys));
    }

    @Test
    public void testValues() {
        Dictionary dictionary = new Dictionary();

        for(int i = 0; i < 100; i++) {
            dictionary.put(i, "value" + i);
        }

        List<Object> expectedValues = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            expectedValues.add("value" + i);
        }

        Values values = dictionary.values();

        List<Object> actualValues = new ArrayList<>();
        for(Object value : values) {
            actualValues.add(value);
        }

        assertEquals(expectedValues.size(), actualValues.size());
        assertTrue(actualValues.containsAll(expectedValues));
    }

    @Test
    public void length100numbers() {
        Dictionary dictionary = new Dictionary();
        final int numberVariable = 100;

        for(int i = 0; i < numberVariable; i++) {
            dictionary.put(i, "value");
        }

        assertEquals(numberVariable, dictionary.length());
    }

    @Test
    public void testIterable() {
        Dictionary dictionary = new Dictionary();

        dictionary.put("key1", "value1");
        dictionary.put(new Pair(202020202, "Valido"), "valute");
        dictionary.put(3.12491725408230500, "value3");

        Iterator<Object> IteratorDictionary = dictionary.iterator();
        assertTrue(IteratorDictionary.hasNext());
        assertEquals("key1", IteratorDictionary.next());
        assertTrue(IteratorDictionary.hasNext());
        assertEquals(new Pair(202020202, "Valido"), IteratorDictionary.next());
        assertTrue(IteratorDictionary.hasNext());
        assertEquals(3.12491725408230500, IteratorDictionary.next());
        assertFalse(IteratorDictionary.hasNext());
    }

    @Test
    public void testReversed() {
        Dictionary dictionary = new Dictionary();

        dictionary.put("key1", null);
        dictionary.put(new Pair("Uno", 2), 800);
        dictionary.put("key3", new Pair("Tres", "Tres"));

        Iterable<Object> reversedDictionary = dictionary.reversed();

        Iterator<Object> iterator = reversedDictionary.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("key3", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Pair("Uno", 2), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("key1", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testPut() {
        Dictionary dictionary = new Dictionary();

        dictionary.put(2152750, "value1");
        dictionary.put(new Pair("Uno", new Pair("Dos", new Pair("Tres", 4))), "value2value2value2");
        dictionary.put(12.3423, new Pair("Cinco", 6));

        // Verifico si los elementos se han metido correctamente al diccionario
        assertTrue(dictionary.contains(2152750));
        assertTrue(dictionary.contains(new Pair("Uno", new Pair("Dos", new Pair("Tres", 4)))));
        assertTrue(dictionary.contains(12.3423));

        assertEquals(3, dictionary.length());

        // Verifico que el valor de la clave es correcto
        assertEquals(null, dictionary.get("key1"));
        assertEquals("value2value2value2", dictionary.get(new Pair("Uno", new Pair("Dos", new Pair("Tres", 4)))));
        assertThrows(KeyError.class, () -> dictionary.retrieve("key2"));
        assertEquals(new Pair("Cinco", 6), dictionary.get(12.3423));
    }

    @Test
    public void testUpdate() {
        Dictionary dictionary = new Dictionary();

        // Creo tres objetos de tipo Pair.
        Pair p1 = new Pair("key1", new Pair(20, 20));
        Pair p2 = new Pair(2, "value2");
        Pair p3 = new Pair(3.5, 3);

        // Inserto los objetos Pair en un array Pair[].
        Pair[] parejas = new Pair[3];
        parejas[0] = p1; parejas[1] = p2; parejas[2] = p3;
        // Utilizo método uptade() para insertar los objetos en el diccionario.
        dictionary.update(parejas);


        // Verifico si los elementos se han metido correctamente al diccionario.
        assertTrue(dictionary.contains("key1"));
        assertTrue(dictionary.contains(2));
        assertTrue(dictionary.contains(3.5));

        assertEquals(new Pair(20, 20), dictionary.get("key1"));
        assertEquals(3, dictionary.get(3.5));
    }

    @Test
    public void testget() {
        Dictionary dictionary = new Dictionary();

        // Inserto valores.
        dictionary.put("key1", null);
        dictionary.put("", 3);
        dictionary.put("key3", "value3");
        dictionary.put(new Pair("key4", "32"));


        // Verifico que el valor de la clave es correcto
        assertEquals(null, dictionary.retrieve("key1"));
        assertEquals(3, dictionary.retrieve(""));
        assertEquals("value3", dictionary.retrieve("key3"));
        assertThrows(KeyError.class, () -> dictionary.retrieve("key5"));
        assertEquals(3, dictionary.retrieve(""));
        assertEquals("32", dictionary.retrieve("key4"));
    }

     @Test
     public void testdel() {
        Dictionary dictionary = new Dictionary();

        // Creo tres objetos de tipo Pair.
        Pair p1 = new Pair("key1", "value1");
        Pair p2 = new Pair(2, "value2");

        Pair[] parejas = new Pair[2];
        parejas[0] = p1; parejas[1] = p2;

        // Ingreso los elementos al diccionario.
        dictionary.update(parejas);
        dictionary.put(3, "value3");
        dictionary.put(4.5, 4.321);

        // Elimino elementos con del().
        dictionary.del(3);
        dictionary.del("key1");

        // Verifico que los elementos han sido eliminados.
        assertThrows(KeyError.class, () -> dictionary.retrieve(3));
        assertEquals(4.321, dictionary.retrieve(4.5));
        assertEquals(2, dictionary.length());
        
     }   

     @Test
     public void testpop() {
        Dictionary dictionary = new Dictionary();

        // Inserto valores.
        dictionary.put("key1", null);
        dictionary.put("", 3);
        dictionary.put("key3", 5);
        dictionary.put(new Pair("key4", "32"));

        assertEquals(4, dictionary.length());

        // Verifico que el método pop() saca elementos del diccionario.
        assertEquals(5, dictionary.pop("key3"));
        assertEquals(3, dictionary.length());
        assertThrows(KeyError.class, () -> dictionary.pop("key3"));
     }

     @Test
     public void testsetdefault() {
        Dictionary dictionary = new Dictionary();

        // Inserto valores en el diccionario.
        dictionary.put("key1", "value1");
        dictionary.put(2, "value2inicial");
        dictionary.put("key3", 64);
        dictionary.put("key4", "value4");

        // Uso el método setdefault(): nuevas claves y claves ya existentes.
        assertEquals("value5", dictionary.setdefault("key5", "value5"));
        assertEquals(64, dictionary.setdefault("key3", "value3"));
        assertEquals("value2inicial", dictionary.setdefault(2, "value2.1"));
     }

     @Test
     public void testfromkeys() {
        // Creo una Lista de objetos.
        ArrayList<Object> keys = new ArrayList<Object>(3);
        keys.add("key1"); keys.add("key2"); keys.add(3);

        Dictionary dict2 = new Dictionary();
        dict2 = Dictionary.fromkeys(keys);
    
        // Inserto los objetos al diccionario usando fromkeys(): valores por defecto a claves distintas..
        Dictionary dictionary = new Dictionary();
        dictionary = Dictionary.fromkeys(keys, 0);
        dictionary.put(3, "new_value");

        // Compruebo que las claves tienen el mismo valor por defecto.
        assertEquals(0, dictionary.get("key1"));
        assertEquals(0, dictionary.get("key2"));
        assertEquals("new_value", dictionary.pop(3));
    
        assertNull(dict2.get("key1"));
        assertNotEquals(dictionary.get("key2"), dict2.get("key3"));
     }

     @Test
     public void testUpdateIterableOfIterables() {
        Dictionary d = new Dictionary();
        List<List<Integer>> l = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        l1.add(10);
        l1.add(11);
        l2.add(20);
        l2.add(2);
        l.add(l1);
        l.add(l2);
        d.update(l);
        assertEquals("{10: 11, 20: 2}", d.toString());
        List<Integer> l3 = new ArrayList<>();
        l3.add(10);
        l3.add(11);
        l3.add(20);
        l.add(l3);
        assertThrows(ValueError.class, () -> {
            d.update(l);
        });
     }
}