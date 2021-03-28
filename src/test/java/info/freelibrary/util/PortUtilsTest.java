
package info.freelibrary.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;

/**
 * Tests of the port utilities class.
 */
public class PortUtilsTest {

    /**
     * Tests <code>PortUtils.getPort()</code>.
     */
    @Test
    public void testGetPort() {
        assertTrue(portIsValid(PortUtils.getPort()));
    }

    /**
     * Tests the port iterator's <code>next()</code> method.
     */
    @Test
    public void testGetPortIteratorNext() {
        final Iterator<Integer> iterator = PortUtils.getPortIterator();

        for (int index = 0; index < 10; index++) {
            assertTrue(portIsValid(iterator.next()));
        }
    }

    /**
     * Tests the port iterator's <code>hasNext()</code> method.
     */
    @Test
    public void testGetPortIteratorHasNext() {
        final Iterator<Integer> iterator = PortUtils.getPortIterator();

        for (int index = 0; index < 10; index++) {
            if (iterator.hasNext()) {
                assertTrue(portIsValid(iterator.next()));
            } else {
                fail();
            }
        }
    }

    /**
     * Checks the supplied value is a valid port.
     *
     * @param aPort A port number
     * @return True if the supplied port number is valid
     */
    private boolean portIsValid(final int aPort) {
        return aPort > 0 && aPort < 65535;
    }
}
