import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class TestPair {

   @Test
    public void testBasicOne() {
        assertEquals(new Pair("Uno", 1), new Pair("Uno", 1));
        assertNotEquals(new Pair("Uno", 1), new Pair("uNo", 1));

        Pair eje1 = new Pair("Dos", 2.2); Pair eje2 = new Pair("Tres", 3);
        assertEquals("Dos", eje1.get(0));
        assertEquals(3, eje2.get(1));

    }

}
