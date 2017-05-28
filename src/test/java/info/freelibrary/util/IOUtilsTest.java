
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
    public void testCloseQuietlyReader() throws IOException {
        IOUtils.closeQuietly(new FileReader(TXT_FILE));
    }

    @Test
    public void testCloseQuietlyWriter() throws IOException {
        IOUtils.closeQuietly(new FileWriter(new File(TMP_DIR, UUID.randomUUID().toString())));
    }

    @Test
    public void testCloseQuietlyInputStream() throws IOException {
        IOUtils.closeQuietly(new FileInputStream(BIN_FILE));
    }

    @Test
    public void testCloseQuietlyImageInputStream() throws IOException {
        IOUtils.closeQuietly(new FileImageInputStream(BIN_FILE));
    }

    @Test
    public void testCloseQuietlyImageOutputStream() throws IOException {
        IOUtils.closeQuietly(new FileImageOutputStream(new File(TMP_DIR, UUID.randomUUID().toString())));
    }

    @Test
    public void testCloseQuietlyOutputStream() throws IOException {
        IOUtils.closeQuietly(new FileOutputStream(new File(TMP_DIR, UUID.randomUUID().toString())));
    }

    @Test
    public void testCloseQuietlyJarFile() throws IOException {
        IOUtils.closeQuietly(new JarFile(JAR_FILE));
    }

    @Test
    public void testCopyStreamInputStreamOutputStream() throws IOException {
        final ByteArrayInputStream baInStream = new ByteArrayInputStream("yes".getBytes());
        final ByteArrayOutputStream baOutStream = new ByteArrayOutputStream();

        IOUtils.copyStream(baInStream, baOutStream);

        Assert.assertEquals("yes", new String(baOutStream.toByteArray()));
    }

    @Test
    public void testCopyStreamFileOutputStream() throws IOException {
        final ByteArrayOutputStream baOutStream = new ByteArrayOutputStream();

        IOUtils.copyStream(TXT_FILE, baOutStream);

        Assert.assertEquals("Lorem ipsum", new String(baOutStream.toByteArray()).substring(0, 11));
    }

    @Test
    public void testReadBytes() throws IOException {
        Assert.assertEquals("yes", new String(IOUtils.readBytes(new ByteArrayInputStream("yes".getBytes()))));
    }

}
