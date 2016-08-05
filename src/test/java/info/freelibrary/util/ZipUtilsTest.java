
package info.freelibrary.util;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Before;
import org.junit.Test;

public class ZipUtilsTest {

    private final File myTmpDir = new File(System.getProperty("java.io.tmpdir"));

    private final Set<String> myFiles = new HashSet<String>();

    @Before
    public void beforeTest() {
        final String[] files = new String[] {
            "/test_folder/test_folder2/test_file1.txt", "/test_folder/test_folder2/test_folder/test_file1.txt",
            "/test_folder/test_file1.txt", "/test_folder/test_folder/test_file1.txt",
            "/test_folder/test_folder/test_file2.txt"
        };

        for (final String file : files) {
            myFiles.add(file);
        }
    }

    @Test
    public void testZipFileFile() throws IOException {
        final File zipFile = new File(myTmpDir, "ziptest-" + UUID.randomUUID().toString() + ".zip");
        final File testDir = new File("src/test/resources");
        final File dir = new File(testDir, "test_folder");
        final ZipInputStream zipStream;

        zipFile.deleteOnExit();

        ZipEntry zipEntry;
        ZipUtils.zip(dir, zipFile);

        zipStream = new ZipInputStream(new FileInputStream(zipFile));

        while ((zipEntry = zipStream.getNextEntry()) != null) {
            final String name = zipEntry.getName();

            if (!myFiles.remove(name)) {
                fail("Found an unexpected zip entry: " + name);
            }
        }

        IOUtils.closeQuietly(zipStream);

        if (myFiles.size() != 0) {
            fail("Failed to find all the zip entries");
        }
    }

    @Test
    public void testZipFileFileFileArray() throws IOException {
        final File zipFile = new File(myTmpDir, "ziptest-" + UUID.randomUUID().toString() + ".zip");
        final File testDir = new File("src/test/resources");
        final File dir = new File(testDir, "test_folder");
        final File other = new File(testDir, "80_char_test_1.txt");
        final ZipInputStream zipStream;

        zipFile.deleteOnExit();

        ZipEntry zipEntry;
        ZipUtils.zip(dir, zipFile, other);

        zipStream = new ZipInputStream(new FileInputStream(zipFile));
        myFiles.add("/test_folder/80_char_test_1.txt");

        while ((zipEntry = zipStream.getNextEntry()) != null) {
            final String name = zipEntry.getName();

            if (!myFiles.remove(name)) {
                fail("Found an unexpected zip entry: " + name);
            }
        }

        IOUtils.closeQuietly(zipStream);

        if (myFiles.size() != 0) {
            fail("Failed to find all the zip entries");
        }
    }

    @Test
    public void testZipFileFileFilenameFilterFileArray() throws IOException {
        final File zipFile = new File(myTmpDir, "ziptest-" + UUID.randomUUID().toString() + ".zip");
        final File testDir = new File("src/test/resources");
        final File dir = new File(testDir, "test_folder");
        final File other = new File(testDir, "80_char_test_1.txt");
        final ZipInputStream zipStream;

        zipFile.deleteOnExit();

        ZipEntry zipEntry;
        ZipUtils.zip(dir, zipFile, new FileExtFileFilter("txt"), other);

        zipStream = new ZipInputStream(new FileInputStream(zipFile));
        myFiles.add("/test_folder/80_char_test_1.txt");

        while ((zipEntry = zipStream.getNextEntry()) != null) {
            final String name = zipEntry.getName();

            if (!myFiles.remove(name)) {
                fail("Found an unexpected zip entry: " + name);
            }
        }

        IOUtils.closeQuietly(zipStream);

        if (myFiles.size() != 0) {
            fail("Failed to find all the zip entries");
        }
    }

}
