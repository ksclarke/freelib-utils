
package info.freelibrary.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Test for {@link info.freelibrary.util.JarUtils}
 */
public class JarUtilsTest {

    private static final File JAR_FILE = new File("src/test/resources/test_folder.jar");

    /**
     * Tests the {@link info.freelibrary.util.JarUtils#extract(java.io.File, String, java.io.File)} method.
     */
    @Test
    public void testExtractFileStringFile() throws IOException {
        final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        final String filePath = "test_folder/test_folder2/test_file1.txt";
        final File output = new File(tmpDir, filePath);

        output.deleteOnExit();
        JarUtils.extract(JAR_FILE, filePath, tmpDir);
        assertTrue(output.exists());
    }

}
