import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    @Test
    public void testAddElementAndGetValue() {

        Dictionary dictionary = new Dictionary();

        dictionary.addElement("clave1", "valor1");
        dictionary.addElement("clave2", "valor2");

        assertEquals("valor1", dictionary.getValue("clave1"), "El valor asociado a clave1 debería ser valor1");
        assertEquals("valor2", dictionary.getValue("clave2"), "El valor asociado a clave2 debería ser valor2");
        assertNull(dictionary.getValue("clave3"), "El valor asociado a clave3 debería ser nulo");

    }

    @Test
    public void testExpandArrays() {

        Dictionary dictionary = new Dictionary();

        for (int i = 0; i < 15; i++) {
            dictionary.addElement("clave" + i, "valor" + i);
        }

        assertEquals("valor10", dictionary.getValue("clave10"), "El valor asociado a clave10 debería ser valor10");
        assertEquals("valor14", dictionary.getValue("clave14"), "El valor asociado a clave14 debería ser valor14");
    }
}