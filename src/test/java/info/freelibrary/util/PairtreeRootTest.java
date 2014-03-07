
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairtreeRootTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PairtreeRootTest.class);

    /**
     * Test method for {@link PairtreeRoot#PairtreeRoot()}.
     */
    @Test
    public void testPairtreeRoot() {
        PairtreeRoot root = null;

        try {
            root = new PairtreeRoot();
            checkVersionFile(root.getParentFile());
            assertEquals(true, root.canWrite());
        } catch (IOException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        } finally {
            if (root != null) {
                root.delete();
            }
        }
    }

    /**
     * Test method for {@link PairtreeRoot#PairtreeRoot(String)}.
     */
    @Test
    public void testPairtreeRootString() {
        PairtreeRoot root = null;

        try {
            root = new PairtreeRoot("myPrefix");
            checkVersionFile(root.getParentFile());
            checkPrefixFile(root.getParentFile());
            assertEquals(true, root.canWrite());
        } catch (IOException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        } finally {
            if (root != null) {
                root.delete();
            }
        }
    }

    /**
     * Test method for {@link PairtreeRoot#PairtreeRoot(File)}.
     */
    @Test
    public void testPairtreeRootFile() {
        PairtreeRoot root = null;

        try {
            root = new PairtreeRoot(new File("src/test/resources"));
            checkVersionFile(root.getParentFile());
            assertEquals(true, root.canWrite());
        } catch (IOException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        } finally {
            if (root != null) {
                root.delete();
            }
        }
    }

    /**
     * Test method for {@link PairtreeRoot#PairtreeRoot(File, String)}.
     */
    @Test
    public void testPairtreeRootFileString() {
        PairtreeRoot root = null;

        try {
            root = new PairtreeRoot(new File("src/test/resources"), "myPrefix");
            checkVersionFile(root.getParentFile());
            checkPrefixFile(root.getParentFile());
            assertEquals(true, root.canWrite());
        } catch (IOException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        } finally {
            if (root != null) {
                root.delete();
            }
        }
    }

    /**
     * Test method for {@link PairtreeRoot#getObjectName()}.
     */
    @Test
    public void testGetObjectName() {
        PairtreeRoot root = null;

        try {
            root = new PairtreeRoot();
        } catch (Exception details) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(details.getMessage(), details);
            }

            fail(details.getMessage());
        } finally {
            if (root != null) {
                root.delete();
            }
        }
    }

    /**
     * Checks the Pairtree version file in the supplied directory.
     * 
     * @param aPtDir A Pairtree directory
     */
    private void checkVersionFile(File aPtDir) {
        FilenameFilter filter = new RegexFileFilter("pairtree_version.*");

        try {
            File[] files = FileUtils.listFiles(aPtDir, filter);

            assertEquals(files.length, 1);

            for (File file : files) {
                assertEquals(true, file.exists());
            }
        } catch (FileNotFoundException details) {
            LOGGER.error(details.getMessage(), details);
            fail(details.getMessage());
        }
    }

    /**
     * Checks that the Pairtree prefix file exists in the supplied directory.
     * 
     * @param aPtDir A Pairtree directory
     */
    private void checkPrefixFile(File aPtDir) {
        assertEquals(true, new File(aPtDir, "pairtree_prefix").exists());
    }
}
