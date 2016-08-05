
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

public class PairtreeObjectTest {

    private static final String TMP_DIR_NAME = System.getProperty("java.io.tmpdir");

    private PairtreeRoot myRoot;

    @After
    public void afterTest() {
        if (myRoot != null) {
            FileUtils.delete(myRoot);
        }
    }

    /**
     * Tests whether PairtreeObject creation without a Pairtree prefix works. Usually PairtreeObjects are not created
     * outside of the PairtreeRoot class. PairtreeRoot know whether or not to ask for a PairtreeObject with a prefix.
     *
     * @throws IOException If there is a problem reading from the file system during the test
     */
    @Test
    public void testPairtreeObjectPairtreeRootString() throws IOException {
        myRoot = new PairtreeRoot(TMP_DIR_NAME, "prefix/");
        assertEquals("prefix=id", new PairtreeObject(myRoot, "prefix/id").getName());
    }

    @Test
    public void testPairtreeObjectPairtreeRootStringString() throws IOException {
        myRoot = new PairtreeRoot(TMP_DIR_NAME, "prefix/");
        assertEquals("id", new PairtreeObject(myRoot, myRoot.getPairtreePrefix(), "prefix/id").getName());
    }

    @Test
    public void testToString() throws IOException {
        myRoot = new PairtreeRoot(TMP_DIR_NAME, "prefix/");
        assertEquals("/tmp/pairtree_root/id/id", new PairtreeObject(myRoot, myRoot.getPairtreePrefix(), "prefix/id")
                .toString());
    }

}
