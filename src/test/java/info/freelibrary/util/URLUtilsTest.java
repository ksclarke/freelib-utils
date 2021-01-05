
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * Tests of URL utilities.
 */
public class URLUtilsTest {

    private static final String TEST_URL = "http://projects.freelibrary.info/test.txt";

    private static final String DECODED_URL = "http://arks.freelibrary.info/ark:/99999/e1wb5v";

    /**
     * Tests returning the file represented by a supplied URL.
     *
     * @throws MalformedURLException If the test file URL isn't valid
     */
    @Test
    public void testToFile() throws MalformedURLException {
        assertTrue(URLUtils.toFile(new java.net.URL("file://src/test/resources/green.gif")).exists());
    }

    /**
     * Tests parsing a URL from a supplied string.
     *
     * @throws MalformedURLException If the expected value is an invalid URL
     * @throws MalformedUrlException If supplied URL string isn't a valid URL
     */
    @Test
    public void testParse() throws MalformedURLException, MalformedUrlException {
        assertEquals(new java.net.URL(TEST_URL), URLUtils.parse(TEST_URL));
    }

    /**
     * Tests getting a string value from a URL.
     *
     * @throws MalformedURLException If the supplied URL is invalid
     * @throws IOException If there is trouble reading from the supplied URL
     */
    @Test
    public void testToStringURL() throws MalformedURLException, IOException {
        assertEquals("This is not a test.", URLUtils.toString(new java.net.URL(TEST_URL)).trim());
    }

    /**
     * Test URL decoding.
     */
    @Test
    public void testDecodeString() {
        assertEquals(DECODED_URL, URLUtils.decode("http://arks.freelibrary.info/ark%3A%2F99999%2Fe1wb5v"));
    }

    /**
     * Tests URL decoding, using a supplied character set.
     */
    @Test
    public void testDecodeStringString() {
        assertEquals(DECODED_URL, URLUtils.decode("http://arks.freelibrary.info/ark%253A%252F99999%252Fe1wb5v",
                StandardCharsets.UTF_8.name()));
    }

    /**
     * Tests URL encoding, slashes ignored.
     */
    @Test
    public void testEncodeSlashesIgnored() {
        assertEquals("ark:/99999/e1wb5v%3Fcache=false", URLUtils.encode("ark%3A%2F99999%2Fe1wb5v?cache=false", true));
    }

    /**
     * Tests URL encoding, including slashes.
     */
    @Test
    public void testEncode() {
        assertEquals("ark:%2F99999%2Fe1wb5v", URLUtils.encode("ark:/99999/e1wb5v", false));
    }

}
