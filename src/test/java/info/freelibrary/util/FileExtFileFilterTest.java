
package info.freelibrary.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

public class FileExtFileFilterTest {

    @Test
    public final void testFileExtFileFilterString() {
        assertEquals(new FileExtFileFilter(".jpg").toString(), "[.jpg]");
        assertEquals(new FileExtFileFilter("jpg").toString(), "[.jpg]");
    }

    @Test
    public final void testFileExtFileFilterStringArray() {
        assertEquals(new FileExtFileFilter(".jpg", "tif").toString(), "[.jpg, .tif]");
        assertEquals(new FileExtFileFilter("jpg", ".tif").toString(), "[.jpg, .tif]");
    }

    @Test
    public final void testToString() {
        assertEquals(new FileExtFileFilter("jpg", "tif").toString(), "[.jpg, .tif]");
    }

    @Test
    public final void testAccept() {
        final String[] fileNames = new File("src/test/resources").list(new FileExtFileFilter("txt"));

        assertEquals(2, fileNames.length);
        assertTrue(Arrays.stream(fileNames).anyMatch(x -> x.equals("80_char_test_1.txt")));
    }

    @Test
    public final void testFilters() {
        assertTrue(new FileExtFileFilter(".jpg", "tif").filters("tif"));
        assertTrue(new FileExtFileFilter("jpg", ".tif").filters("tif"));
        assertTrue(new FileExtFileFilter(".jpg", "tif").filters(".tif"));
        assertTrue(new FileExtFileFilter("jpg", ".tif").filters(".tif"));
        assertTrue(new FileExtFileFilter("tif").filters(".tif"));
        assertTrue(new FileExtFileFilter(".tif").filters(".tif"));
    }

}
