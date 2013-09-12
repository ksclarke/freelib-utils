
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

/**
 * Tests for the {@link FileUtils} class.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">ksclarke@gmail.com</a>
 */
public class FileUtilsTest {

    /**
     * Tests method for {@link FileUtils#getSize(File)}
     */
    @Test
    public void testGetSize() {
        assertEquals(41787, FileUtils.getSize(new File(
                "src/test/resources/test_folder")));
    }

    /**
     * Tests method for {@link FileUtils#sizeFromBytes(long)}
     */
    @Test
    public void testSizeFromBytes() {
        assertEquals("40 kilobytes", FileUtils.sizeFromBytes(41787));
    }

}
