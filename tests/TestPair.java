import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestPair {

   @Test
    public void testBasicOne() {
        assertEquals(new Pair("Uno", 1), new Pair("Uno", 1));
        assertNotEquals(new Pair("Uno", 1), new Pair("uNo", 1));
    }

    @Test
    public void testGet() {
        Pair eje1 = new Pair("Dos", 2.2); Pair eje2 = new Pair("Tres", 3); Pair eje3 = new Pair("Tres", 50);
        assertEquals("Dos", eje1.get(0));
        assertEquals(3, eje2.get(1));

        assertEquals(eje2.get(0), eje3.get(0));
    }

    @Test
    public void testToString() {
        assertEquals("('Álvaro', 'Ojeda')", new Pair("Álvaro", "Ojeda").toString());
        assertEquals("(3.34643, '12')", new Pair(3.34643, "12").toString());
        assertNotEquals("('3.34643', '12')", new Pair(3.34643, "12").toString());
    }

    @Test
    public void testEquals() {
            assertTrue(new Pair("Antonio", "Gutiérrez").equals(new Pair("Antonio", "Gutiérrez")));
            assertFalse(new Pair("Antonio", "GuTiErReZ").equals(new Pair("Antonio", "Gutiérrez")));

            assertTrue(new Pair("Antonio", "Gu").get(0).equals(new Pair("Antonio", "Valido").get(0)));
            assertTrue(new Pair(20.202020, 3).equals(new Pair(20.202020, 3)));
            assertTrue(new Pair("20.2020", 3).get(1).equals(new Pair(20.2020, 3).get(1)));

            assertFalse(new Pair("20.2020", 3).get(0).equals(new Pair(20.2020, 3).get(0)));
    }
}
