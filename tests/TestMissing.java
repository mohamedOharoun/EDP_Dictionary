import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class TestMissing {
    DummyDictionaryMissing ds;
    @Before
    public void init() {
        ds = new DummyDictionaryMissing();
    }

    /*
     * Caso en el que no esté la clave.
     */
    @Test
    public void TestRetrieve() {
        assertEquals("Default from Missing", ds.retrieve(0));
    }

    /*
     * Caso en el que sí esté la clave.
     */
    @Test
    public void TestRetrieveExists() {
        assertEquals("Default from Missing", ds.retrieve(0));
        ds.put(new Pair(0, 0));
        assertEquals(0, ds.retrieve(0));
    }
}
