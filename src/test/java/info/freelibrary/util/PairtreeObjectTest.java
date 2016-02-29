
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class PairtreeObjectTest {

    /**
     * Tests whether PairtreeObject creation without a Pairtree prefix works. Usually PairtreeObjects are not created
     * outside of the PairtreeRoot class. PairtreeRoot know whether or not to ask for a PairtreeObject with a prefix.
     *
     * @throws IOException If there is a problem reading from the file system during the test
     */
    @Test
    public void testPairtreeObjectPairtreeRootString() throws IOException {
        final PairtreeRoot root = new PairtreeRoot("/tmp", "prefix/");
        final PairtreeObject ptObj = new PairtreeObject(root, "prefix/id");

        assertEquals("prefix=id", ptObj.getName());
    }

    @Test
    public void testPairtreeObjectPairtreeRootStringString() throws IOException {
        final PairtreeRoot root = new PairtreeRoot("/tmp", "prefix/");
        final PairtreeObject ptObj = new PairtreeObject(root, root.getPairtreePrefix(), "prefix/id");

        assertEquals("id", ptObj.getName());
    }

    @Test
    public void testToString() throws IOException {
        final PairtreeRoot root = new PairtreeRoot("/tmp", "prefix/");
        final PairtreeObject ptObj = new PairtreeObject(root, root.getPairtreePrefix(), "prefix/id");

        assertEquals("/tmp/pairtree_root/id/id", ptObj.toString());
    }

}
