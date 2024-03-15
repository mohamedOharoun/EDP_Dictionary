import static org.junit.Assert.*;

import org.junit.Test;


public class JUnitTest {

    @Test
    public void añadirElementosPut() {
        Dictionary dictionary = new Dictionary();

        // Test añadir elementos
        dictionary.put("key1", "value1");
        dictionary.put("key2", "value2");

        assertEquals(2, dictionary.length());
    }
