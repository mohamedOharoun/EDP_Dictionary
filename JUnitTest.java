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

        dictionary.put("key1", "value1");
        dictionary.put("key2", "value2");
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

        dictionary.put("key1", "value1");
        dictionary.put("key2", "value2");
        dictionary.put("key3", "value3");

        Dictionary copia = dictionary.copy();

        assertNotSame(dictionary, copia);

        assertEquals(dictionary.length(), copia.length());
        assertTrue(copia.contains("key1"));
        assertTrue(copia.contains("key2"));
        assertTrue(copia.contains("key3"));
        assertEquals("value1", copia.get("key1"));
        assertEquals("value2", copia.get("key2"));
        assertEquals("value3", copia.get("key3"));
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
        int numberVariable = 100;

        for(int i = 0; i < 100; i++) {
            dictionary.put(i, "value");
        }

        assertEquals(100, dictionary.length());
    }

    @Test
    public void testIterable() {
        Dictionary dictionary = new Dictionary();

        dictionary.put("key1", "value1");
        dictionary.put("key2", "value2");
        dictionary.put("key3", "value3");

        Iterator<Object> IteratorDictionary = dictionary.iterator();
        assertTrue(IteratorDictionary.hasNext());
        assertEquals("key1", IteratorDictionary.next());
        assertTrue(IteratorDictionary.hasNext());
        assertEquals("key2", IteratorDictionary.next());
        assertTrue(IteratorDictionary.hasNext());
        assertEquals("key3", IteratorDictionary.next());
        assertFalse(IteratorDictionary.hasNext());
    }

    @Test
    public void testReversed() {
        Dictionary dictionary = new Dictionary();

        dictionary.put("key1", "value1");
        dictionary.put("key2", "value2");
        dictionary.put("key3", "value3");

        Iterable<Object> reversedDictionary = dictionary.reversed();

        Iterator<Object> iterator = reversedDictionary.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("key3", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("key2", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("key1", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testPut() {
        Dictionary dictionary = new Dictionary();

        dictionary.put("key1", "value1");
        dictionary.put("key2", "value2");
        dictionary.put("key3", "value3");

        // Verifico si los elementos se han metido correctamente al diccionario
        assertTrue(dictionary.contains("key1"));
        assertTrue(dictionary.contains("key2"));
        assertTrue(dictionary.contains("key3"));

        assertEquals(3, dictionary.length());

        // Verifico que el valor de la clave es correcto
        assertEquals("value1", dictionary.get("key1"));
        assertEquals("value2", dictionary.get("key2"));
        assertEquals("value3", dictionary.get("key3"));
    }
}