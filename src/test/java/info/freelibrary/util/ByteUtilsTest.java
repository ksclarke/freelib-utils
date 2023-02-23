
package info.freelibrary.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of ByteUtils.
 */
public class ByteUtilsTest {

    /** A reversed array of bytes. */
    private byte[] myReversedArrayOfBytes;

    /** A list of bytes. */
    private List<Byte> myListOfBytes;

    /** An array of bytes. */
    private byte[] myArrayOfBytes;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myListOfBytes = Arrays.asList(getByte("a"), getByte("s"), getByte("d"), getByte("f"));
        myReversedArrayOfBytes = "fdsa".getBytes();
        myArrayOfBytes = "asdf".getBytes();
    }

    /**
     * Tests ByteUtils.asList().
     */
    @Test
    public void testAsList() {
        assertEquals(myListOfBytes, ByteUtils.asList(myArrayOfBytes));
    }

    /**
     * Tests ByteUtils.asArray().
     */
    @Test
    public void testAsArray() {
        assertArrayEquals(myArrayOfBytes, ByteUtils.asArray(myListOfBytes));
    }

    /**
     * Tests ByteUtils.reverse(byte[]).
     */
    @Test
    public void testReverseByteArray() {
        ByteUtils.reverse(myArrayOfBytes);
        assertArrayEquals(myReversedArrayOfBytes, myArrayOfBytes);
    }

    /**
     * Tests ByteUtils.reverse(byte[], int, int).
     */
    @Test
    public void testReverseByteArrayIntInt() {
        ByteUtils.reverse(myArrayOfBytes, 0, myArrayOfBytes.length - 1);
        assertArrayEquals(myReversedArrayOfBytes, myArrayOfBytes);
    }

    /**
     * Gets a byte from the supplied character value.
     *
     * @param aCharValue A character value
     * @return A byte
     */
    private Byte getByte(final String aCharValue) {
        return aCharValue.getBytes(StandardCharsets.UTF_8)[0];
    }
}
