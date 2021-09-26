
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.junit.Test;

/**
 * Tests for the {@link FileUtils} class.
 */
public class FileUtilsTest {

    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    private static final File TEST_FILE = new File("src/test/resources/test_file");

    private static final File TEST_FOLDER = new File("src/test/resources/test_folder");

    private static final String TEST_FOLDER_MAP = "target/test-classes/test_folder-map.txt";

    private static final String DOES_NOT_EXIST = "doesnotexist";

    private static final String WILDCARD = ".*";

    /**
     * Tests using the private constructor.
     *
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Test
    public void testConstructorIsPrivate()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Constructor<FileUtils> constructor = FileUtils.class.getDeclaredConstructor();

        assertTrue("Constructor should be private", Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();
    }

    /**
     * Tests toFileURL.
     *
     * @throws MalformedURLException If the supplied URL is malformed
     */
    @Test(expected = MalformedURLException.class)
    public void testToFileURLBad() throws MalformedURLException {
        FileUtils.toFile(new URL("http://example.org"));
    }

    /**
     * Tests toFileURL.
     *
     * @throws MalformedURLException If the supplied URL is malformed
     */
    @Test
    public void testToFileURL() throws MalformedURLException {
        final String path = new File(TEST_FOLDER_MAP).getAbsolutePath();
        final URL url = new URL("file://" + path);
        final File testFile = FileUtils.toFile(url);

        assertTrue("Failed to test: " + url, testFile.exists());
    }

    /**
     * Tests listFiles(File, FilenameFilter, Boolean, String).
     *
     * @throws FileNotFoundException If the file isn't found
     */
    @Test(expected = FileNotFoundException.class)
    public void testListFilesFileFilenameFilterBooleanString() throws FileNotFoundException {
        FileUtils.listFiles(new File(DOES_NOT_EXIST), new RegexFileFilter(WILDCARD), true, new String[] {});
    }

    /**
     * Tests listFiles(File, FilenameFilter, Boolean, String array).
     *
     * @throws FileNotFoundException If the file isn't found
     */
    @Test
    public void testListFilesFileFilenameFilterBooleanStringFileTarget() throws FileNotFoundException {
        assertEquals(1, FileUtils.listFiles(new File(TEST_FOLDER_MAP), new RegexFileFilter(WILDCARD), false,
                new String[] {}).length);
    }

    /**
     * Tests listFiles(File, FilenameFilter, Boolean, String array).
     *
     * @throws FileNotFoundException If the file isn't found
     */
    @Test
    public void testListFilesFileFilenameFilterBooleanStringFileTargetJpg() throws FileNotFoundException {
        assertEquals(0, FileUtils.listFiles(new File(TEST_FOLDER_MAP), new RegexFileFilter(".*\\.jpg"), false,
                new String[] {}).length);
    }

    /**
     * Tests listFiles(File, FilenameFilter, Boolean, String array).
     *
     * @throws FileNotFoundException If the file isn't found
     */
    @Test
    public void testListFilesFileFilenameFilterBooleanStringFileTargetIgnored() throws FileNotFoundException {
        assertEquals(3, FileUtils.listFiles(TEST_FOLDER, new RegexFileFilter(".*\\.txt"), true,
                new String[] { "test_folder" }).length);
    }

    /**
     * Tests stripExt(File).
     */
    @Test
    public void testStripExtFile() {
        assertEquals("test_folder-map", FileUtils.stripExt(new File(TEST_FOLDER_MAP)));
    }

    /**
     * Tests stripExt(File).
     */
    @Test
    public void testStripExtFileNoDot() {
        assertEquals("test_file", FileUtils.stripExt(TEST_FILE));
    }

    /**
     * Tests getExt(String).
     */
    @Test
    public void testGetExtString() {
        assertEquals("txt", FileUtils.getExt(TEST_FOLDER_MAP));
    }

    /**
     * Tests getExt(String).
     */
    @Test
    public void testGetExtStringNoExt() {
        assertEquals("", FileUtils.getExt(TEST_FILE.getName()));
    }

    /**
     * Tests getSize().
     */
    @Test
    public void testGetSizeEmpty() {
        assertEquals(0, FileUtils.getSize(new File(DOES_NOT_EXIST)));
    }

    /**
     * Tests getSize().
     */
    @Test
    public void testGetSizeNull() {
        assertEquals(0, FileUtils.getSize((File) null));
    }

    /**
     * Tests copy(File, File).
     *
     * @throws IOException If there is trouble copying files
     */
    @Test
    public void testCopyFileFile() throws IOException {
        final File newFile = new File(TMP_DIR, UUID.randomUUID().toString());

        FileUtils.copy(TEST_FILE, newFile);

        assertTrue(newFile.exists());
    }

    /**
     * Tests copy(File, File).
     *
     * @throws IOException If there is trouble copying.
     */
    @Test
    public void testCopyFileDir() throws IOException {
        final File newDir = new File(TMP_DIR, UUID.randomUUID().toString());

        FileUtils.copy(TEST_FOLDER, newDir);
    }

    /**
     * Tests copy(File, File).
     *
     * @throws IOException If there is trouble copying.
     */
    @Test(expected = IOException.class)
    public void testCopyFileFileDir() throws IOException {
        FileUtils.copy(TEST_FILE, TMP_DIR);
    }

    /**
     * Test method for {@link FileUtils#getSize(File)}
     */
    @Test
    public void testGetSize() {
        assertEquals(25403, FileUtils.getSize(TEST_FOLDER));
    }

    /**
     * Test method for {@link FileUtils#sizeFromBytes(long)}
     */
    @Test
    public void testSizeFromBytes() {
        assertEquals("40 kilobytes", FileUtils.sizeFromBytes(41787));
    }

    /**
     * Test method for {@link FileUtils#delete(File)}
     */
    @Test
    public void testDeleteOfEmptyDir() {
        final File dir = new File("target/test-classes/test_folder");

        try {
            if (!dir.exists()) {
                FileUtils.copy(TEST_FOLDER, dir);
            }
        } catch (final IOException details) {
            fail(details.getMessage());
        }

        assertTrue(FileUtils.delete(dir));
    }

}
