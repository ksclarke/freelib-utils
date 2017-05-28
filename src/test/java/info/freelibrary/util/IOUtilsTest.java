
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

public class IOUtilsTest {

    private static final File BIN_FILE = new File("src/test/resources/green.gif");

    private static final File TXT_FILE = new File("src/test/resources/80_char_test_1.txt");

    private static final File JAR_FILE = new File("src/test/resources/test_folder.jar");

    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    @Test
    public void testCloseQuietlyReader() {
        try {
            IOUtils.closeQuietly(new FileReader(TXT_FILE));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testCloseQuietlyWriter() {
        try {
            IOUtils.closeQuietly(new FileWriter(new File(TMP_DIR, UUID.randomUUID().toString())));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testCloseQuietlyInputStream() {
        try {
            IOUtils.closeQuietly(new FileInputStream(BIN_FILE));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testCloseQuietlyImageInputStream() {
        try {
            IOUtils.closeQuietly(new FileImageInputStream(BIN_FILE));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testCloseQuietlyImageOutputStream() {
        try {
            IOUtils.closeQuietly(new FileImageOutputStream(new File(TMP_DIR, UUID.randomUUID().toString())));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testCloseQuietlyOutputStream() {
        try {
            IOUtils.closeQuietly(new FileOutputStream(new File(TMP_DIR, UUID.randomUUID().toString())));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testCloseQuietlyJarFile() {
        try {
            IOUtils.closeQuietly(new JarFile(JAR_FILE));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

    @Test
    public void testCopyStreamInputStreamOutputStream() {
        final ByteArrayInputStream baInStream = new ByteArrayInputStream("yes".getBytes());
        final ByteArrayOutputStream baOutStream = new ByteArrayOutputStream();

        try {
            IOUtils.copyStream(baInStream, baOutStream);
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }

        Assert.assertEquals("yes", new String(baOutStream.toByteArray()));
    }

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

    @Test
    public void testReadBytes() {
        try {
            Assert.assertEquals("yes", new String(IOUtils.readBytes(new ByteArrayInputStream("yes".getBytes()))));
        } catch (final IOException details) {
            Assert.fail(details.getMessage());
        }
    }

}
