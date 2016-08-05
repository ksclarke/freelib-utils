
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairtreeRootTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PairtreeRootTest.class);

    private static final String TMP_DIR_NAME = System.getProperty("java.io.tmpdir");

    private PairtreeRoot myRoot;

    @After
    public void afterTest() {
        if (myRoot != null) {
            FileUtils.delete(myRoot);
        }
    }

    /**
     * Test method for {@link PairtreeRoot#PairtreeRoot()}.
     */
    @Test
    public void testPairtreeRoot() {
        try {
            myRoot = new PairtreeRoot(TMP_DIR_NAME);
            checkVersionFile(myRoot.getParentFile());
            assertEquals(true, myRoot.canWrite());
        } catch (final IOException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link PairtreeRoot#PairtreeRoot(String)}.
     */
    @Test
    public void testPairtreeRootString() {
        try {
            myRoot = new PairtreeRoot(TMP_DIR_NAME, "myPrefix");
            checkVersionFile(myRoot.getParentFile());
            checkPrefixFile(myRoot.getParentFile());
            assertEquals(true, myRoot.canWrite());
        } catch (final IOException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link PairtreeRoot#PairtreeRoot(File)}.
     */
    @Test
    public void testPairtreeRootFile() {
        try {
            myRoot = new PairtreeRoot(new File(TMP_DIR_NAME));
            checkVersionFile(myRoot.getParentFile());
            assertEquals(true, myRoot.canWrite());
        } catch (final IOException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link PairtreeRoot#PairtreeRoot(File, String)}.
     */
    @Test
    public void testPairtreeRootFileString() {
        try {
            myRoot = new PairtreeRoot(new File(TMP_DIR_NAME), "myPrefix");
            checkVersionFile(myRoot.getParentFile());
            checkPrefixFile(myRoot.getParentFile());
            assertEquals(true, myRoot.canWrite());
        } catch (final IOException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        }
    }

    /**
     * Checks the Pairtree version file in the supplied directory.
     *
     * @param aPtDir A Pairtree directory
     */
    private void checkVersionFile(final File aPtDir) {
        final FilenameFilter filter = new RegexFileFilter("pairtree_version.*");

        try {
            final File[] files = FileUtils.listFiles(aPtDir, filter);

            assertEquals(files.length, 1);

            for (final File file : files) {
                assertEquals(true, file.exists());
            }
        } catch (final FileNotFoundException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        }
    }

    /**
     * Checks that the Pairtree prefix file exists in the supplied directory.
     *
     * @param aPtDir A Pairtree directory
     */
    private void checkPrefixFile(final File aPtDir) {
        final File prefixFile = new File(aPtDir, "pairtree_prefix");

        prefixFile.deleteOnExit();
        assertEquals(true, prefixFile.exists());
    }
}
