
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * Tests the unchecked URL class.
 */
public class URLTest {

    private static final String FILE_PATH = "/test.txt";

    private static final String HOST = "projects.freelibrary.info";

    private static final String PROTOCOL = "http";

    private static final String QUERY = "cache=false";

    private static final String TEST_URL = StringUtils.format("{}://{}{}", PROTOCOL, HOST, FILE_PATH);

    /**
     * Tests the hashCode() method.
     */
    @Test
    public void testHashCode() {
        System.out.println(TEST_URL);
        assertEquals(new URL(TEST_URL).hashCode(), new URL(TEST_URL).hashCode());
    }

    /**
     * Tests the constructor that takes a standard Java URL.
     *
     * @throws MalformedURLException If there is trouble creating the standard Java URL
     */
    @Test
    public void testURLURL() throws MalformedURLException {
        assertEquals(new URL(TEST_URL), new URL(new java.net.URL(TEST_URL)));
    }

    /**
     * Tests the constructor that takes a string representation of a URL.
     */
    @Test
    public void testURLString() {
        assertEquals(new URL(TEST_URL), new URL(TEST_URL));
    }

    /**
     * Tests the constructor that takes the components of a URL.
     */
    @Test
    public void testURLStringStringString() {
        assertEquals(new URL(TEST_URL), new URL(PROTOCOL, HOST, FILE_PATH));
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getQuery()}.
     */
    @Test
    public void testGetQuery() {
        assertEquals(QUERY, new URL(TEST_URL + "?" + QUERY).getQuery().get());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getPath()}.
     */
    @Test
    public void testGetPath() {
        assertEquals(FILE_PATH, new URL(TEST_URL).getPath());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getUserInfo()}.
     */
    @Test
    public void testGetUserInfo() {
        assertEquals("ksclarke", new URL("https://ksclarke@projects.freelibrary.info").getUserInfo().get());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getAuthority()}.
     */
    @Test
    public void testGetAuthority() {
        assertEquals(HOST, new URL(TEST_URL).getAuthority());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getPort()}.
     */
    @Test
    public void testGetPortEmpty() {
        assertEquals(-1, new URL(TEST_URL).getPort());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getPort()}.
     */
    @Test
    public void testGetPort() {
        assertEquals(8080, new URL("https://localhost:8080/test.txt").getPort());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getDefaultPort()}.
     */
    @Test
    public void testGetDefaultPort() {
        assertEquals(80, new URL(TEST_URL).getDefaultPort());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getProtocol()}.
     */
    @Test
    public void testGetProtocol() {
        assertEquals(PROTOCOL, new URL(TEST_URL).getProtocol());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getHost()}.
     */
    @Test
    public void testGetHost() {
        assertEquals(HOST, new URL(TEST_URL).getHost());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getFile()}.
     */
    @Test
    public void testGetFile() {
        assertEquals(FILE_PATH, new URL(TEST_URL).getFile());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#getRef()}.
     */
    @Test
    public void testGetRef() {
        assertEquals("here", new URL(TEST_URL + "#here").getRef().get());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#equals()}.
     */
    @Test
    public void testEquals​() {
        assertEquals(new URL(TEST_URL), new URL(TEST_URL));
    }

    /**
     * Tests {@code info.freelibrary.util.URL#sameFile()}.
     */
    @Test
    public void testSameFile​() throws MalformedURLException {
        assertTrue(new URL(TEST_URL).sameFile​(new URL(TEST_URL + "#fragment")));
    }

    /**
     * Tests {@code info.freelibrary.util.URL#toURL()}.
     */
    @Test
    public void testToURL() throws MalformedURLException {
        assertEquals(new java.net.URL(TEST_URL), new URL(TEST_URL).toURL());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(TEST_URL, new URL(TEST_URL).toString());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#toExternalForm()}.
     */
    @Test
    public void testToExternalForm() {
        assertEquals(TEST_URL, new URL(TEST_URL).toExternalForm());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#toURI()}.
     */
    @Test
    public void testToURI() {
        assertEquals(URI.create(TEST_URL), new URL(TEST_URL).toURI());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#openConnection()}.
     */
    @Test
    public void testOpenConnection() throws IOException {
        assertEquals(20, new URL(TEST_URL).openConnection().getContentLength());
    }

    /**
     * Tests {@code info.freelibrary.util.URL#openStream()}.
     */
    @Test
    public void testOpenStream() throws IOException {
        final InputStream inStream = new URL(TEST_URL).openStream();
        final byte[] bytes = inStream.readAllBytes();

        inStream.close();
        assertEquals(20, new String(bytes, StandardCharsets.UTF_8).length());
    }

}
