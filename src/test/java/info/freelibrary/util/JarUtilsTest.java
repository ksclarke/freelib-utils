
package info.freelibrary.util;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Test;

/**
 * Test for {@link info.freelibrary.util.JarUtils}.
 */
public class JarUtilsTest {

    /**
     * A test Jar file.
     */
    private static final File JAR_FILE = new File("src/test/resources/test_folder.jar");

    /**
     * The JDK's temporary directory.
     */
    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    /**
     * The path to the test file.
     */
    private static final String TEST_FILE_PATH = "test_folder/test_folder2/test_file1.txt";

    /**
     * A pattern to check that a Jar on the classpath exists.
     */
    private static final Pattern JAR_PATTERN =
            Pattern.compile("jar:file://.*/.m2/repository/org/slf4j/slf4j-api/[0-9\\.]*/slf4j-api-[0-9\\.]*.jar!/");

    /**
     * Test clean-up performed after test has run.
     */
    @After
    public void afterTest() {
        FileUtils.delete(new File(TMP_DIR, "test_folder"));
    }

    /**
     * Tests the {@link info.freelibrary.util.JarUtils#extract(File, String, File)} method.
     */
    @Test
    public void testExtractFileStringFile() throws IOException {
        JarUtils.extract(JAR_FILE, TEST_FILE_PATH, TMP_DIR);
        assertTrue(new File(TMP_DIR, TEST_FILE_PATH).exists());
    }

    /**
     * Tests the {@link info.freelibrary.util.JarUtils#extract(URL, String, File)} method.
     */
    @Test
    public void testExtractStringStringFile() throws IOException {
        JarUtils.extract(JarUtils.JAR_URL_PROTOCOL + JAR_FILE.getAbsolutePath(), TEST_FILE_PATH, TMP_DIR);
        assertTrue(new File(TMP_DIR, TEST_FILE_PATH).exists());
    }

    /**
     * Tests extracting a Jar subfile from the supplied Jar URL.
     *
     * @throws IOException If there is trouble reading from the Jar file
     */
    @Test
    public void testExtractURLStringFile() throws IOException {
        JarUtils.extract(new URL(
                JarUtils.JAR_URL_PROTOCOL + JAR_FILE.getAbsolutePath() + JarUtils.JAR_URL_DELIMITER + TEST_FILE_PATH),
                TMP_DIR);
        assertTrue(new File(TMP_DIR, TEST_FILE_PATH).exists());
    }

    /**
     * Tests that supplied a file that doesn't exist.
     *
     * @throws IOException If there is trouble reading the file
     */
    @Test(expected = NoSuchFileException.class)
    public void testExtractNoSuchFile() throws IOException {
        JarUtils.extract("test_folder.jar", EMPTY, TMP_DIR);
    }

    /**
     * Tests the JarUtils contains method.
     *
     * @throws IOException If there is trouble reading the Jar file
     */
    @Test
    public void testContains() throws IOException {
        assertTrue(JarUtils.contains(new JarFile(JAR_FILE), TEST_FILE_PATH));
    }

    /**
     * Tests getting the Jars on the classpath.
     */
    @Test
    public void testGetJarURLs() throws IOException {
        assertTrue(Arrays.stream(JarUtils.getJarURLs()).map(URL::toExternalForm)
                .anyMatch(url -> JAR_PATTERN.matcher(url).matches()));
    }
}
