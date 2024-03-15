import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;


public class JUnitTest {

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