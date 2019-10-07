
package info.freelibrary.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.jar.JarFile;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of IOUtils.
 */
public class IOUtilsTest {

    private static final File BIN_FILE = new File("src/test/resources/green.gif");

    private static final File TXT_FILE = new File("src/test/resources/80_char_test_1.txt");

    private static final File JAR_FILE = new File("src/test/resources/test_folder.jar");

    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    private static final String YES = "yes";

    /**
     * Tests closeQuietly(Reader).
     */
    @Test
    public void testCloseQuietlyReader() {
        try {
            IOUtils.closeQuietly(new FileReader(TXT_FILE));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests closeQuietly(Writer).
     */
    @Test
    public void testCloseQuietlyWriter() {
        try {
            IOUtils.closeQuietly(new FileWriter(new File(TMP_DIR, UUID.randomUUID().toString())));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests closeQuietly(InputStream).
     */
    @Test
    public void testCloseQuietlyInputStream() {
        try {
            IOUtils.closeQuietly(new FileInputStream(BIN_FILE));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests closeQuietly(ImageInputStream).
     */
    @Test
    public void testCloseQuietlyImageInputStream() {
        try {
            IOUtils.closeQuietly(new FileImageInputStream(BIN_FILE));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests closeQuietly(ImageOutputStream).
     */
    @Test
    public void testCloseQuietlyImageOutputStream() {
        try {
            IOUtils.closeQuietly(new FileImageOutputStream(new File(TMP_DIR, UUID.randomUUID().toString())));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests closeQuietly(OutputStream).
     */
    @Test
    public void testCloseQuietlyOutputStream() {
        try {
            IOUtils.closeQuietly(new FileOutputStream(new File(TMP_DIR, UUID.randomUUID().toString())));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests closeQuietly(JarFile).
     */
    @Test
    public void testCloseQuietlyJarFile() {
        try {
            IOUtils.closeQuietly(new JarFile(JAR_FILE));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Tests copyStream(InputStream, OutputStream).
     */
    @Test
    public void testCopyStreamInputStreamOutputStream() {
        final ByteArrayInputStream baInStream = new ByteArrayInputStream(YES.getBytes());
        final ByteArrayOutputStream baOutStream = new ByteArrayOutputStream();

        try {
            IOUtils.copyStream(baInStream, baOutStream);
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }

        Assert.assertEquals(YES, new String(baOutStream.toByteArray()));
    }

    /**
     * Tests copyStream(File, OutputStream).
     */
    @Test
    public void testCopyStreamFileOutputStream() {
        final ByteArrayOutputStream baOutStream = new ByteArrayOutputStream();

        try {
            IOUtils.copyStream(TXT_FILE, baOutStream);
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }

        Assert.assertEquals("Lorem ipsum", new String(baOutStream.toByteArray()).substring(0, 11));
    }

    /**
     * Tests readBytes(ByteArrayInputStream).
     */
    @Test
    public void testReadBytes() {
        try {
            Assert.assertEquals(YES, new String(IOUtils.readBytes(new ByteArrayInputStream(YES.getBytes()))));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

}
