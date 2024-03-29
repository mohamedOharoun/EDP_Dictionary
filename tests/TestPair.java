import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class TestPair {

   @Test
    public void testBasicOne() {
        assertEquals(new Pair("Uno", 1), new Pair("Uno", 1));
        assertNotEquals(new Pair("Uno", 1), new Pair("uNo", 1));
    }

}
