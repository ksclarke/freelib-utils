
package info.freelibrary.util;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of ZipUtils.
 */
public class ZipUtilsTest {

    /** The logger for the tests. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtilsTest.class, MessageCodes.BUNDLE);

    /** A temporary directory used in testing. */
    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    /** A test file name. */
    private static final String FILE_NAME = "80_char_test_1.txt";

    /** A test folder name. */
    private static final String FOLDER_NAME = "test_folder";

    /** The resources directory used in testing. */
    private static final File RESOURCES_DIR = new File("src/test/resources");

    /** A Zip file extension. */
    private static final String ZIP_EXT = ".zip";

    /** A name pattern used in testing. */
    private static final String ZIP_TEST = "ziptest-";

    /** The set of test files. */
    private final Set<String> myFiles = new HashSet<>();

    /**
     * Set up the testing environment.
     */
    @Before
    public void beforeTest() {
        final String[] files = { "/test_folder/test_folder2/test_file1.txt",
            "/test_folder/test_folder2/test_folder/test_file1.txt", "/test_folder/test_file1.txt",
            "/test_folder/test_folder/test_file1.txt", "/test_folder/test_folder/test_file2.txt" };

        Collections.addAll(myFiles, files);
    }

    /**
     * Tests ZipUtils.
     *
     * @throws IOException If there is trouble using ZipUtils
     */
    @Test
    public void testZipFileFile() throws IOException {
        final File zipFile = new File(TMP_DIR, ZIP_TEST + UUID.randomUUID().toString() + ZIP_EXT);
        final File dir = new File(RESOURCES_DIR, FOLDER_NAME);
        final ZipInputStream zipStream;

        zipFile.deleteOnExit();

        ZipEntry zipEntry;
        ZipUtils.zip(dir, zipFile);

        zipStream = new ZipInputStream(new FileInputStream(zipFile));

        while ((zipEntry = zipStream.getNextEntry()) != null) {
            final String name = zipEntry.getName();

            if (!myFiles.remove(name)) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_063, name));
            }
        }

        IOUtils.closeQuietly(zipStream);

        if (myFiles.size() != 0) {
            fail(LOGGER.getMessage(MessageCodes.UTIL_064));
        }
    }

    /**
     * Tests ZipUtils.
     *
     * @throws IOException If there is trouble using ZipUtils
     */
    @Test
    public void testZipFileFileFileArray() throws IOException {
        final File zipFile = new File(TMP_DIR, ZIP_TEST + UUID.randomUUID().toString() + ZIP_EXT);
        final File dir = new File(RESOURCES_DIR, FOLDER_NAME);
        final File other = new File(RESOURCES_DIR, FILE_NAME);
        final ZipInputStream zipStream;

        zipFile.deleteOnExit();

        ZipEntry zipEntry;
        ZipUtils.zip(dir, zipFile, other);

        zipStream = new ZipInputStream(new FileInputStream(zipFile));
        myFiles.add("/test_folder/" + FILE_NAME);

        while ((zipEntry = zipStream.getNextEntry()) != null) {
            final String name = zipEntry.getName();

            if (!myFiles.remove(name)) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_063, name));
            }
        }

        IOUtils.closeQuietly(zipStream);

        if (myFiles.size() != 0) {
            fail(LOGGER.getMessage(MessageCodes.UTIL_064));
        }
    }

    /**
     * Tests ZipUtils.
     *
     * @throws IOException If there is trouble using ZipUtils
     */
    @Test
    public void testZipFileFileFilenameFilterFileArray() throws IOException {
        final File zipFile = new File(TMP_DIR, ZIP_TEST + UUID.randomUUID().toString() + ZIP_EXT);
        final File dir = new File(RESOURCES_DIR, FOLDER_NAME);
        final File other = new File(RESOURCES_DIR, FILE_NAME);
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
