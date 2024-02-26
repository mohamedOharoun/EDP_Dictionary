import org.junit.*;


public class JUnitTest {

    @Test
    public void testDictionaryOperations() {
        Dictionary dictionary = new Dictionary();

        // Test añadir elementos
        Entry entry1 = new Entry("key1", "value1");
        Entry entry2 = new Entry("key2", "value2");

        dictionary.addElement(entry1);
        dictionary.addElement(entry2);

        assertEquals(2, dictionary.length());

        // Test coger elementos
        Entry retrievedEntry1 = (Entry) dictionary.getElement("key1");
        Entry retrievedEntry2 = (Entry) dictionary.getElement("key2");

        assertEquals(entry1, retrievedEntry1);
        assertEquals(entry2, retrievedEntry2);

        // Test borrar elementos
        dictionary.deleteElement("key1");
        assertEquals(1, dictionary.length());

        // Intentar recuperar el elemento eliminado debería lanzar una excepción
        assertThrows(RuntimeException.class, () -> dictionary.getElement("key1"));

        // Prueba de redimensionamiento
        // Para esto, es posible que necesites hacer público el método resize() en tu clase Dictionary
        // Luego, puedes probar añadir más elementos para activar el redimensionamiento
        for (int i = 0; i < 20; i++) {
            Entry newEntry = new Entry("key" + i, "value" + i);
            dictionary.addElement(newEntry);
        }

        // Comprueba si se añadieron todos los elementos y se pueden recuperar.
        for (int i = 0; i < 20; i++) {
            Entry retrievedEntry = (Entry) dictionary.getElement("key" + i);
            assertNotNull(retrievedEntry);
            assertEquals("value" + i, retrievedEntry.getValue());
        }

        // Test eliminando todos los elementos
        for (int i = 0; i < 20; i++) {
            dictionary.deleteElement("key" + i);
        }

        assertEquals(0, dictionary.length());
    }
}

