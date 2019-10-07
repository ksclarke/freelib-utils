
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

/**
 * Tests FileExtFileFilter.
 */
public class FileExtFileFilterTest {

    private static final String JPG = "jpg";

    private static final String TIFF = "tif";

    private static final String DOT = ".";

    private static final String BOX_JPG = "[.jpg]";

    private static final String BOX_JPG_TIFF = "[.jpg, .tif]";

    /**
     * Tests toString().
     */
    @Test
    public final void testFileExtFileFilterString() {
        assertEquals(new FileExtFileFilter(DOT + JPG).toString(), BOX_JPG);
        assertEquals(new FileExtFileFilter(JPG).toString(), BOX_JPG);
    }

    /**
     * Tests toString().
     */
    @Test
    public final void testFileExtFileFilterStringArray() {
        assertEquals(new FileExtFileFilter(DOT + JPG, TIFF).toString(), BOX_JPG_TIFF);
        assertEquals(new FileExtFileFilter(JPG, DOT + TIFF).toString(), BOX_JPG_TIFF);
    }

    /**
     * Tests toString().
     */
    @Test
    public final void testToString() {
        assertEquals(new FileExtFileFilter(JPG, TIFF).toString(), BOX_JPG_TIFF);
    }

    /**
     * Tests accept().
     */
    @Test
    public final void testAccept() {
        final String[] fileNames = new File("src/test/resources").list(new FileExtFileFilter("txt"));

        assertEquals(2, fileNames.length);
        assertTrue(Arrays.stream(fileNames).anyMatch(x -> x.equals("80_char_test_1.txt")));
    }

    /**
     * Tests filters().
     */
    @Test
    public final void testFilters() {
        assertTrue(new FileExtFileFilter(DOT + JPG, TIFF).filters(TIFF));
        assertTrue(new FileExtFileFilter(JPG, DOT + TIFF).filters(TIFF));
        assertTrue(new FileExtFileFilter(DOT + JPG, TIFF).filters(DOT + TIFF));
        assertTrue(new FileExtFileFilter(JPG, DOT + TIFF).filters(DOT + TIFF));
        assertTrue(new FileExtFileFilter(TIFF).filters(DOT + TIFF));
        assertTrue(new FileExtFileFilter(DOT + TIFF).filters(DOT + TIFF));
    }

}
